package com.nhom7.qltd.controller;


import com.nhom7.qltd.model.Contact;
import com.nhom7.qltd.model.TinTuc;
import com.nhom7.qltd.service.TinTucCategoryService;
import com.nhom7.qltd.service.TinTucService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/tintuc")
public class TintucController {
private final TinTucService tintucService;
private  final TinTucCategoryService tinTucCategoryService;

    public TintucController(TinTucService tintucService, TinTucCategoryService tinTucCategoryService) {
        this.tintucService = tintucService;
        this.tinTucCategoryService = tinTucCategoryService;
    }
    @GetMapping("")
    public String tintuc(Model model) {
        model.addAttribute("categories", tinTucCategoryService.getAllTinTucCategories());
        model.addAttribute("news", tintucService.getActiveNews());
        return "tintuc/index";
    }
   @GetMapping("/")
    public String getPostsByCategory(@RequestParam("categoryId") Integer categoryId, Model model) {
        List<TinTuc> posts = tintucService.getPostsByCategory(categoryId);
       model.addAttribute("categories", tinTucCategoryService.getAllTinTucCategories());
        model.addAttribute("news", tintucService.getPostsByCategory(categoryId));
        return "tintuc/index2";
    }
}
