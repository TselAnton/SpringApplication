package com.tsel.app.controller;

import com.tsel.app.entity.taxi.TaxiOrder;
import com.tsel.app.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Random;

import static com.tsel.app.service.TimeService.DATE_TIME_FORMATTER;
import static java.lang.Math.ceil;
import static java.lang.String.format;

@Controller
@RequestMapping("/taxi-service")
public class TaxiController extends AbstractController {

    private static final String HOME_DIR = "taxi";
    private static final String HOME_PAGE = "taxi-home-page";
    private static final String ORDER_STATUS_PAGE = "taxi-order-status-page";
    private static final String MAKE_ORDER_PAGE = "taxi-make-order";

    private static final String ERROR_ORDER_MESSAGE = "Заказа с номером #%d не найден";
    private static final String ERROR_MAKE_ORDER_MESSAGE = "К сожалению, все такси заняты на данный момент";

    private static final String ORDER_STATUS_LINK_PATTERN = "/SpringApplication/taxi-service/get-order-status/%d";

    @Autowired
    private TaxiService taxiService;

    @GetMapping
    public String getTaxiServiceHomeView(Model model) {
        setDateTimeToModel(model);
        return resolveView(HOME_PAGE);
    }

    @GetMapping(value = "/get-order-status/{orderNumb}")
    public String getOrderStatus(@PathVariable("orderNumb") Integer orderNumb, Model model) {
        TaxiOrder order = taxiService.getOrder(orderNumb).orElse(null);
        if (order == null) {
            return returnErrorPageView(model, format(ERROR_ORDER_MESSAGE, orderNumb), "/taxi-service");
        }

        model.addAttribute("price", format("%.0f", ceil(order.getPrice())));
        model.addAttribute("startTime", order.getStartTimeOfTrip().format(DATE_TIME_FORMATTER));
        model.addAttribute("endTime", order.getEndTimeOfTrip().format(DATE_TIME_FORMATTER));
        model.addAttribute("tripLength", format("%.2f", order.getTripLength()));
        model.addAttribute("status", order.getOrderStatus());
        model.addAttribute("carClass", order.getTaxi().getCarClass().getDescription());
        model.addAttribute("carModel", order.getTaxi().getCarModel());
        model.addAttribute("carColor", order.getTaxi().getColor().toLowerCase());
        model.addAttribute("carNumber", order.getTaxi().getCarNumber().toUpperCase());
        model.addAttribute("driverName", order.getTaxi().getDriverName());
        model.addAttribute("orderNumb", order.getOrderNumber());

        setDateTimeToModel(model);

        return resolveView(ORDER_STATUS_PAGE);
    }

    private String resolveView(String page) {
        return HOME_DIR + "/" + page;
    }

    @GetMapping(value = "/get-make-order-form")
    public String getMakeOrderForm(Model model) {
        setDateTimeToModel(model);
        return resolveView(MAKE_ORDER_PAGE);
    }

    @PostMapping(value = "/make-order")
    public RedirectView makeOrder(Model model, WebRequest request) {
        String fullName = StringUtils.isEmpty(request.getParameter("fullName")) ?
                request.getParameter("fullName") : "unknown";

        Double tripLength = new Random().nextDouble() * 100.0 + 1.0;    // Длина пути случайна

        TaxiOrder madeOrder = taxiService.makeAnOrder(fullName, tripLength).orElse(null);
        if (madeOrder == null) {
            return redirectToErrorPage(ERROR_MAKE_ORDER_MESSAGE, "/taxi-service");
        }

        return new RedirectView(format(ORDER_STATUS_LINK_PATTERN, madeOrder.getOrderNumber()));
    }
}
