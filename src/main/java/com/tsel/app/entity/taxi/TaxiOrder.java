package com.tsel.app.entity.taxi;

import static java.lang.String.format;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Data;

@Data
public class TaxiOrder {

    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("HH:mm");

    private long orderNumber;
    private Taxi taxi;
    private String passengerFullName;
    private double tripLength;
    private double price;
    private LocalDateTime startTimeOfTrip;
    private LocalDateTime endTimeOfTrip;
    private boolean isEnded;
    private boolean isCanceled;

    public TaxiOrder(long orderNumber, Taxi taxi, String passengerFullName, double tripLength, LocalDateTime startTimeOfTrip, LocalDateTime endTimeOfTrip) {
        this.orderNumber = orderNumber;
        this.taxi = taxi;
        this.passengerFullName = passengerFullName;
        this.tripLength = tripLength;
        this.startTimeOfTrip = startTimeOfTrip;
        this.endTimeOfTrip = endTimeOfTrip;
        this.price = countPrice();
        this.isEnded = false;
        this.isCanceled = false;
    }

    public boolean isNotEnded() {
        return !isEnded;
    }

    public void closeOrder() {
        isEnded = true;
    }

    public boolean cancelOrder() {
        if (!isEnded) {
            this.isCanceled = true;
            this.isEnded = true;
            return true;
        }
        return false;
    }

    private double countPrice() {
        return this.taxi.getCarClass().getCostPerKilometer() * tripLength +
            0.3 * ChronoUnit.SECONDS.between(startTimeOfTrip, endTimeOfTrip) +
            0.2 * tripLength + 0.1 * taxi.getAverageSpeed();
    }

    public String getOrderStatus() {
        if (isCanceled) {
            return "Заказ отменён";
        }
        if (isEnded) {
            return "Заказ завершён";
        }
        return "Заказ выполняется";
    }

    @Override
    public String toString() {
        return format("%d. %s - Цена поездки: %s руб.%nТакси: %s%nСтатус заказа: %s. Время начала поездки: %s. Время конца поездки: %s. Длина пути: %.1f",
                orderNumber, startTimeOfTrip.format(DATE_PATTERN), price, taxi.toString(), getOrderStatus(),
                startTimeOfTrip.format(TIME_PATTERN), endTimeOfTrip.format(TIME_PATTERN), tripLength);
    }
}