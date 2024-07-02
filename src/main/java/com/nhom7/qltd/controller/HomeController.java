package com.nhom7.qltd.controller;

import com.nhom7.qltd.service.GoivayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

public class HomeController {
    @Autowired
    private final GoivayService goivayService;

    public HomeController(GoivayService goivayService) {
        this.goivayService = goivayService;
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("goivays", goivayService.getAllGoivay());
        return "home/index";
    }
    @GetMapping("/home")
    public String home2() {
        return "layout";
    }
}