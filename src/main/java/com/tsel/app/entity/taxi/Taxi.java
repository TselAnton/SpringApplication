package com.tsel.app.entity.taxi;

import com.tsel.app.entity.Transport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static java.lang.String.format;

@Setter
@Getter
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
        return format("%s %s цвет %s. Номер машины: %s. Водитель: %s",
                carClass.getDescription(), carModel, color, carNumber, driverName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return isChildSeat == taxi.isChildSeat &&
                carNumber.equals(taxi.carNumber) &&
                color.equals(taxi.color) &&
                carModel.equals(taxi.carModel) &&
                averageSpeed.equals(taxi.averageSpeed) &&
                fuelPerKilometer.equals(taxi.fuelPerKilometer) &&
                driverName.equals(taxi.driverName) &&
                carClass.equals(taxi.carClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carNumber, color, carModel, averageSpeed, fuelPerKilometer, driverName, isChildSeat, carClass);
    }
}