package com.tsel.app;

import com.tsel.app.service.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.format.DateTimeFormatter;

@Slf4j
public class App {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    //TODO: Подрубить MVC и дописать контроллеры

    public static void main(String[] args) {
        ConfigurableApplicationContext context
            = new ClassPathXmlApplicationContext("/contexts/MainContext.xml");

        TimeService service = context.getBean("timeService", TimeService.class);
        log.info("App started.");
        log.info("Program time: " + service.now().format(FORMATTER));
    }
}
