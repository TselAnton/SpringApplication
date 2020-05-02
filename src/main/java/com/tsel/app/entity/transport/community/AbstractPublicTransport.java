package com.tsel.app.entity.transport.community;

import com.tsel.app.entity.transport.PublicTransport;
import com.tsel.app.service.RouteService;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class AbstractPublicTransport implements PublicTransport {

    private String carNumber;
    private String carModel;
    private String color;

    private Double averageSpeed;
    private Integer numberOfSeats;
    private Double costByTicket;

    private String routeNumber;
    private Map<String, Double> routeMap;
    private List<Integer> routeList;
    private LocalTime routeStartTime;
    private LocalTime routeEndTime;

    private RouteService routeService;

    public AbstractPublicTransport(String carNumber,
                                   String carModel,
                                   String color,
                                   Double averageSpeed,
                                   Integer numberOfSeats,
                                   Double costByTicket,
                                   String routeNumber,
                                   String routeStartTime,
                                   String routeEndTime,
                                   List<Integer> route,
                                   RouteService routeService) {
        this.carNumber = carNumber;
        this.carModel = carModel;
        this.color = color;
        this.averageSpeed = averageSpeed;
        this.numberOfSeats = numberOfSeats;
        this.costByTicket = costByTicket;
        this.routeNumber = routeNumber;
        this.routeStartTime = LocalTime.parse(routeStartTime);
        this.routeEndTime = LocalTime.parse(routeEndTime);
        this.routeService = routeService;
        this.routeMap = routeService.getPath(route);
    }
}
