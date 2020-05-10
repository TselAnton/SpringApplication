package com.tsel.app;

import com.tsel.app.service.TimeService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context
            = new ClassPathXmlApplicationContext("/contexts/MainContext.xml");

//        TimeService service = context.getBean("timeService", TimeService.class);
//        System.out.println(service.now());
    }
}
