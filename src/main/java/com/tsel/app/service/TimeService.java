package com.tsel.app.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/***
 * Представление времени в программе:
 * Часы — реальные минуты
 * Минуты — реальные секунды
 */
@Data
@Service
@NoArgsConstructor
public final class TimeService {

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