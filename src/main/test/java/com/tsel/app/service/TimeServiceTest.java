package com.tsel.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/resources/contexts/MainContext.xml")
public class TimeServiceTest {

    @Autowired
    private TimeService timeService;

    @Test
    public void shouldReturnRightTime() throws InterruptedException {
        LocalDateTime beforeProgramTime = timeService.now();
        Thread.sleep(1000);
        LocalDateTime afterProgramTime = timeService.now();

        assertEquals(beforeProgramTime.getHour(), afterProgramTime.getHour());
        assertEquals(1, afterProgramTime.getMinute() - beforeProgramTime.getMinute());
    }

    @Test
    public void shouldReturnRightTimePlusSomeSeconds() {
        int seconds = 120;
        LocalDateTime time = timeService.now();
        LocalDateTime timePlusSeconds = timeService.getTimeAfterSeconds(seconds);

        assertEquals(2, timePlusSeconds.getMinute() - time.getMinute());
    }

    @Test
    public void shouldReturnTrueWhenDateIsBeforeNow() {
        LocalDateTime nowTime = LocalDateTime.now().minusDays(10);
        assertTrue(timeService.isTimeOver(nowTime));
    }

    @Test
    public void shouldReturnFalseWhenDateIsAfterNow() {
        LocalDateTime nowTime = LocalDateTime.now().plusDays(10);
        assertFalse(timeService.isTimeOver(nowTime));
    }
}