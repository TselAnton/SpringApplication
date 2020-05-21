package com.tsel.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportDTO {

    private String number;
    private String color;
    private String model;
    private String fuelPerKil;
    private String avrSpeed;
    private String type;
}
