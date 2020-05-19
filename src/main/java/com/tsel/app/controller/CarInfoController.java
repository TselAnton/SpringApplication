package com.tsel.app.controller;

import com.tsel.app.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.tsel.app.service.TimeService.DATE_TIME_FORMATTER;

@Controller
public class CarInfoController {

    @Autowired
    private TimeService timeService;

    @GetMapping(value = "/car-info")
    public String getCarInfoHomeView(Model model) {
        model.addAttribute("time", timeService.now().format(DATE_TIME_FORMATTER));
        return "car-info-home-page";
    }
}
