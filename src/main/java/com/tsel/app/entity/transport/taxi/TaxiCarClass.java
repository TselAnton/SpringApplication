package com.tsel.app.entity.transport.taxi;

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
