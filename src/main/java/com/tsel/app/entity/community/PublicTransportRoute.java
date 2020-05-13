package com.tsel.app.entity.community;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicTransportRoute {

    private PublicTransportEntity transport;
    private LocalDate date;
    private int numOfPassengers;
    private double pathLength;
}
