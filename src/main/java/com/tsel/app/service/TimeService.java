package com.tsel.app.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/***
 * Представление времени в программе:
 * Часы — реальные минуты
 * Минуты — реальные секунды
 */
public final class TimeService {

    private TimeService(){}

    private static final LocalDateTime initDateTime = LocalDateTime.now();

    public static LocalDateTime now() {
        LocalDateTime currentTime = LocalDateTime.now();
        long realSeconds = ChronoUnit.SECONDS.between(initDateTime, currentTime);
        return initDateTime.plusMinutes(realSeconds);
    }
}