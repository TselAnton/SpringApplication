package com.tsel.app.service;

import static com.tsel.app.service.TimeService.DATE_FORMATTER;
import static java.util.Collections.emptyMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import com.tsel.app.entity.community.PublicTransportRoute;
import com.tsel.app.entity.taxi.TaxiOrder;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class CashService {

    public static final String TAXI_PROFIT_KEY = "taxi.profit";
    public static final String TAXI_EXPENSES_KEY = "taxi.expenses";
    public static final String PUBLIC_PROFIT_KEY = "public.profit";
    public static final String PUBLIC_EXPENSES_KEY = "public.expenses";

    private TaxiService taxiService;
    private PublicTransportService transportService;
    private TimeService timeService;

    /**
     * Получить все доходы автопарка за период
     * @param start Начало периода
     * @param end Конец периода
     * @return Доход в рублях
     */
    public Map<String, Double> getIncomeForPeriod(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            log.warn("Start date \"{}\" is after end date \"{}\"", start.format(DATE_FORMATTER), end.format(DATE_FORMATTER));
            return emptyMap();
        }

        if (end.isAfter(timeService.now().toLocalDate())) {
            log.warn("End time \"{}\" late than now \"{}\"", end.format(DATE_FORMATTER), timeService.now().format(DATE_FORMATTER));
            end = timeService.now().toLocalDate();
        }

        Map<String, Double> incomeMap = new HashMap<>(getTaxiIncomeForPeriod(start, end));
        incomeMap.putAll(getPublicTransportIncomeForPeriod(start, end));
        return incomeMap;
    }

    private Map<String, Double> getTaxiIncomeForPeriod(LocalDate start, LocalDate end) {
        double profit = 0.0;
        double expenses = 0.0;
        do {
            List<TaxiOrder> orders = taxiService.getOrderByDate(start);
            // Count profit
            profit += orders.stream()
                .map(TaxiOrder::getPrice)
                .reduce(Double::sum)
                .orElse(0.0);
            // Count expenses
            expenses += orders.stream()
                .map(order -> order.getTripLength() * order.getTaxi().getCarClass().getCostPerKilometer())
                .reduce(Double::sum)
                .orElse(0.0);

            start = start.plusDays(1);
        } while (start.isBefore(end));

        Map<String, Double> incomeMap = new HashMap<>();
        incomeMap.put(TAXI_PROFIT_KEY, profit);
        incomeMap.put(TAXI_EXPENSES_KEY, expenses);
        return incomeMap;
    }

    private Map<String, Double> getPublicTransportIncomeForPeriod(LocalDate start, LocalDate end) {
        return countProfitToTransport(transportService.getRoutesByTimePeriod(start, end));
    }

    private Map<String, Double> countProfitToTransport(List<PublicTransportRoute> routes) {
        double profit = 0.0;
        double expenses = 0.0;

        // Count profit
        profit += routes.stream()
            .map(t -> t.getNumOfPassengers() * t.getTransport().getCostByTicket())
            .reduce(Double::sum)
            .orElse(0.0);

        // Count expenses
        expenses += routes.stream()
            .map(t -> t.getPathLength() * t.getTransport().getFuelPerKilometer())
            .reduce(Double::sum)
            .orElse(0.0);

        Map<String, Double> incomeMap = new HashMap<>();
        incomeMap.put(PUBLIC_PROFIT_KEY, profit);
        incomeMap.put(PUBLIC_EXPENSES_KEY, expenses);
        return incomeMap;
    }
}
