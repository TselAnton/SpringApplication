package com.tsel.app.controller;

import static com.tsel.app.service.CashService.PUBLIC_EXPENSES_KEY;
import static com.tsel.app.service.CashService.PUBLIC_PROFIT_KEY;
import static com.tsel.app.service.CashService.TAXI_EXPENSES_KEY;
import static com.tsel.app.service.CashService.TAXI_PROFIT_KEY;
import static java.lang.String.format;

import com.tsel.app.service.CashService;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cash-info")
public class CashInfoController extends AbstractController {

    private static final String HOME_DIR = "cash-info";
    private static final String HOME_PAGE = "cash-info-home-page";
    private static final String OUT_PAGE = "cash-info-out-page";
    private static final String ERROR_MESSAGE = "Неверно выбраны даты, попробуйте ещё раз";

    private static final String DECIMAL_FORMAT = "###.###.###.###, ##";

    public CashInfoController() {
        super(HOME_DIR);
    }

    @Autowired
    private CashService cashService;

    @GetMapping
    public String getHomePageView(Model model) {
        return resolveView(HOME_PAGE, model);
    }

    @GetMapping(value = "/get-cash-info")
    public String getCashInfoHomeView(
        @RequestParam("fromDate") String fromDate,
        @RequestParam("byDate") String byDate, Model model) {

        LocalDate startPeriod = parseStringToLocalDate(fromDate).orElse(null);
        LocalDate endPeriod = parseStringToLocalDate(byDate).orElse(null);

        if (startPeriod == null || endPeriod == null) {
            return returnErrorPageView(model, ERROR_MESSAGE, HOME_DIR);
        }

        Map<String, Double> incoming = cashService.getIncomeForPeriod(startPeriod, endPeriod);

        if (incoming.isEmpty()) {
            return returnErrorPageView(model, ERROR_MESSAGE, HOME_DIR);
        }

        Double sumProfit = incoming.get(TAXI_PROFIT_KEY) + incoming.get(PUBLIC_PROFIT_KEY);
        Double sumExpenses = incoming.get(TAXI_EXPENSES_KEY) + incoming.get(PUBLIC_EXPENSES_KEY);

        model.addAttribute("publicProfit", customFormat(incoming.get(PUBLIC_PROFIT_KEY)));
        model.addAttribute("publicExpenses", customFormat(incoming.get(PUBLIC_EXPENSES_KEY)));
        model.addAttribute("taxiProfit", customFormat(incoming.get(TAXI_PROFIT_KEY)));
        model.addAttribute("taxiExpenses", customFormat(incoming.get(TAXI_EXPENSES_KEY)));
        model.addAttribute("sumProfit", customFormat(sumProfit));
        model.addAttribute("sumExpenses", customFormat(sumExpenses));
        model.addAttribute("sumAll", customFormat(sumProfit - sumExpenses));

        return resolveView(OUT_PAGE, model);
    }

    static public String customFormat(double value) {
        return format("%.2f", value);
    }
}
