package com.tsel.app.conventor;

import static com.tsel.app.service.TimeService.TIME_FORMATTER;

import com.tsel.app.dto.PublicTransportDTO;
import com.tsel.app.entity.community.PublicTransportEntity;
import com.tsel.app.util.RouteBuilder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PublicTransportConvector implements Convector<PublicTransportEntity, PublicTransportDTO> {

    private RouteBuilder routeBuilder;

    @Override
    public PublicTransportDTO convertToDto(PublicTransportEntity entity) {
        return PublicTransportDTO.builder()
            .carNumber(entity.getCarNumber().toUpperCase())
            .carModel(entity.getCarModel())
            .carColor(entity.getColor().toLowerCase())
            .numberOfSeats(String.valueOf(entity.getNumberOfSeats()))
            .costByTicket(String.valueOf(entity.getCostByTicket()))
            .routeNumber(entity.getRouteNumber())
            .routeStartTime(entity.getRouteStartTime().format(TIME_FORMATTER))
            .routeEndTime(entity.getRouteEndTime().format(TIME_FORMATTER))
            .routeList(convertRouteToString(entity.getRouteList()))
            .transportType(entity.getTransportType().getType())
            .build();
    }

    private String convertRouteToString(List<Integer> routeList) {
        return routeList.stream()
            .map(routeBuilder::getPointNameUTF8)
            .collect(Collectors.joining(" - "));
    }
}
