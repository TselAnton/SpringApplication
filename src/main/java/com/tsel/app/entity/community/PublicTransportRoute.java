package com.tsel.app.entity.community;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PublicTransportRoute {

    private PublicTransportEntity transport;
    private LocalDate date;
    private int numOfPassengers;
    private double pathLength;
}
