package edu.harvard.dbmi.avillach.versioner.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
public class WebController {
    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("serverTime", LocalDateTime.now().toString());
        return "index";
    }
}