package com.tsel.app.entities;

import java.util.List;

public interface PublicTransport extends Transport {

    Integer getNumberOfSeats();
    String getRouteNumber();
    Double getCostByTicket();
    List<String> getRoute();

    // TODO: Добавить время начала работы маршрута
    //TODO: Так же, возможно, стоит подумать над количеством поездок по маршруту (величина N)
}
