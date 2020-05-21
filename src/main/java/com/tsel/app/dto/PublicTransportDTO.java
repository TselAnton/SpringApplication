package com.tsel.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicTransportDTO {
    private String carNumber;
    private String carModel;
    private String carColor;
    private String numberOfSeats;
    private String costByTicket;
    private String routeNumber;
    private String routeStartTime;
    private String routeEndTime;
    private String routeList;
    private String currentLocation;
    private String transportType;
}
