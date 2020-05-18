package com.tsel.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public static String getHomeView() {
        return "homePage";
    }

    @RequestMapping(value = "/carpark", method = RequestMethod.GET)
    public static String getNewHomeView() {
        return "SpringApplication";
    }
}
