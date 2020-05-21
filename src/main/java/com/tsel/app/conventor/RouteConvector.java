package com.tsel.app.conventor;

import static com.tsel.app.service.TimeService.DATE_FORMATTER;
import static java.lang.String.format;

import com.tsel.app.dto.RouteDTO;
import com.tsel.app.entity.community.PublicTransportRoute;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RouteConvector implements Convector<PublicTransportRoute, RouteDTO> {

    private PublicTransportConvector transportConvector;

    @Override
    public RouteDTO convertToDto(PublicTransportRoute entity) {
        return RouteDTO.builder()
            .transport(transportConvector.convertToDto(entity.getTransport()))
            .date(entity.getDate().format(DATE_FORMATTER))
            .numOfPassengers(String.valueOf(entity.getNumOfPassengers()))
            .pathLength(format("%.2f", entity.getPathLength()))
            .build();
    }
}
