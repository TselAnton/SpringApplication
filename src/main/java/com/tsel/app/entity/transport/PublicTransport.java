package com.tsel.app.entity.transport;

import java.time.LocalTime;
import java.util.Map;

public interface PublicTransport extends Transport {

    Integer getNumberOfSeats();
    String getRouteNumber();
    Double getCostByTicket();
    LocalTime getRouteStartTime();
    LocalTime getRouteEndTime();
    Map<String, Double> getRouteMap();
}
