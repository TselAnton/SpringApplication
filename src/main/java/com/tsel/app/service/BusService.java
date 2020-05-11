package com.tsel.app.service;

import com.tsel.app.entity.PublicTransport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Service
public class BusService {

    //TODO: Записывать путь как заказ. Туда генерить количество пассажиров
    //TODO: Так же подумать над записью в файл этого чуда

    private Set<PublicTransport> publicTransports;

    /**
     * Поиск общественного транспорта по номеру маршрута
     * @param routeNumber Номер маршрута
     * @return Общественный транспорт
     */
    public Optional<PublicTransport> findTransportsByRouteNumber(String routeNumber) {
        return publicTransports.stream()
                .filter(t -> t.getRouteNumber().equalsIgnoreCase(routeNumber))
                .findFirst();
    }
}
