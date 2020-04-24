package com.tsel.app.entities.taxi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TaxiCarClass {

    private String description;
    private Double costPerKilometer;
}
