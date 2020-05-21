package com.tsel.app.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController extends AbstractController{

    private static final String HOME_PAGE = "index";

    public MainController() {
        super(null);
    }

    @GetMapping(value = "/")
    public String getHomeView(Model model) {
        setDateTimeToModel(model);
        return HOME_PAGE;
    }

    @GetMapping(value = "/error-page/{message}/{link}")
    public String getErrorPage(Model model, @PathVariable("message") String message, @PathVariable("link") String link) {

        return returnErrorPageView(model,
                new String(Base64.getDecoder().decode(message), StandardCharsets.UTF_8),
                new String(Base64.getDecoder().decode(link), StandardCharsets.UTF_8));
    }
}
