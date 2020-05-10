package com.tsel.app.entity.taxi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
public class TaxiCarClass {

    private String description;
    private Double costPerKilometer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxiCarClass that = (TaxiCarClass) o;
        return description.equals(that.description) &&
                costPerKilometer.equals(that.costPerKilometer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, costPerKilometer);
    }
}
