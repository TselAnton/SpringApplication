package com.tsel.app.service;

import static java.util.Collections.emptyList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import com.tsel.app.entity.PublicTransport;
import com.tsel.app.entity.community.PublicTransportRoute;
import com.tsel.app.util.FileBufferUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/test/resources/context/PublicTransportServiceTestContext.xml")
public class PublicTransportServiceTest {

    @Autowired
    private PublicTransportService service;

    @Autowired
    private TimeService timeService;

    @Autowired
    private FileBufferUtil bufferUtil;

    @Test
    public void getTransportByRouteNumberShouldReturnAllTransportsByRouteNumber() {
        assertTrue(service.getTransportByRouteNumber("1").isPresent());
        assertTrue(service.getTransportByRouteNumber("2").isPresent());
        assertTrue(service.getTransportByRouteNumber("3").isPresent());
        assertFalse(service.getTransportByRouteNumber("4").isPresent());
    }

    @Test
    public void getRouteByDateShouldReturnInformationAboutRoute() {
        bufferUtil.clearBuff(PublicTransportRoute.class);
        LocalDate date = timeService.now().minusDays(1).toLocalDate();
        PublicTransport transport = service.getTransportByRouteNumber("1").orElse(null);
        Optional<PublicTransportRoute> route = service.getRouteByDate("1", date);
        assertTrue(route.isPresent());
        assertThat(route.get().getDate(), is(date));
        assertThat(route.get().getTransport(), is(transport));
        assertTrue(route.get().getNumOfPassengers() > 0);
        assertTrue(route.get().getPathLength() > 0);
        PublicTransportRoute equalsRoute = service.getRouteByDate("1", date).orElse(null);
        assertEquals(route.get(), equalsRoute);
    }

    @Test
    public void getRouteByDateShouldReturnEmptyWhenTransportNotExist() {
        bufferUtil.clearBuff(PublicTransportRoute.class);
        LocalDate date = timeService.now().minusDays(1).toLocalDate();
        Optional<PublicTransportRoute> route = service.getRouteByDate("10", date);
        assertFalse(route.isPresent());
    }

    @Test
    public void getRouteByDateShouldReturnEmptyWhenDateIsAfterNow() {
        bufferUtil.clearBuff(PublicTransportRoute.class);
        LocalDate date = timeService.now().plusDays(1).toLocalDate();
        Optional<PublicTransportRoute> route = service.getRouteByDate("1", date);
        assertFalse(route.isPresent());
    }

    @Test
    public void getRoutesByTimePeriodShouldReturnEmptyListWhenTransportNotExist() {
        bufferUtil.clearBuff(PublicTransportRoute.class);
        LocalDate start = timeService.now().minusDays(2).toLocalDate();
        LocalDate end = timeService.now().minusDays(1).toLocalDate();
        List<PublicTransportRoute> route = service.getRoutesByTimePeriod("50", start, end);
        assertEquals(emptyList(), route);
    }

    @Test
    public void getRoutesByTimePeriodShouldReturnEmptyWhenStartDateIsAfterEndDate() {
        bufferUtil.clearBuff(PublicTransportRoute.class);
        LocalDate end = timeService.now().minusDays(2).toLocalDate();
        LocalDate start = timeService.now().minusDays(1).toLocalDate();
        List<PublicTransportRoute> route = service.getRoutesByTimePeriod("1", start, end);
        assertEquals(emptyList(), route);
    }

    @Test
    public void getRoutesByTimePeriodShouldReturnNotFullRouteListWhenEndDateAfterNow() {
        bufferUtil.clearBuff(PublicTransportRoute.class);
        LocalDate start = timeService.now().minusDays(2).toLocalDate();
        LocalDate end = timeService.now().plusDays(1).toLocalDate();
        List<PublicTransportRoute> route = service.getRoutesByTimePeriod("1", start, end);
        assertEquals(2, route.size());
        assertEquals(start, route.get(0).getDate());
        assertEquals(start.plusDays(1), route.get(1).getDate());
    }

    @Test
    public void getRoutesByTimePeriodShouldReturnFullRouteList() {
        bufferUtil.clearBuff(PublicTransportRoute.class);
        LocalDate start = timeService.now().minusDays(50).toLocalDate();
        LocalDate end = timeService.now().toLocalDate();
        List<PublicTransportRoute> route = service.getRoutesByTimePeriod("1", start, end);
        assertEquals(50, route.size());
    }

    @Test
    public void getCurrentLocationShouldReturnEmptyWhenTransportWithRouteNumberNotExist() {
        Optional<String> location = service.getCurrentLocation("30");
        assertFalse(location.isPresent());
    }

    @Test
    public void getCurrentLocationShouldReturnNotWorkingMessage() {
        String location = service.getCurrentLocation("2").orElse("");
        assertTrue(location.contains("не работает в данный момент"));
    }

    @Test
    public void getCurrentLocationShouldReturnRightPath() {
        String location = service.getCurrentLocation("3").orElse("");
        assertTrue("BusStop5 - BusStop9".equals(location) || "BusStop9 - BusStop5".equals(location));
    }
}