package com.tsel.app.controller;

import com.tsel.app.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

import static com.tsel.app.service.TimeService.DATE_FORMATTER;
import static com.tsel.app.service.TimeService.TIME_FORMATTER;
import static java.lang.String.format;

public abstract class AbstractController {

    protected static final String ERROR_PAGE = "error-page";
    protected static final String REDIRECT_TO_ERROR_PAGE_PATTERN = "/SpringApplication/error-page/%s/%s";

    protected static final String HOME_LINK = "/SpringApplication";

    @Autowired
    private TimeService timeService;

    protected void setDateTimeToModel(Model model) {
        LocalDateTime dateTime = timeService.now();
        model.addAttribute("date", dateTime.format(DATE_FORMATTER));
        model.addAttribute("time", dateTime.format(TIME_FORMATTER));
    }

    protected String returnErrorPageView(Model model, String errorMessage, String linkBack) {
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("link", resolveLink(linkBack));
        setDateTimeToModel(model);
        return ERROR_PAGE;
    }

    protected RedirectView redirectToErrorPage(String errorMessage, String backLink) {
        return new RedirectView(format(REDIRECT_TO_ERROR_PAGE_PATTERN,
                Base64.getEncoder().encodeToString(errorMessage.getBytes()),
                Base64.getEncoder().encodeToString(backLink.getBytes())));
    }

    private String resolveLink(String link) {
        if (link.startsWith("/")) {
            return HOME_LINK + link;
        }
        return HOME_LINK + "/" + link;
    }
}
