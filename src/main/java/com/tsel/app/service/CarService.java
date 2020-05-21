package com.tsel.app.service;

import com.tsel.app.entity.Transport;
import com.tsel.app.entity.community.PublicTransportEntity;
import com.tsel.app.entity.taxi.Taxi;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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

    /**
     * Получить тип транспорта
     * @param transport Транспорт
     * @return Тип транспорта
     */
    public String getCarType(Transport transport) {
        if (transport instanceof Taxi) {
            return "Такси: " + ((Taxi) transport).getCarClass().getDescription();
        } else {
            return ((PublicTransportEntity) transport).getTransportType().getType();
        }
    }
}
