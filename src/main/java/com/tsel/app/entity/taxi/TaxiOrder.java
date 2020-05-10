package com.tsel.app.entity.taxi;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.lang.String.format;

@Setter
@Getter
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
        this.price = taxi.getCarClass().getCostPerKilometer() * tripLength;
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

    private String getOrderStatus() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxiOrder taxiOrder = (TaxiOrder) o;
        return orderNumber == taxiOrder.orderNumber &&
                Double.compare(taxiOrder.tripLength, tripLength) == 0 &&
                Double.compare(taxiOrder.price, price) == 0 &&
                isEnded == taxiOrder.isEnded &&
                isCanceled == taxiOrder.isCanceled &&
                taxi.equals(taxiOrder.taxi) &&
                passengerFullName.equals(taxiOrder.passengerFullName) &&
                startTimeOfTrip.equals(taxiOrder.startTimeOfTrip) &&
                endTimeOfTrip.equals(taxiOrder.endTimeOfTrip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, taxi, passengerFullName, tripLength, price, startTimeOfTrip, endTimeOfTrip, isEnded, isCanceled);
    }
}