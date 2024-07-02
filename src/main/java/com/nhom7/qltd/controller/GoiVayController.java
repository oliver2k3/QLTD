package com.nhom7.qltd.controller;

import com.nhom7.qltd.service.GoivayService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goivay")
public class GoiVayController {
    @Autowired
    private final GoivayService goivayService;

    public GoiVayController(GoivayService goivayService) {
        this.goivayService = goivayService;
    }
    @GetMapping("")
    public String goivay(Model model) {
        model.addAttribute("goivays", goivayService.getAllGoivay());
        return "goivay/index";
    }
    @GetMapping("/detail/{id}")
    public String goivayDetail(@PathVariable int id, Model model) {
        model.addAttribute("goivays", goivayService.getGoivayById(id));
        return "goivay/detail";
    }

}
