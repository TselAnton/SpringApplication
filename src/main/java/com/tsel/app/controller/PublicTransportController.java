package com.tsel.app.controller;

import com.tsel.app.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.tsel.app.service.TimeService.DATE_TIME_FORMATTER;

@Controller
public class PublicTransportController {

    @Autowired
    private TimeService timeService;

    @GetMapping(value = "/public-transport-service")
    public String getPublicTransportServiceHomeView(Model model) {
        model.addAttribute("time", timeService.now().format(DATE_TIME_FORMATTER));
        return "public-transport-home-page";
    }
}
