package com.tsel.app.service;

import static com.tsel.app.service.TimeService.DATE_FORMATTER;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import com.tsel.app.entity.community.PublicTransportRoute;
import com.tsel.app.entity.taxi.TaxiOrder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class CashService {

    private TaxiService taxiService;
    private PublicTransportService transportService;
    private TimeService timeService;

    /**
     * Получить все доходы автопарка за период
     * @param start Начало периода
     * @param end Конец периода
     * @return Доход в рублях
     */
    public Optional<Double> getIncomeForPeriod(LocalDate start, LocalDate end) {
        if (isNotNormalDates(start, end)) {
            log.warn("Start date \"{}\" is after end date \"{}\"", start.format(DATE_FORMATTER), end.format(DATE_FORMATTER));
            return empty();
        }

        if (end.isAfter(timeService.now().toLocalDate())) {
            log.warn("End time \"{}\" late than now \"{}\"", end.format(DATE_FORMATTER), timeService.now().format(DATE_FORMATTER));
            end = timeService.now().toLocalDate();
        }
        return of(getTaxiIncomeForPeriod(start, end) + getPublicTransportIncomeForPeriod(start, end));
    }

    /**
     * Получить все доходы автопарка за период по типу транспорта
     * @param transportType Тип транспорта
     * @param start Начало периода
     * @param end Конец периода
     * @return Доход в рублях
     */
    public Optional<Double> getIncomeForPeriodByTransportType(String transportType, LocalDate start, LocalDate end) {
        if (isNotNormalDates(start, end)) {
            log.warn("Start date \"{}\" is after end date \"{}\"", start.format(DATE_FORMATTER), end.format(DATE_FORMATTER));
            return empty();
        }

        if (end.isAfter(timeService.now().toLocalDate())) {
            log.warn("End time \"{}\" late than now \"{}\"", end.format(DATE_FORMATTER), timeService.now().format(DATE_FORMATTER));
            end = timeService.now().toLocalDate();
        }

        if (transportType.equalsIgnoreCase("taxi") || transportType.equalsIgnoreCase("такси")) {
            return of(getTaxiIncomeForPeriod(start, end));
        } else {
            return ofNullable(getPublicTransportIncomeForPeriodByType(transportType, start, end));
        }
    }

    private double getTaxiIncomeForPeriod(LocalDate start, LocalDate end) {
        double sum = 0.0;
        do {
            List<TaxiOrder> orders = taxiService.getOrderByDate(start);
            // Count profit
            sum += orders.stream()
                .map(TaxiOrder::getPrice)
                .reduce(Double::sum)
                .orElse(0.0);
            // Count expenses
            sum -= orders.stream()
                .map(order -> order.getTripLength() * order.getTaxi().getCarClass().getCostPerKilometer())
                .reduce(Double::sum)
                .orElse(0.0);

            start = start.plusDays(1);
        } while (start.isBefore(end));

        return sum;
    }

    private Double getPublicTransportIncomeForPeriodByType(String type, LocalDate start, LocalDate end) {
        List<PublicTransportRoute> transportRoutes = transportService.getRoutesByTimePeriod(start, end);
        List<PublicTransportRoute> routes = transportRoutes.stream()
            .filter(t -> t.getTransport().getTransportType().getType().equalsIgnoreCase(type))
            .collect(Collectors.toList());

        if (routes.isEmpty() && !transportRoutes.isEmpty()) {
            return null;
        }

        return countProfitToTransport(routes);
    }

    private double getPublicTransportIncomeForPeriod(LocalDate start, LocalDate end) {
        return countProfitToTransport(transportService.getRoutesByTimePeriod(start, end));
    }

    private double countProfitToTransport(List<PublicTransportRoute> routes) {
        double sum = 0.0;
        // Count profit
        sum += routes.stream()
            .map(t -> t.getNumOfPassengers() * t.getTransport().getCostByTicket())
            .reduce(Double::sum)
            .orElse(0.0);

        // Count expenses
        sum -= routes.stream()
            .map(t -> t.getPathLength() * t.getTransport().getFuelPerKilometer())
            .reduce(Double::sum)
            .orElse(0.0);

        return sum;
    }

    private boolean isNotNormalDates(LocalDate start, LocalDate end) {
        return end.isBefore(start);
    }
}
