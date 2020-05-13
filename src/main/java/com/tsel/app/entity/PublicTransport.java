package com.tsel.app.entity;

import com.tsel.app.entity.community.PublicTransportType;
import java.time.LocalTime;
import java.util.List;

public interface PublicTransport extends Transport {

    Integer getNumberOfSeats();
    String getRouteNumber();
    PublicTransportType getTransportType();
    Double getCostByTicket();
    LocalTime getRouteStartTime();
    LocalTime getRouteEndTime();
    List<Integer> getRouteList();
}
