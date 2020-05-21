package com.tsel.app.conventor;

import static com.tsel.app.service.TimeService.DATE_TIME_FORMATTER;
import static java.lang.Math.ceil;
import static java.lang.String.format;

import com.tsel.app.dto.TaxiOrderDTO;
import com.tsel.app.entity.taxi.TaxiOrder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TaxiOrderConvector implements Convector<TaxiOrder, TaxiOrderDTO> {

    @Override
    public TaxiOrderDTO convertToDto(TaxiOrder order) {
        return TaxiOrderDTO.builder()
            .orderNumb(String.valueOf(order.getOrderNumber()))
            .price(format("%.0f", ceil(order.getPrice())))
            .startTime(order.getStartTimeOfTrip().format(DATE_TIME_FORMATTER))
            .endTime(order.getEndTimeOfTrip().format(DATE_TIME_FORMATTER))
            .tripLength(format("%.2f", order.getTripLength()))
            .status(order.getOrderStatus())
            .carClass(order.getTaxi().getCarClass().getDescription())
            .carModel(order.getTaxi().getCarModel())
            .carColor(order.getTaxi().getColor().toLowerCase())
            .carNumber(order.getTaxi().getCarNumber().toUpperCase())
            .driverName(order.getTaxi().getDriverName())
            .passengerFullName(order.getPassengerFullName())
            .build();
    }

}
