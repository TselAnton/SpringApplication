package com.tsel.app.entities.taxi;

import com.tsel.app.entities.Fuel;
import com.tsel.app.entities.Transport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Taxi implements Transport, Fuel {

    private String carNumber;
    private String color;
    private String carModel;
    private Double averageSpeed;
    private Double fuelPerKilometer;
    private String driverName;
    private boolean isChildSeat;
    private TaxiCarClass taxiCarClass;

    @Override
    public String currentStatus() {
        return null;
    }
}