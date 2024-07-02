package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.GoiVay;
import com.nhom7.qltd.service.CategoryService;
import com.nhom7.qltd.service.GoivayService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final GoivayService goivayService;
    @Autowired
    private final CategoryService categoryService;
    public AdminController(GoivayService goivayService, CategoryService categoryService) {
        this.goivayService = goivayService;
        this.categoryService = categoryService;
    }
    @GetMapping("/goivay/add")
    public String showAddForm(Model model) {
        model.addAttribute("loans", new GoiVay());;
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/goivay/AddGoiVay";
    }
    @PostMapping("/goivay/add")
    public String addCourse(@Valid GoiVay goiVay, BindingResult result,@RequestParam MultipartFile imageProduct) {
        if (result.hasErrors()) {
            return "/admin/goivay/AddGoiVay";
        }
        goivayService.updateImage(goiVay, imageProduct);
        goivayService.addGoivay(goiVay);
        return "redirect:/admin/goivay";
    }
}
