package pl.garciapl.currencyfair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lukasz on 18.05.15.
 */
@Controller
public class GraphController {

    @RequestMapping(value = "/volumegraph", method = RequestMethod.GET)
    public String volumeGraph() {
        return "volume";
    }

    @RequestMapping(value = "/currencysumgraph", method = RequestMethod.GET)
    public String sumCurrencyGraph() {
        return "currencysum";
    }

    @RequestMapping(value = "/countriessumgraph", method = RequestMethod.GET)
    public String countriesSumGraph() { return "countriessum"; }

    @RequestMapping(value = "/currenciespairsgraph", method = RequestMethod.GET)
    public String currenciesParisGraph() { return "currenciespairs"; }

    @RequestMapping(value = "/eurograph", method = RequestMethod.GET)
    public String euroSumGraph() { return "euro"; }
}
