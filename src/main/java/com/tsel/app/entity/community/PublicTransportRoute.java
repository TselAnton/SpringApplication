package com.tsel.app.entity.community;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PublicTransportRoute {

    private PublicTransportEntity transport;
    private LocalDate date;
    private int numOfPassengers;
    private double pathLength;
}
