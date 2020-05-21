package com.tsel.app.controller;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import com.tsel.app.conventor.PublicTransportConvector;
import com.tsel.app.conventor.RouteConvector;
import com.tsel.app.dto.PublicTransportDTO;
import com.tsel.app.entity.community.PublicTransportEntity;
import com.tsel.app.entity.community.PublicTransportRoute;
import com.tsel.app.service.PublicTransportService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/public-transport-service")
public class PublicTransportController extends AbstractController {

    private static final String HOME_DIR = "public-transport";
    private static final String HOME_PAGE = "public-transport-home-page";
    private static final String HOME_LINK = "/public-transport-service";

    private static final String FIND_TRANSPORT_BY_ROUTE_NUMBER_PAGE = "find-transport-by-route-number";
    private static final String INFORMATION_ABOUT_TRANSPORT_PAGE = "out-transports-page";
    private static final String INFORMATION_ABOUT_ROUTE_PAGE = "out-route-page";

    private static final String INFO_ABOUT_ROUTE = "info-about-route";
    private static final String INFO_ABOUT_ALL_ROUTE = "info-about-all-route";

    private static final String FIND_TRANSPORT_ERROR_MESSAGE = "Транспорт с номером \"%s\" не найден";
    private static final String DATE_PARSE_ERROR_MESSAGE = "Даты указаны неверно, попробуйте ещё раз";
    private static final String NOT_FOUND_ROUTE_ERROR_MESSAGE = "Ничего не найдено. "
        + "Проверьте правильность ввода и попробуйте ещё раз";

    @Autowired
    private PublicTransportService transportService;

    @Autowired
    private PublicTransportConvector transportConvector;

    @Autowired
    private RouteConvector routeConvector;

    public PublicTransportController() {
        super(HOME_DIR);
    }

    @GetMapping
    public String getPublicTransportServiceHomeView(Model model) {
        return resolveView(HOME_PAGE, model);
    }

    @GetMapping(value = "/show-transport-info")
    public String showTransportInfo(@RequestParam("routeNumber") String routeNumber, Model model) {
        PublicTransportEntity transport = transportService.getTransportByRouteNumber(routeNumber).orElse(null);

        if (transport != null) {
            PublicTransportDTO dto = transportConvector.convertToDto(transport);
            dto.setCurrentLocation(transportService.getCurrentLocation(routeNumber).orElse("Неизвестно"));
            model.addAttribute("transport", dto);
            return resolveView(INFORMATION_ABOUT_TRANSPORT_PAGE, model);
        }
        return returnErrorPageView(model, format(FIND_TRANSPORT_ERROR_MESSAGE, routeNumber),
             HOME_LINK + "/find-by-route-number");
    }

    @GetMapping(value = "/info-about-route")
    public String infoAboutRoute(
        @RequestParam("fromDate") String fromDate,
        @RequestParam("byDate") String byDate,
        @RequestParam("routeNumber") String routeNumber, Model model) {

        LocalDate startPeriod = parseStringToLocalDate(fromDate).orElse(null);
        LocalDate endPeriod = parseStringToLocalDate(byDate).orElse(null);

        if (startPeriod == null || endPeriod == null) {
            return returnErrorPageView(model, DATE_PARSE_ERROR_MESSAGE,
                 HOME_LINK + "/find-info-about-route");
        }

        List<PublicTransportRoute> routes = transportService.getRouteByTimePeriod(routeNumber, startPeriod, endPeriod);
        if (routes.isEmpty()) {
            return returnErrorPageView(model, NOT_FOUND_ROUTE_ERROR_MESSAGE,
                 HOME_LINK + "/find-info-about-route");
        }

        model.addAttribute("routes", routeConvector.convertListToDtos(routes));
        model.addAttribute("routeNumb", routeNumber);
        return resolveView(INFORMATION_ABOUT_ROUTE_PAGE, model);
    }

    @GetMapping(value = "/info-about-all-routes")
    public String infoAboutAllRoutes(
        @RequestParam("fromDate") String fromDate,
        @RequestParam("byDate") String byDate, Model model) {

        LocalDate startPeriod = parseStringToLocalDate(fromDate).orElse(null);
        LocalDate endPeriod = parseStringToLocalDate(byDate).orElse(null);

        if (startPeriod == null || endPeriod == null) {
            return returnErrorPageView(model, DATE_PARSE_ERROR_MESSAGE,
                HOME_LINK + "/find-info-about-route");
        }

        List<PublicTransportRoute> routes = transportService.getRoutesByTimePeriod(startPeriod, endPeriod);
        if (routes.isEmpty()) {
            return returnErrorPageView(model, NOT_FOUND_ROUTE_ERROR_MESSAGE,
                HOME_LINK + "/find-info-about-route");
        }

        model.addAttribute("routes", routeConvector.convertListToDtos(routes));
        return resolveView(INFORMATION_ABOUT_ROUTE_PAGE, model);
    }


    @GetMapping(value = "/find-by-route-number")
    public String getFindTransportByRouteNameView(Model model) {
        return resolveView(FIND_TRANSPORT_BY_ROUTE_NUMBER_PAGE, model);
    }

    @GetMapping(value = "/find-info-about-route")
    public String getFindInfoAboutRouteView(Model model) {
        return resolveView(INFO_ABOUT_ROUTE, model);
    }

    @GetMapping(value = "/find-all-info-about-route")
    public String getFindAllInfoAboutRouteView(Model model) {
        return resolveView(INFO_ABOUT_ALL_ROUTE, model);
    }
}
