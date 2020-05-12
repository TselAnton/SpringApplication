package com.tsel.app.service;

import com.tsel.app.entity.community.PublicTransportEntity;
import com.tsel.app.entity.community.PublicTransportRoute;
import com.tsel.app.util.FileBufferUtil;
import com.tsel.app.util.RouteBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Data
@Slf4j
@Service
@AllArgsConstructor
public class PublicTransportService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private TimeService timeService;
    private FileBufferUtil bufferUtil;
    private RouteBuilder routeBuilder;

    private Set<PublicTransportEntity> publicTransports;

    /**
     * Поиск общественного транспорта по номеру маршрута
     * @param routeNumber Номер маршрута
     * @return Общественный транспорт
     */
    public Optional<PublicTransportEntity> getTransportByRouteNumber(String routeNumber) {
        return publicTransports.stream()
                .filter(t -> t.getRouteNumber().equalsIgnoreCase(routeNumber))
                .findFirst();
    }

    /**
     * Получить информацию о поездке в определённую дату
     * @param routeNumber Номер маршрута
     * @param date Дата поездки
     * @return of(PublicTransportRoute), если маршрут существует и уже пройден
     */
    public Optional<PublicTransportRoute> getRouteByDate(String routeNumber, LocalDate date) {

        PublicTransportEntity transport = getTransportByRouteNumber(routeNumber).orElse(null);
        if (transport == null) {
            log.warn("Public Transport with number {} not exist", routeNumber);
            return empty();
        }

        if (timeService.now().isBefore(LocalDateTime.of(date, transport.getRouteEndTime()))) {
            log.warn("Data by date \"{}\" does not yet exist", date.format(FORMATTER));
            return empty();
        }

        Optional<PublicTransportRoute> route = getTransportRouteByDate(transport, date);
        if (!route.isPresent()) {
            int countOfPassengers = countPassengers(transport);
            route = of(new PublicTransportRoute(
                    transport,
                    date,
                    countOfPassengers,
                    countPathLength(transport, countOfPassengers))
            );
            bufferUtil.addObjectsToBuffEnd(PublicTransportRoute.class, singletonList(route.get()));
        }
        return route;
    }

    /**
     * Получить информацию о поездке по определённому промежутку времени
     * @param routeNumber Номер маршрута
     * @param startPeriod Начало периода
     * @param endPeriod Конец периода
     * @return Список информации за промежуток
     */
    public List<PublicTransportRoute> getRoutesByTimePeriod(String routeNumber, LocalDate startPeriod, LocalDate endPeriod) {
        Optional<PublicTransportEntity> transport = getTransportByRouteNumber(routeNumber);
        if (!transport.isPresent()) {
            log.warn("Public Transport with number {} not exist", routeNumber);
            return emptyList();
        }

        if (endPeriod.isBefore(startPeriod)) {
            log.warn("Start date \"{}\" and end date \"{}\" are incorrect",
                startPeriod.format(FORMATTER), endPeriod.format(FORMATTER));
            return emptyList();
        }

        if (timeService.now().isBefore(LocalDateTime.of(endPeriod, LocalTime.parse("00:00:00")))) {
            endPeriod = timeService.now().toLocalDate();
        }

        List<PublicTransportRoute> routeList = new ArrayList<>();
        do {
            routeList.add(getRouteByDate(routeNumber, startPeriod).orElse(null));
            startPeriod = startPeriod.plusDays(1);
        } while (startPeriod.isBefore(endPeriod));

        return routeList.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * Получить текущее местоположение транспорта
     * @param routeNumber Номер маршрута
     * @return of(String), если маршрут существует и транспорт находится в пути
     */
    public Optional<String> getCurrentLocation(String routeNumber) {
        PublicTransportEntity transport = getTransportByRouteNumber(routeNumber).orElse(null);
        if (transport == null) {
            log.warn("Public Transport with number {} not exist", routeNumber);
            return empty();
        }

        if (isNotWorkingTime(transport)) {
            log.warn("Public Transport with number {} not working now", routeNumber);
            return of(format("%s под номером \"%s\" не работает в данный момент",
                    transport.getTransportType(), transport.getRouteNumber()));
        }

        long workingTime = Duration.between(transport.getRouteStartTime(), timeService.now().toLocalTime()).getSeconds();
        double pathLength = workingTime * (transport.getAverageSpeed() / 3600.0);
        int counter = 0;
        int routeListSize = transport.getRouteList().size();
        while (pathLength > 0) {
            double sizeBetweenPoints = RouteBuilder.getPathLengthBetweenTwoPoints(
                transport.getRouteList().get(counter % routeListSize),
                transport.getRouteList().get((counter + 1) % routeListSize));

            if (pathLength - sizeBetweenPoints < 0) {
                return of(routeBuilder.getRouteName(
                        transport.getRouteList().get(counter % routeListSize),
                        transport.getRouteList().get((counter + 1) % routeListSize))
                );
            }
            pathLength -= sizeBetweenPoints;
            counter += 1;
        }
        return empty();
    }

    private boolean isNotWorkingTime(PublicTransportEntity transport) {
        return transport.getRouteStartTime().isAfter(timeService.now().toLocalTime()) ||
                transport.getRouteEndTime().isBefore(timeService.now().toLocalTime());
    }

    private Optional<PublicTransportRoute> getTransportRouteByDate(PublicTransportEntity publicTransport, LocalDate date) {
        HashSet<PublicTransportRoute> routeHashSet =
            new HashSet<>(bufferUtil.getObjectsFromBuff(PublicTransportRoute.class));

        return routeHashSet.stream()
            .filter(route -> route.getTransport().equals(publicTransport) && route.getDate().equals(date))
            .findFirst();
    }

    private int countPassengers(PublicTransportEntity transport) {
        return new Random().nextInt((transport.getNumberOfSeats() * 300) - (transport.getNumberOfSeats() * 10))
            + (transport.getNumberOfSeats() * 10);
    }

    private double countPathLength(PublicTransportEntity transport, int passengerCount) {
        long workingTime = Duration.between(transport.getRouteStartTime(), transport.getRouteEndTime()).getSeconds();
        return (double)workingTime * ((transport.getAverageSpeed() + new Random().nextInt(20) *
                        (passengerCount < transport.getNumberOfSeats() * 150 ? (-1) : 1)) / 3600.0);
    }
}
