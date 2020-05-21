package com.tsel.app.controller;

import com.tsel.app.conventor.PublicTransportConvector;
import com.tsel.app.conventor.TransportConvector;
import com.tsel.app.dto.PublicTransportDTO;
import com.tsel.app.dto.TransportDTO;
import com.tsel.app.entity.community.PublicTransportEntity;
import com.tsel.app.entity.taxi.Taxi;
import com.tsel.app.service.CarService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car-info")
public class CarInfoController extends AbstractController {

    private static final String HOME_DIR = "car-info";
    private static final String HOME_PAGE = "car-info-home-page";

    private static final String ALL_WORKING_TRANSPORT_PAGE = "all-working-transport";
    private static final String ALL_WORKING_PUBLIC_TRANSPORT_PAGE = "all-working-public-transport";
    private static final String ALL_WORKING_TRANSPORT_ERROR_MESSAGE = "Поиск не дал результатов. Видимо, сейчас все отдыхают ;)";
    private static final String ALL_TRANSPORT_ERROR_MESSAGE = "Поиск не дал результатов. Видимо, автопарк пустует :(";

    public CarInfoController() {
        super(HOME_DIR);
    }

    @Autowired
    private CarService carService;

    @Autowired
    private TransportConvector transportConvector;

    @Autowired
    private PublicTransportConvector publicTransportConvector;

    @GetMapping
    public String getCarInfoHomeView(Model model) {
        return resolveView(HOME_PAGE, model);
    }

    @GetMapping(value = "/info-about-all-working-transport")
    public String getInfoAboutAllWorkingTransport(Model model) {
        List<TransportDTO> transport = carService.getAllWorkingTransport()
            .stream()
            .map(transportConvector::convertToDto)
            .collect(Collectors.toList());

        if (transport.isEmpty()) {
            return returnErrorPageView(model, ALL_WORKING_TRANSPORT_ERROR_MESSAGE, HOME_DIR);
        }

        model.addAttribute("transports", transport);
        return resolveView(ALL_WORKING_TRANSPORT_PAGE, model);
    }

    @GetMapping(value = "/info-about-all-working-public-transport")
    public String getInfoAboutAllWorkingPublicTransport(Model model) {
        List<PublicTransportDTO> transport = carService.getAllWorkingTransport()
            .stream()
            .filter(t -> t instanceof PublicTransportEntity)
            .map(t -> (PublicTransportEntity)t)
            .map(publicTransportConvector::convertToDto)
            .collect(Collectors.toList());

        if (transport.isEmpty()) {
            return returnErrorPageView(model, ALL_WORKING_TRANSPORT_ERROR_MESSAGE, HOME_DIR);
        }

        model.addAttribute("transports", transport);
        return resolveView(ALL_WORKING_PUBLIC_TRANSPORT_PAGE, model);
    }

    @GetMapping(value = "/info-about-working-taxi")
    public String getInfoAboutWorkingTaxi(Model model) {
        List<TransportDTO> transport = carService.getAllWorkingTransport()
            .stream()
            .filter(t -> t instanceof Taxi)
            .map(transportConvector::convertToDto)
            .collect(Collectors.toList());

        if (transport.isEmpty()) {
            return returnErrorPageView(model, ALL_WORKING_TRANSPORT_ERROR_MESSAGE, HOME_DIR);
        }

        model.addAttribute("transports", transport);
        return resolveView(ALL_WORKING_TRANSPORT_PAGE, model);
    }

    @GetMapping(value = "/info-about-all-transport")
    public String getInfoAboutAllTransport(Model model) {
        List<TransportDTO> transport = carService.getAllCarParkTransport()
            .stream()
            .map(transportConvector::convertToDto)
            .collect(Collectors.toList());

        if (transport.isEmpty()) {
            return returnErrorPageView(model, ALL_TRANSPORT_ERROR_MESSAGE, HOME_DIR);
        }

        model.addAttribute("transports", transport);
        return resolveView(ALL_WORKING_TRANSPORT_PAGE, model);
    }
}
