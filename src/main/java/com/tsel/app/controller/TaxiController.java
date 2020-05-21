package com.tsel.app.controller;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

import com.tsel.app.conventor.TaxiOrderConvector;
import com.tsel.app.entity.taxi.TaxiOrder;
import com.tsel.app.service.TaxiService;
import java.util.Optional;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@RequestMapping("/taxi-service")
public class TaxiController extends AbstractController {

    private static final String HOME_DIR = "taxi";

    private static final String HOME_PAGE = "taxi-home-page";
    private static final String ORDER_STATUS_PAGE = "taxi-order-status-page";
    private static final String MAKE_ORDER_PAGE = "taxi-make-order";
    private static final String ORDER_STATUS_FORM_PAGE = "taxi-get-order-status";
    private static final String CANCEL_ORDER_FORM_PAGE = "taxi-cancel-order";

    private static final String ERROR_ORDER_MESSAGE = "Заказа с номером #%s не найден";
    private static final String ERROR_ORDER_ID_MESSAGE = "Введенное, чтобы это не было, не является id";
    private static final String ERROR_MAKE_ORDER_MESSAGE = "К сожалению, все такси заняты на данный момент";
    private static final String ERROR_CANCEL_ORDER_MESSAGE = "Заказа с номером #%s не найден или уже завершен/отменен";

    private static final String ORDER_STATUS_LINK_PATTERN = "/SpringApplication/taxi-service/get-order-status?orderNumb=%d";
    private static final String HOME_LINK = "/taxi-service";

    @Autowired
    private TaxiService taxiService;

    @Autowired
    private TaxiOrderConvector convector;

    public TaxiController() {
        super(HOME_DIR);
    }

    @GetMapping
    public String getTaxiServiceHomeView(Model model) {
        return resolveView(HOME_PAGE, model);
    }

    @GetMapping(value = "/get-order-status")
    public String getOrderStatus(@RequestParam("orderNumb") String orderNumb, Model model) {
        Optional<Long> id = convertToLong(orderNumb);
        if (!id.isPresent()) {
            return returnErrorPageView(model, format(ERROR_ORDER_ID_MESSAGE, orderNumb), HOME_LINK);
        }

        TaxiOrder order = taxiService.getOrder(id.get()).orElse(null);
        if (order == null) {
            return returnErrorPageView(model, format(ERROR_ORDER_MESSAGE, orderNumb),
                HOME_LINK + "/get-order-status-form");
        }
        model.addAttribute("order", convector.convertToDto(order));

        return resolveView(ORDER_STATUS_PAGE, model);
    }

    @GetMapping(value = "/get-order-status-form")
    public String getOrderStatusView(Model model) {
        return resolveView(ORDER_STATUS_FORM_PAGE, model);
    }

    @GetMapping(value = "/get-make-order-form")
    public String makeOrderView(Model model) {
        return resolveView(MAKE_ORDER_PAGE, model);
    }

    @GetMapping(value = "/get-cancel-order-form")
    public String cancelOrderView(Model model) {
        return resolveView(CANCEL_ORDER_FORM_PAGE, model);
    }

    @PostMapping(value = "/make-order")
    public RedirectView makeOrder(Model model, WebRequest request) {
        String fullName = resolveFullName(request.getParameter("fullName"));

        Double tripLength = new Random().nextDouble() * 100.0 + 1.0;    // Длина пути случайна
        TaxiOrder madeOrder = taxiService.makeAnOrder(fullName, tripLength).orElse(null);
        if (madeOrder == null) {
            return redirectToErrorPage(ERROR_MAKE_ORDER_MESSAGE, HOME_LINK + "/get-make-order-form");
        }
        return new RedirectView(format(ORDER_STATUS_LINK_PATTERN, madeOrder.getOrderNumber()));
    }

    @PostMapping(value = "/cancel-order")
    public RedirectView cancelOrder(Model model, WebRequest request) {
        Optional<Long> orderId = convertToLong(request.getParameter("orderNumb"));

        if (!orderId.isPresent()) {
            return redirectToErrorPage(ERROR_ORDER_ID_MESSAGE, HOME_LINK + "/get-cancel-order-form");
        }

        if (taxiService.cancelOrder(orderId.get())) {
            return new RedirectView(format(ORDER_STATUS_LINK_PATTERN, orderId.get()));
        }

        return redirectToErrorPage(
            format(ERROR_CANCEL_ORDER_MESSAGE, request.getParameter("orderNumb")), HOME_LINK + "/get-cancel-order-form");
    }

    private String resolveFullName(String var) {
        return isEmpty(var) ? "unknown" : var;
    }
}
