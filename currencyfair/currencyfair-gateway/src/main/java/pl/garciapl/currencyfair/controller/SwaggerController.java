package pl.garciapl.currencyfair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lukasz on 18.05.15.
 */
@Controller
public class SwaggerController {

    @RequestMapping(value = "/swagger", method = RequestMethod.GET)
    public String swagger() {
        return "swagger";
    }

}
