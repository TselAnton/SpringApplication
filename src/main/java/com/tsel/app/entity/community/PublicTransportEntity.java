package com.tsel.app.entity.community;

import com.tsel.app.entity.PublicTransport;
import com.tsel.app.util.RouteBuilder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class PublicTransportEntity implements PublicTransport {

    private String transportType;
    private String carNumber;
    private String carModel;
    private String color;

    private Double averageSpeed;
    private Double fuelPerKilometer;
    private Integer numberOfSeats;
    private Double costByTicket;

    private String routeNumber;
    private List<Integer> routeList;
    private LocalTime routeStartTime;
    private LocalTime routeEndTime;

    public PublicTransportEntity(String transportType, String carNumber, String carModel, String color, Double averageSpeed,
                                 Double fuelPerKilometer, Integer numberOfSeats, Double costByTicket,
                                 String routeNumber, List<Integer> routeList, String routeStartTime,
                                 String routeEndTime) {
        this.transportType = transportType;
        this.carNumber = carNumber;
        this.carModel = carModel;
        this.color = color;
        this.averageSpeed = averageSpeed;
        this.fuelPerKilometer = fuelPerKilometer;
        this.numberOfSeats = numberOfSeats;
        this.costByTicket = costByTicket;
        this.routeNumber = routeNumber;
        this.routeList = routeList;
        this.routeStartTime = LocalTime.parse(routeStartTime);
        this.routeEndTime = LocalTime.parse(routeEndTime);
    }

    protected void init() {
        RouteBuilder.checkPointsForCorrectness(this.routeList);
    }
}
