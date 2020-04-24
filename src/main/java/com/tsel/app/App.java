package com.tsel.app;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext context
            = new ClassPathXmlApplicationContext("/context_files/MainContext.xml");
    }
}
