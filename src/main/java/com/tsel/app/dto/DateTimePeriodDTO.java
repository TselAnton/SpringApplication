package com.tsel.app.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateTimePeriodDTO {

    private LocalDate startPeriod;
    private LocalDate endPeriod;
}
