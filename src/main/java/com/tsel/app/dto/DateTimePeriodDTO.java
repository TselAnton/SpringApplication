package com.tsel.app.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DateTimePeriodDTO {

    private LocalDate startPeriod;
    private LocalDate endPeriod;
}
