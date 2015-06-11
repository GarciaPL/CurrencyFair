package pl.garciapl.currencyfair.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.garciapl.currencyfair.controller.domain.LoginForm;

@Controller
public class HomeController {

    @Value("#{propSource.currencyfairUser}")
    public String currencyFairUser;

    @Value("#{propSource.currencyfairPassword}")
    private String currencyFairPassword;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        return "Hello CurrencyFair!";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginProcess(LoginForm loginForm, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("message", "Wrong login or password");
            return null;
        }

        if (loginForm.getUsername().equals(currencyFairUser) && loginForm.getPassword().equals(currencyFairPassword)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(currencyFairUser, currencyFairPassword);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:index";
        } else {
            model.addAttribute("message", "Wrong login or password. Please try again.");
            return null;
        }
    }
}
