package com.tsel.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public-transport-service")
public class PublicTransportController extends AbstractController {

    private static final String HOME_DIR = "public-transport";
    private static final String HOME_PAGE = "public-transport-home-page";

    @GetMapping
    public String getPublicTransportServiceHomeView(Model model) {
        setDateTimeToModel(model);
        return resolveView(HOME_PAGE);
    }

    private String resolveView(String page) {
        return HOME_DIR + "/" + page;
    }
}
