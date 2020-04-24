package com.tsel.app.entities;

public interface Transport {

    String getCarNumber();
    String getColor();
    Double getAverageSpeed();
    String getCarModel();

    String currentStatus();
}
