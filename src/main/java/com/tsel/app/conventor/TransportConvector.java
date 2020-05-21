package com.tsel.app.conventor;

import static java.lang.String.format;

import com.tsel.app.dto.TransportDTO;
import com.tsel.app.entity.Transport;
import com.tsel.app.service.CarService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransportConvector implements Convector<Transport, TransportDTO> {

    private CarService carService;

    @Override
    public TransportDTO convertToDto(Transport entity) {
        return TransportDTO.builder()
            .number(entity.getCarNumber().toUpperCase())
            .color(entity.getColor())
            .model(entity.getCarModel())
            .avrSpeed(format("%.2f", entity.getAverageSpeed()))
            .fuelPerKil(format("%1.3f", entity.getFuelPerKilometer()))
            .type(carService.getCarType(entity))
            .build();
    }
}
