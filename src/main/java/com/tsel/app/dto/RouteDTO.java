package com.tsel.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDTO {

    private PublicTransportDTO transport;
    private String date;
    private String numOfPassengers;
    private String pathLength;
}
