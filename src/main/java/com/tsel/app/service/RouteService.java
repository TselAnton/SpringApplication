package com.tsel.app.service;

import com.tsel.app.exception.BusStopNotExistException;
import com.tsel.app.exception.NonExistentWayException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

@Setter
@Service
@NoArgsConstructor
@AllArgsConstructor
public final class RouteService {

    private static final Map<Route, Double> ROUTE_MAP = new HashMap<>();

    private String point1Name;
    private String point2Name;
    private String point3Name;
    private String point4Name;
    private String point5Name;
    private String point6Name;
    private String point7Name;
    private String point8Name;
    private String point9Name;
    private String point10Name;
    private String point11Name;

    static {
        ROUTE_MAP.put(new Route(1, 2), 1.2);
        ROUTE_MAP.put(new Route(1, 10), 0.8);
        ROUTE_MAP.put(new Route(1, 11), 0.8);
        ROUTE_MAP.put(new Route(2, 3), 0.9);
        ROUTE_MAP.put(new Route(2, 5), 1.0);
        ROUTE_MAP.put(new Route(2, 9), 2.0);
        ROUTE_MAP.put(new Route(2, 11), 0.8);
        ROUTE_MAP.put(new Route(3, 4), 1.0);
        ROUTE_MAP.put(new Route(3, 5), 0.6);
        ROUTE_MAP.put(new Route(3, 6), 1.1);
        ROUTE_MAP.put(new Route(3, 7), 1.9);
        ROUTE_MAP.put(new Route(3, 11), 1.1);
        ROUTE_MAP.put(new Route(4, 5), 1.1);
        ROUTE_MAP.put(new Route(4, 6), 0.6);
        ROUTE_MAP.put(new Route(4, 7), 1.0);
        ROUTE_MAP.put(new Route(4, 8), 1.8);
        ROUTE_MAP.put(new Route(5, 9), 1.0);
        ROUTE_MAP.put(new Route(6, 7), 0.8);
        ROUTE_MAP.put(new Route(6, 9), 0.5);
        ROUTE_MAP.put(new Route(7, 8), 1.0);
        ROUTE_MAP.put(new Route(7, 9), 0.8);
        ROUTE_MAP.put(new Route(8, 9), 0.8);
        ROUTE_MAP.put(new Route(8, 10), 1.5);
        ROUTE_MAP.put(new Route(9, 10), 1.1);
        ROUTE_MAP.put(new Route(9, 11), 1.3);
        ROUTE_MAP.put(new Route(10, 11), 0.8);
    }

    public Map<String, Double> getPath(List<Integer> points) {
        checkPointsForCorrectness(points);

        Map<String, Double> resultPath = new HashMap<>();
        for (int i = 0; i < points.size() - 1; i++) {
            Route currentRoute = new Route(points.get(i), points.get(i + 1));
            if (ROUTE_MAP.containsKey(currentRoute)) {
                resultPath.put(
                        format("%s - %s", resolvePointName(points.get(i)), resolvePointName(points.get(i + 1))),
                        ROUTE_MAP.get(currentRoute));
            } else {
                throw new NonExistentWayException(points.get(i), points.get(i + 1));
            }

            Route lastRoute = new Route(points.get(points.size() - 1), points.get(0));
            if (ROUTE_MAP.containsKey(lastRoute)) {
                resultPath.put(
                        format("%s - %s", resolvePointName(points.get(points.size() - 1)), resolvePointName(points.get(0))),
                        ROUTE_MAP.get(lastRoute));
            } else {
                throw new NonExistentWayException(points.get(points.size() - 1), points.get(0));
            }
        }
        return resultPath;
    }

    private void checkPointsForCorrectness(List<Integer> points) {
        Optional<Integer> wrongPoint = points.stream()
                .filter(p -> p < 1 || p > 11)
                .findAny();

        if (wrongPoint.isPresent()) {
            throw new BusStopNotExistException(wrongPoint.get());
        }
    }

    private String resolvePointName(Integer p) {
        switch (p) {
            case 1: return isNotEmpty(point1Name) ? point1Name : "1";
            case 2: return isNotEmpty(point2Name) ? point2Name : "2";
            case 3: return isNotEmpty(point3Name) ? point3Name : "3";
            case 4: return isNotEmpty(point4Name) ? point4Name : "4";
            case 5: return isNotEmpty(point5Name) ? point5Name : "5";
            case 6: return isNotEmpty(point6Name) ? point6Name : "6";
            case 7: return isNotEmpty(point7Name) ? point7Name : "7";
            case 8: return isNotEmpty(point8Name) ? point8Name : "8";
            case 9: return isNotEmpty(point9Name) ? point9Name : "9";
            case 10: return isNotEmpty(point10Name) ? point10Name : "10";
            case 11: return isNotEmpty(point11Name) ? point11Name : "11";
            default: return null;
        }
    }

    private boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    @AllArgsConstructor
    private static class Route {
        private int p1;
        private int p2;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Route route = (Route) o;
            return (p1 == route.p1 && p2 == route.p2) || (p1 == route.p2 && p2 == route.p1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2) + Objects.hash(p2, p1);
        }
    }
}
