package com.tsel.app.entity.transport.community;

import com.tsel.app.entity.transport.Electric;
import com.tsel.app.service.RouteService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrolleyBus extends AbstractPublicTransport implements Electric {

    private Double energyPerKilometer;

    public TrolleyBus(String carNumber,
                      String carModel,
                      String color,
                      Double averageSpeed,
                      Integer numberOfSeats,
                      Double costByTicket,
                      String routeNumber,
                      List<Integer> route,
                      String routeStartTime,
                      String routeEndTime,
                      Double energyPerKilometer,
                      RouteService routeService) {
        super(carNumber, carModel, color, averageSpeed, numberOfSeats, costByTicket,
                routeNumber, routeStartTime, routeEndTime, route, routeService);
        this.energyPerKilometer = energyPerKilometer;
    }
}
