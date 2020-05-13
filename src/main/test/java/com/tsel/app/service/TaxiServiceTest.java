package com.tsel.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.utils.Asserts.assertNotNull;

import com.tsel.app.entity.taxi.Taxi;
import com.tsel.app.entity.taxi.TaxiOrder;
import com.tsel.app.util.FileBufferUtil;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/test/resources/context/TaxiServiceTestContext.xml")
public class TaxiServiceTest {

    @Autowired
    private TaxiService taxiService;

    @Autowired
    private FileBufferUtil bufferUtil;

    @Autowired
    private TimeService timeService;

    @Test
    public void makeOrderShouldReturnRightOrder() {
        bufferUtil.clearBuff(TaxiOrder.class);
        double tripLength = 100.0;
        Optional<TaxiOrder> order = taxiService.makeAnOrder("fullName", tripLength);
        assertTrue(order.isPresent());

        TaxiOrder taxiOrder = order.get();
        assertEquals(taxiService.getNumberOfLastOrder(), taxiOrder.getOrderNumber());
        assertNotNull(taxiOrder.getTaxi(), "Taxi is null");
        assertEquals("fullName", taxiOrder.getPassengerFullName());
        assertEquals(tripLength, taxiOrder.getTripLength(), 0.1);
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
        assertTrue(secondOrder.isPresent());
        Optional<TaxiOrder> thirdOrder = taxiService.makeAnOrder("fullName", 100.0);
        assertTrue(thirdOrder.isPresent());
        Optional<TaxiOrder> fourOrder = taxiService.makeAnOrder("fullName", 100.0);
        assertFalse(fourOrder.isPresent());
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

    @Test
    public void getOrderByDateShouldReturnAllOrders() throws InterruptedException {
        bufferUtil.clearBuff(TaxiOrder.class);
        Optional<TaxiOrder> order1 = taxiService.makeAnOrder("first passenger", 0.0001);
        assertTrue(order1.isPresent());

        Optional<TaxiOrder> order2 = taxiService.makeAnOrder("second passenger", 0.0001);
        assertTrue(order2.isPresent());

        Optional<TaxiOrder> order3 = taxiService.makeAnOrder("third passenger", 0.0001);
        assertTrue(order3.isPresent());

        Thread.sleep(1000);

        List<TaxiOrder> orderList = taxiService.getOrderByDate(timeService.now().toLocalDate());
        assertEquals(3, orderList.size());
    }
}