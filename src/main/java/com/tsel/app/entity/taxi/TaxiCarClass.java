package com.tsel.app.entity.taxi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaxiCarClass {

    private String description;
    private Double costPerKilometer;
}
