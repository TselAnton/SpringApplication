package com.tsel.app.service;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import com.tsel.app.entity.taxi.Taxi;
import com.tsel.app.entity.taxi.TaxiCarClass;
import com.tsel.app.entity.taxi.TaxiOrder;
import com.tsel.app.util.FileBufferUtil;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/test/resources/context/CashServiceTestContext.xml")
public class CashServiceTest {

    private static int count = 1;

    @Autowired
    private TimeService timeService;

    @Autowired
    private CashService cashService;

    @Autowired
    private FileBufferUtil bufferUtil;

    @Test
    public void getIncomeForPeriodShouldReturnEmptyWhenStartDateAfterEndDate() {
        LocalDate start = timeService.now().toLocalDate().minusDays(1);
        LocalDate end = timeService.now().toLocalDate().minusDays(2);

        Optional<Double> result = cashService.getIncomeForPeriod(start, end);
        assertFalse(result.isPresent());
    }

    @Test
    public void getIncomeForPeriodShouldReturnRightValue() {
        LocalDate start = timeService.now().toLocalDate().minusDays(2);
        LocalDate end = timeService.now().toLocalDate().minusDays(1);

        Optional<Double> result = cashService.getIncomeForPeriod(start, end);
        assertTrue(result.isPresent());
    }

    @Test
    public void getIncomeForPeriodShouldReturnNotFullValue() {
        LocalDate start = timeService.now().toLocalDate().minusDays(2);
        LocalDate end = timeService.now().toLocalDate().plusDays(1);

        Optional<Double> result = cashService.getIncomeForPeriod(start, end);
        assertTrue(result.isPresent());
    }

    @Test
    public void getIncomeForPeriodByTransportTypeShouldReturnTaxiProfit() throws InterruptedException {
        bufferUtil.clearBuff(TaxiOrder.class);
        LocalDate start = timeService.now().toLocalDate().minusDays(3);
        LocalDate end = timeService.now().toLocalDate();

        TaxiOrder order1 = buildOrder("carNum1", 50.0, 0.05, 0.3, 10.0, 20, 3);
        TaxiOrder order2 = buildOrder("carNum1", 40.0, 0.05, 0.5, 12.0, 20, 2);
        TaxiOrder order3 = buildOrder("carNum1", 30.0, 0.05, 0.4, 14.0, 20, 1);

        bufferUtil.addObjectsToBuffEnd(TaxiOrder.class, order1);
        bufferUtil.addObjectsToBuffEnd(TaxiOrder.class, order2);
        bufferUtil.addObjectsToBuffEnd(TaxiOrder.class, order3);

        Optional<Double> result = cashService.getIncomeForPeriodByTransportType("такси", start, end);
        assertTrue(result.isPresent());
        assertEquals(1099.2, result.get(), 0.01);
    }

    @Test
    public void getIncomeForPeriodByTransportTypeShouldReturnEmptyWhenTypeIsNotExist() {
        LocalDate start = timeService.now().toLocalDate().minusDays(3);
        LocalDate end = timeService.now().toLocalDate();

        Optional<Double> result = cashService.getIncomeForPeriodByTransportType("null", start, end);
        assertFalse(result.isPresent());
    }

    @Test
    public void getIncomeForPeriodByTransportTypeShouldReturnEmptyWhenStartDateAfterEndDate() {
        LocalDate start = timeService.now().toLocalDate().minusDays(3);
        LocalDate end = timeService.now().toLocalDate();

        Optional<Double> result = cashService.getIncomeForPeriodByTransportType("null", end, start);
        assertFalse(result.isPresent());
    }

    @Test
    public void getIncomeForPeriodByTransportTypeShouldReturnBusProfit() {
        LocalDate start = timeService.now().toLocalDate().minusDays(3);
        LocalDate end = timeService.now().toLocalDate();

        Optional<Double> result = cashService.getIncomeForPeriodByTransportType("Автобус", start, end);
        assertTrue(result.isPresent());
    }

    @Test
    public void getIncomeForPeriodByTransportTypeShouldReturnMinibusProfit() {
        LocalDate start = timeService.now().toLocalDate().minusDays(3);
        LocalDate end = timeService.now().toLocalDate();

        Optional<Double> result = cashService.getIncomeForPeriodByTransportType("Микроавтобус", start, end);
        assertTrue(result.isPresent());
    }

    @Test
    public void getIncomeForPeriodByTransportTypeShouldReturnTrolleybusProfit() {
        LocalDate start = timeService.now().toLocalDate().minusDays(3);
        LocalDate end = timeService.now().toLocalDate().plusDays(3);

        Optional<Double> result = cashService.getIncomeForPeriodByTransportType("Троллейбус", start, end);
        assertTrue(result.isPresent());
    }

    private TaxiOrder buildOrder(String carNumb, Double avSpeed, Double fuel, Double cost, Double length, int minutes, int minusDays) {
        return new TaxiOrder(
            count++,
            buildTaxi(carNumb, avSpeed, fuel, cost),
            null,
            length,
            timeService.now().minusDays(minusDays),
            timeService.now().minusDays(minusDays).plusMinutes(minutes)
        );
    }

    private Taxi buildTaxi(String carNumb, Double avSpeed, Double fuel, Double perKil) {
        return new Taxi(carNumb, null, null, avSpeed, fuel, null, true, new TaxiCarClass(null, perKil));
    }
}