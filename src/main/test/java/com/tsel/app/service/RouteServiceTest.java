package com.tsel.app.service;

import com.tsel.app.exception.BusStopNotExistException;
import com.tsel.app.exception.NonExistentWayException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.util.Arrays.asList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/resources/contexts/MainContext.xml")
public class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @Test(expected = BusStopNotExistException.class)
    public void shouldThrowExceptionWhenPointNotExist() {
        routeService.getPath(asList(1, Integer.MAX_VALUE, 2));
    }

    @Test(expected = NonExistentWayException.class)
    public void shouldThrowExceptionWhenPointsNotConnected() {
        routeService.getPath(asList(1, 8, 10));
    }

    @Test(expected = NonExistentWayException.class)
    public void shouldThrowExceptionWhenFirstAndLastPointsNotConnected() {
        routeService.getPath(asList(1, 2, 9));
    }

    @Test
    public void shouldReturnRouteMap() {
        routeService.getPath(asList(1, 2, 9, 8, 10));
    }
}