package com.tsel.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaxiOrderDTO {

    private String orderNumb;
    private String price;
    private String startTime;
    private String endTime;
    private String tripLength;
    private String status;
    private String carClass;
    private String carModel;
    private String carColor;
    private String carNumber;
    private String driverName;
    private String passengerFullName;
}
