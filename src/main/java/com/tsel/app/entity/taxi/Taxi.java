package com.tsel.app.entity.taxi;

import static java.lang.String.format;

import com.tsel.app.entity.Transport;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Taxi implements Transport {

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
        return format("%s %s, цвет %s. %nНомер машины: %s. Водитель: %s",
                carClass.getDescription(), carModel, color, carNumber, driverName);
    }
}