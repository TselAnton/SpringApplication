package com.tsel.app.entity.community;

import com.tsel.app.service.RouteService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Minibus extends AbstractPublicTransport {

    public Minibus(String carNumber, String carModel, String color, Double averageSpeed, Double fuelPerKilometer, Integer numberOfSeats, Double costByTicket, String routeNumber, String routeStartTime, String routeEndTime, List<Integer> route, RouteService routeService) {
        super(carNumber, carModel, color, averageSpeed, fuelPerKilometer, numberOfSeats, costByTicket, routeNumber, routeStartTime, routeEndTime, route, routeService);
    }
}
