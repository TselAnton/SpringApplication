package com.tsel.app.service;

import com.tsel.app.entity.taxi.Taxi;
import com.tsel.app.entity.taxi.TaxiOrder;
import com.tsel.app.util.FileBufferUtil;
import lombok.Data;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/test/resources/context/TaxiServiceTestContext.xml")
public class TaxiServiceTest {

    @Autowired
    @Qualifier(value = "testTaxiService")
    private TaxiService taxiService;

    @Autowired
    private FileBufferUtil bufferUtil;

    @Autowired
    @Qualifier(value = "testTaxi")
    private Taxi taxi;

    @Test
    public void makeOrderShouldReturnRightOrder() {
        bufferUtil.clearBuff(TaxiOrder.class);
        double tripLength = 100.0;
        Optional<TaxiOrder> order = taxiService.makeAnOrder("fullName", tripLength);
        assertTrue(order.isPresent());

        TaxiOrder taxiOrder = order.get();
        assertEquals(taxiService.getNumberOfLastOrder(), taxiOrder.getOrderNumber());
        assertEquals(taxi, taxiOrder.getTaxi());
        assertEquals("fullName", taxiOrder.getPassengerFullName());
        assertEquals(tripLength, taxiOrder.getTripLength(), 0.1);
        assertEquals((taxi.getCarClass().getCostPerKilometer() * tripLength), taxiOrder.getPrice(), 0.1);
        assertEquals(1, taxiOrder.getEndTimeOfTrip().getHour() - taxiOrder.getStartTimeOfTrip().getHour());
        assertFalse(taxiOrder.isEnded());
        assertFalse(taxiOrder.isCanceled());
    }

    @Test
    public void makeOrderShouldReturnEmpty() {
        bufferUtil.clearBuff(TaxiOrder.class);
        Optional<TaxiOrder> firstOrder = taxiService.makeAnOrder("fullName", 100.0);
        assertTrue(firstOrder.isPresent());
        Optional<TaxiOrder> secondOrder = taxiService.makeAnOrder("fullName", 100.0);
        assertFalse(secondOrder.isPresent());
    }

    @Test
    public void cancelOrderShouldReturnTrueWhenOrderIsCanceled() {
        bufferUtil.clearBuff(TaxiOrder.class);
        Optional<TaxiOrder> order = taxiService.makeAnOrder("fullName", 100.0);
        assertTrue(order.isPresent());
        assertTrue(taxiService.cancelOrder(taxiService.getNumberOfLastOrder()));
        assertFalse(taxiService.cancelOrder(100));
    }

    @Test
    public void shouldReturnOrder() {
        bufferUtil.clearBuff(TaxiOrder.class);
        Optional<TaxiOrder> order = taxiService.makeAnOrder("fullName", 100.0);
        assertTrue(order.isPresent());

        Optional<TaxiOrder> updatedOrder = taxiService.getOrder(taxiService.getNumberOfLastOrder());
        assertTrue(updatedOrder.isPresent());
        assertEquals(order, updatedOrder);
    }
}