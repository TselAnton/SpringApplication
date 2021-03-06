package com.tsel.app.service;

import static com.tsel.app.service.TimeService.DATE_FORMATTER;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import com.tsel.app.entity.taxi.Taxi;
import com.tsel.app.entity.taxi.TaxiOrder;
import com.tsel.app.util.FileBufferUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class TaxiService {

    private TimeService timeService;
    private FileBufferUtil bufferUtil;

    private Set<Taxi> taxis;
    private long numberOfLastOrder;

    public TaxiService(TimeService timeService, FileBufferUtil bufferUtil, Set<Taxi> taxis) {
        this.timeService = timeService;
        this.taxis = taxis;
        this.bufferUtil = bufferUtil;
        numberOfLastOrder = 0;
        bufferUtil.clearBuff(TaxiOrder.class);
    }

    /**
     * Сделать заказ
     * @param passengerFullName ФИО клиента
     * @param tripLength Протяжённость пути
     * @return True если заказ успешно сделан
     */
    public Optional<TaxiOrder> makeAnOrder(String passengerFullName, Double tripLength) {
        checkOrdersToEnded();
        Taxi freeTaxi = findFreeTaxi();
        if (freeTaxi == null) {
            return empty();
        }

        TaxiOrder newOrder = new TaxiOrder(
                getOrderNum(),
                freeTaxi,
                passengerFullName,
                tripLength,
                timeService.now(),
                getEndTime(tripLength, freeTaxi));

        log.debug("Created order {}", newOrder);
        bufferUtil.addObjectsToBuffEnd(TaxiOrder.class, singletonList(newOrder));
        return of(newOrder);
    }

    /**
     * Получить заказ по номеру заказа
     * @param orderNumb Номер заказа
     * @return Заказ
     */
    public Optional<TaxiOrder> getOrder(long orderNumb) {
        checkOrdersToEnded();
        log.debug("Get order by orderNumb {}", orderNumb);
        return getOrders().stream()
                .filter(order -> order.getOrderNumber() == orderNumb)
                .findFirst();
    }

    /**
     * Получить заказы по дате
     * @param date Дата
     * @return Лист заказов
     */
    public List<TaxiOrder> getOrderByDate(LocalDate date) {
        checkOrdersToEnded();
        log.debug("Get order by date {}", date.format(DATE_FORMATTER));
        return getOrders().stream()
            .filter(order -> order.getEndTimeOfTrip().toLocalDate().equals(date))
            .filter(order -> !order.isCanceled() && order.isEnded())
            .collect(Collectors.toList());
    }

    /**
     * Отменить заказ
     * @param orderNumb Номер заказа
     * @return True если заказ будет отменён
     */
    public boolean cancelOrder(long orderNumb) {
        checkOrdersToEnded();
        log.debug("Cancel order by orderNumb {}", orderNumb);

        HashSet<TaxiOrder> orders = getOrders();
        TaxiOrder cancelOrder = orders.stream()
            .filter(order -> order.getOrderNumber() == orderNumb)
            .findFirst()
            .orElse(null);

        if (cancelOrder != null && cancelOrder.cancelOrder()) {
            orders.add(cancelOrder);
            bufferUtil.addObjectsToBuff(TaxiOrder.class, orders);
            return true;
        }
        return false;
    }

    /**
     * Получить все работающие в текущий момент такси
     * @return List работающих такси
     */
    public List<Taxi> getAllWorkingTaxis() {
        return getOrders()
                .stream()
                .filter(TaxiOrder::isNotEnded)
                .map(TaxiOrder::getTaxi)
                .collect(Collectors.toList());
    }

    /**
     * Получить все имеющиеся такси
     * @return List всех такси
     */
    public List<Taxi> getAllTaxis() {
        return new ArrayList<>(taxis);
    }

    private HashSet<TaxiOrder> getOrders() {
        return new HashSet<>(bufferUtil.getObjectsFromBuff(TaxiOrder.class));
    }

    private void checkOrdersToEnded() {
        HashSet<TaxiOrder> orders = getOrders();
        orders.stream()
                .filter(order -> timeService.isTimeOver(order.getEndTimeOfTrip()))
                .forEach(TaxiOrder::closeOrder);
        bufferUtil.addObjectsToBuff(TaxiOrder.class, orders);
    }

    private LocalDateTime getEndTime(Double tripLength, Taxi taxi) {
        int tripTime = (int)(3600.0 * tripLength / taxi.getAverageSpeed());
        return timeService.getTimeAfterSeconds((tripTime));
    }

    private Taxi findFreeTaxi() {
        Set<Taxi> freeTaxi = new HashSet<>(taxis);
        freeTaxi.removeAll(getAllWorkingTaxis());
        if (freeTaxi.isEmpty()) {
            return null;
        }
        return returnRandomTaxi(freeTaxi);
    }

    private Taxi returnRandomTaxi(Set<Taxi> taxis) {
        int randomNumb = new Random().nextInt(taxis.size());
        Taxi randomTaxi = null;
        for (Taxi taxi : taxis) {
            if (--randomNumb < 0) {
                randomTaxi = taxi;
            }
        }
        return randomTaxi;
    }

    private long getOrderNum() {
        numberOfLastOrder += 1;
        return numberOfLastOrder;
    }

    private Long getMaxOrderNum() {
        return getOrders()
                .stream()
                .map(TaxiOrder::getOrderNumber)
                .max(Long::compareTo)
                .orElse(0L);
    }
}
