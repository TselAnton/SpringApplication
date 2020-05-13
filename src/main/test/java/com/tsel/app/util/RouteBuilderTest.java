package com.tsel.app.util;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.tsel.app.exception.RouteBuildException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/test/resources/context/UtilContext.xml")
public class RouteBuilderTest {

    @Autowired
    private RouteBuilder routeBuilder;

    @Test(expected = RouteBuildException.class)
    public void shouldThrowExceptionWhenGetNotExistedBusStop() {
        RouteBuilder.checkPointsForCorrectness(asList(1, 2, 35, 4));
    }

    @Test(expected = RouteBuildException.class)
    public void shouldThrowExceptionWhenSomePointsInMiddleRouteHasNotConnect() {
        RouteBuilder.checkPointsForCorrectness(asList(1, 11, 5, 2));
    }

    @Test(expected = RouteBuildException.class)
    public void shouldThrowExceptionWhenFirstAndLastPointsHasNotConnect() {
        RouteBuilder.checkPointsForCorrectness(asList(1, 11, 9, 8));
    }

    @Test
    public void shouldReturnRightLengthBetweenTwoPoints() {
        assertThat(RouteBuilder.getPathLengthBetweenTwoPoints(1, 2), is(1.2));
    }

    @Test
    public void shouldReturnRightRouteName() {
        assertThat(routeBuilder.getRouteName(1, 11), is("BusStop1 - BusStop11"));
    }
}