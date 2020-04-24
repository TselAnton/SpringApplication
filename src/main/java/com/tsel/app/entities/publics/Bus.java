package com.tsel.app.entities.publics;

import com.tsel.app.entities.Fuel;
import com.tsel.app.entities.PublicTransport;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Bus implements PublicTransport, Fuel {

    private String carNumber;
    private String color;
    private Double averageSpeed;
    private String carModel;
    private Double fuelPerKilometer;

    private Integer numberOfSeats;
    private String routeNumber;
    private Double costByTicket;
    private List<String> route;

    @Override
    public String currentStatus() {
        return null;
    }
}
