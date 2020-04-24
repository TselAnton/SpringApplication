package com.tsel.app.entities.publics;

import com.tsel.app.entities.Electric;
import com.tsel.app.entities.PublicTransport;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TrolleyBus implements PublicTransport, Electric {

    private String carNumber;
    private String color;
    private Double averageSpeed;
    private String carModel;
    private Double energyPerKilometer;

    private Integer numberOfSeats;
    private String routeNumber;
    private Double costByTicket;
    private List<String> route;

    @Override
    public String currentStatus() {
        return null;
    }
}
