package happy.module.common.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Home Controller
 * */
@Controller
@RequestMapping(value = "/")
public class HomeController {

    @RequestMapping(value = "")
    public String index() {
        return "redirect:html-modules/home.html";
    }
}
