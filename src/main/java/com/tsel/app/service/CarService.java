package com.tsel.app.service;

import com.tsel.app.entity.Transport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@AllArgsConstructor
public class CarService {

    private TaxiService taxiService;
    private PublicTransportService transportService;

    /**
     * Получить весь транспорт автопарка
     * @return List всего транспорта автопарка
     */
    public List<Transport> getAllCarParkTransport() {
        List<Transport> transports = new ArrayList<>(taxiService.getAllTaxis());
        transports.addAll(transportService.getAllTransport());
        return transports;
    }

    /**
     * Получить весь работающий транспорт автопарка
     * @return List работающего транспорта автопарка
     */
    public List<Transport> getAllWorkingTransport() {
        List<Transport> transports = new ArrayList<>(taxiService.getAllWorkingTaxis());
        transports.addAll(transportService.getAllWorkingTransport());
        return transports;
    }
}
