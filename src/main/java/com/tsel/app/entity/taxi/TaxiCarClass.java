package com.tsel.app.entity.taxi;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class TaxiCarClass {

    private String description;
    private Double costPerKilometer;
}
