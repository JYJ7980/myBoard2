package com.example.queryMethodTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutTestController {
    @GetMapping("/test")
    public String testView(Model model){
        model.addAttribute("name", "지영준");
        return "articles/show_all";
    }

}
