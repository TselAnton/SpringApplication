package com.tsel.app.entity.transport.taxi;

import com.tsel.app.entity.transport.Fuel;
import com.tsel.app.entity.transport.Transport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static java.lang.String.format;

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
    private TaxiCarClass carClass;

    @Override
    public String toString() {
        return format("%s %s, цвет %s. Номер машины: %s. Водитель: %s",
                carClass.getDescription(), carNumber, color, carNumber, driverName);
    }
}