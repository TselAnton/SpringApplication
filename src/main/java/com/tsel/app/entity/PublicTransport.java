package com.tsel.app.entity;

import java.time.LocalTime;
import java.util.List;

public interface PublicTransport extends Transport {

    Integer getNumberOfSeats();
    String getRouteNumber();
    String getTransportType();
    Double getCostByTicket();
    LocalTime getRouteStartTime();
    LocalTime getRouteEndTime();
    List<Integer> getRouteList();
}
