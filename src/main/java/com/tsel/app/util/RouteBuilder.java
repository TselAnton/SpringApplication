package com.tsel.app.util;

import com.tsel.app.exception.RouteBuildException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
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
        switch (p) {
            case 1: return busStopName1;
            case 2: return busStopName2;
            case 3: return busStopName3;
            case 4: return busStopName4;
            case 5: return busStopName5;
            case 6: return busStopName6;
            case 7: return busStopName7;
            case 8: return busStopName8;
            case 9: return busStopName9;
            case 10: return busStopName10;
            case 11: return busStopName11;
            default: return null;
        }
    }
}
