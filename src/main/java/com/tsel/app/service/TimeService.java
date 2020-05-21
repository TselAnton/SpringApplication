package com.tsel.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/***
 * Представление времени в программе:
 * Часы — реальные минуты
 * Минуты — реальные секунды
 */
@Data
@Service
@NoArgsConstructor
public final class TimeService {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

    private static final LocalDateTime INIT_DATE_TIME = LocalDateTime.now();

    public LocalDateTime now() {
        LocalDateTime currentTime = LocalDateTime.now();
        long realSeconds = ChronoUnit.SECONDS.between(INIT_DATE_TIME, currentTime);
        return INIT_DATE_TIME.plusMinutes(realSeconds);
    }

    public LocalDateTime getTimeAfterSeconds(int seconds) {
        return now().plusSeconds(seconds);
    }

    public boolean isTimeOver(LocalDateTime time) {
        return time.isBefore(now());
    }
}