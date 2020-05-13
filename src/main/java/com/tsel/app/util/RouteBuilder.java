package com.tsel.app.util;

import com.tsel.app.exception.RouteBuildException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class RouteBuilder {

    private static final Double[][] ROUTE_MAP = {
        {0.0,   1.2,    null,   null,   null,   null,   null,   null,   null,   0.8,    0.8},
        {1.2,   0.0,    0.9,    null,   1.0,    null,   null,   null,   2.0,    null,   0.8},
        {null,  0.9,    0.0,    1.0,    0.6,    1.1,    1.9,    null,   null,   null,   1.1},
        {null,  null,   1.0,    0.0,    1.1,    0.6,    1.0,    1.8,    null,   null,   null},
        {null,  1.0,    0.6,    1.1,    0.0,    null,   null,   null,   1.0,    null,   null},
        {null,  null,   1.1,    0.6,    null,   0.0,    0.8,    null,   0.5,    null,   null},
        {null,  null,   1.9,    1.0,    null,   0.8,    0.0,    1.0,    0.8,    null,   null},
        {null,  null,   null,   1.8,    null,   null,   1.0,    0.0,    0.8,    1.5,    null},
        {null,  2.0,    null,   null,   1.0,    0.5,    0.8,    0.8,    0.0,    1.1,    1.3},
        {0.8,   null,   null,   null,   null,   null,   null,   1.5,    1.1,    0.0,    0.8},
        {0.8,   0.8,    1.1,    null,   null,   null,   null,   null,   1.3,    0.8,    0.0},
    };

    private String busStopName1;
    private String busStopName2;
    private String busStopName3;
    private String busStopName4;
    private String busStopName5;
    private String busStopName6;
    private String busStopName7;
    private String busStopName8;
    private String busStopName9;
    private String busStopName10;
    private String busStopName11;

    /**
     * Проверка пути на возможность построения
     * @param points Путь, состоящий из номеров остановок
     * Данный метод кидает эксепшен, чтобы не было возможности поднять контекст с неправильно заданным путём.
     */
    public static void checkPointsForCorrectness(List<Integer> points) {
        Optional<Integer> wrongPoint = points.stream()
            .filter(p -> p < 1 || p > 11)
            .findAny();

        if (wrongPoint.isPresent()) {
            throw new RouteBuildException(wrongPoint.get());
        }

        for (int i = 1; i <= points.size(); i++) {
            if (ROUTE_MAP[points.get((i - 1) % points.size()) - 1][points.get(i % points.size()) - 1] == null) {
                throw new RouteBuildException(points.get((i - 1) % points.size()), points.get(i % points.size()));
            }
        }
    }

    /**
     * Получение длины пути между двумя остановками
     * @param p1 Первая остановка
     * @param p2 Вторая остановка
     * @return Длина пути
     */
    public static Double getPathLengthBetweenTwoPoints(Integer p1, Integer p2) {
        return ROUTE_MAP[p1 - 1][p2 - 1];
    }

    /**
     * Вернуть текущее положение
     * @param p1 Начальная остановка
     * @param p2 Конечная остановка
     * @return Текущее положение типа String
     */
    public String getRouteName(Integer p1, Integer p2) {
        return getPointName(p1) + " - " + getPointName(p2);
    }

    private String getPointName(Integer p) {
        if (p == 1 && isNotBlank(busStopName1)) return busStopName1;
        else if (p == 2 && isNotBlank(busStopName2)) return busStopName2;
        else if (p == 3 && isNotBlank(busStopName3)) return busStopName3;
        else if (p == 4 && isNotBlank(busStopName4)) return busStopName4;
        else if (p == 5 && isNotBlank(busStopName5)) return busStopName5;
        else if (p == 6 && isNotBlank(busStopName6)) return busStopName6;
        else if (p == 7 && isNotBlank(busStopName7)) return busStopName7;
        else if (p == 8 && isNotBlank(busStopName8)) return busStopName8;
        else if (p == 9 && isNotBlank(busStopName9)) return busStopName9;
        else if (p == 10 && isNotBlank(busStopName10)) return busStopName10;
        else if (p == 11 && isNotBlank(busStopName11)) return busStopName11;

        return String.valueOf(p + 1);
    }

    private boolean isNotBlank(String str) {
        return str != null && str.length() > 0;
    }
}
