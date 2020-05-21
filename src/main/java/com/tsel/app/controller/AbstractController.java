package com.tsel.app.controller;

import static com.tsel.app.service.TimeService.DATE_FORMATTER;
import static com.tsel.app.service.TimeService.TIME_FORMATTER;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import com.tsel.app.service.TimeService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
public abstract class AbstractController {

    protected static final String ERROR_PAGE = "error-page";
    protected static final String REDIRECT_TO_ERROR_PAGE_PATTERN = "/SpringApplication/error-page/%s/%s";

    protected static final String START_LINK = "/SpringApplication";

    @Autowired
    private TimeService timeService;

    private String homeDir;

    public AbstractController(String homeDir) {
        this.homeDir = homeDir;
    }

    protected void setDateTimeToModel(Model model) {
        LocalDateTime dateTime = timeService.now();
        model.addAttribute("date", dateTime.format(DATE_FORMATTER));
        model.addAttribute("time", dateTime.format(TIME_FORMATTER));
    }

    protected String returnErrorPageView(Model model, String errorMessage, String linkBack) {
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("link", resolveErrorLink(linkBack));
        setDateTimeToModel(model);
        return ERROR_PAGE;
    }

    protected RedirectView redirectToErrorPage(String errorMessage, String backLink) {
        return new RedirectView(format(REDIRECT_TO_ERROR_PAGE_PATTERN,
                Base64.getEncoder().encodeToString(errorMessage.getBytes()),
                Base64.getEncoder().encodeToString(backLink.getBytes())));
    }

    private String resolveErrorLink(String link) {
        if (link.startsWith("/")) {
            return START_LINK + link;
        }
        return START_LINK + "/" + link;
    }

    protected Optional<Long> convertToLong(String val) {
        try {
            return of(Long.valueOf(val));
        } catch (Exception e) {
            log.warn("Can't convert path parameter {}", val);
        }
        return empty();
    }

    protected String resolveView(String page, Model model) {
        setDateTimeToModel(model);
        return homeDir + "/" + page;
    }

    protected Optional<LocalDate> parseStringToLocalDate(String date) {
        try {
            return of(LocalDate.parse(date));
        } catch (Exception e) {
            log.warn("Can't parse date {} to LocalDate", date);
            return empty();
        }
    }
}
