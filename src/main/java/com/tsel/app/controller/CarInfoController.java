package com.tsel.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car-info")
public class CarInfoController extends AbstractController {

    private static final String HOME_DIR = "cash-info";
    private static final String HOME_PAGE = "cash-info-home-page";

    @GetMapping
    public String getCarInfoHomeView(Model model) {
        setDateTimeToModel(model);
        return resolveView(HOME_PAGE);
    }

    private String resolveView(String page) {
        return HOME_DIR + "/" + page;
    }
}
