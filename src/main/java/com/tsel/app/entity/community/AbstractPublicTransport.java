package com.tsel.app.entity.community;

import com.tsel.app.entity.PublicTransport;
import com.tsel.app.service.RouteService;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public abstract class AbstractPublicTransport implements PublicTransport {

    private String carNumber;
    private String carModel;
    private String color;

    private Double averageSpeed;
    private Double fuelPerKilometer;
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
                                   Double fuelPerKilometer,
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
        this.fuelPerKilometer = fuelPerKilometer;
        this.numberOfSeats = numberOfSeats;
        this.costByTicket = costByTicket;
        this.routeNumber = routeNumber;
        this.routeStartTime = LocalTime.parse(routeStartTime);
        this.routeEndTime = LocalTime.parse(routeEndTime);
        this.routeService = routeService;
        this.routeMap = routeService.getPath(route);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPublicTransport that = (AbstractPublicTransport) o;
        return carNumber.equals(that.carNumber) &&
                carModel.equals(that.carModel) &&
                color.equals(that.color) &&
                averageSpeed.equals(that.averageSpeed) &&
                fuelPerKilometer.equals(that.fuelPerKilometer) &&
                numberOfSeats.equals(that.numberOfSeats) &&
                costByTicket.equals(that.costByTicket) &&
                routeNumber.equals(that.routeNumber) &&
                routeMap.equals(that.routeMap) &&
                routeList.equals(that.routeList) &&
                routeStartTime.equals(that.routeStartTime) &&
                routeEndTime.equals(that.routeEndTime) &&
                routeService.equals(that.routeService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carNumber, carModel, color, averageSpeed, fuelPerKilometer, numberOfSeats, costByTicket, routeNumber, routeMap, routeList, routeStartTime, routeEndTime, routeService);
    }
}
