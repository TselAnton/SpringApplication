package com.tsel.app.entities.taxi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class TaxiOrder {

    private Taxi taxi;
    private String passengerFullName;
    private boolean isInTrip;
    private LocalDateTime startTimeOfTrip;
    private LocalDateTime endTimeOfTrip;
    private double tripLength;

    public boolean startTrip() {
        return false;
    }

    public boolean stopTrip() {
        return false;
    }

    public boolean isDuringTrip() {
        return false;
    }

    //TODO: Работа со временем: при заказе такси,
    // будет в заказ записываться текущее время + расчитываться время поездки.
    // Пока время не истечёт, такси не закончит своё движение

    // TODO: Пусть в сервисе происходит проверка: если такси в поездке + время вышло, то можно перезаказать такси, иначе искать следующее
}
