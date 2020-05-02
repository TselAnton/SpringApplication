package com.tsel.app;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.format.DateTimeFormatter;

public class App {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {
        ConfigurableApplicationContext context
            = new ClassPathXmlApplicationContext("/context_files/MainContext.xml");
    }
}
