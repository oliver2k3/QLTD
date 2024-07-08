package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.*;
import com.nhom7.qltd.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final GoivayService goivayService;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private  final TinTucService tinTucService;
    @Autowired
    private  final TinTucCategoryService tinTucCategoryService;
    @Autowired
    private final CardService cardService;
    @Autowired
    private final UserService UserService;
    @Autowired
    private UserService userService;
    @Autowired
    private HopdongvayService hopdongvayService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ChiTietHDVService chiTietHDVService;
    public AdminController(GoivayService goivayService, CategoryService categoryService, TinTucService tinTucService, TinTucCategoryService tinTucCategoryService, CardService cardService, com.nhom7.qltd.service.UserService userService) {
        this.goivayService = goivayService;
        this.categoryService = categoryService;
        this.tinTucService = tinTucService;
        this.tinTucCategoryService = tinTucCategoryService;
        this.cardService = cardService;
        UserService = userService;
    }
    @GetMapping("")
    public String admin(Model model) {
        return "layoutadmin";
    }
    @GetMapping("/hopdongvay")
    public String goivay(Model model) {
        model.addAttribute("hopdongvays", hopdongvayService.getAllHopdongvay());
        return "admin/hopdongvay/index";
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
    @GetMapping("/tintuc/add")
    public String showAddFormTinTuc(Model model) {
        model.addAttribute("tintucs", new TinTuc());;
        model.addAttribute("categories", tinTucCategoryService.getAllTinTucCategories());
        return "/admin/tintuc/Add";
    }
    @PostMapping("/tintuc/add")
    public String addTintuc(@Valid TinTuc tinTuc, BindingResult result,@RequestParam MultipartFile img1,@RequestParam MultipartFile img2,@RequestParam MultipartFile img3) {
        if (result.hasErrors()) {
            return "/admin/tintuc/Add";
        }
        tinTuc.setHide(false);
        tinTucService.updateImg1(tinTuc, img1);
        tinTucService.updateImg2(tinTuc, img2);
        tinTucService.updateImg3(tinTuc, img3);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No authentication found. User must be logged in to send messages.");
        }
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));

        tinTuc.setUser(user);
        tinTuc.setTimeUpload(LocalDateTime.now());
        tinTuc.setTimeActive(LocalDateTime.now());
        tinTucService.addTinTuc(tinTuc);
        return "redirect:/admin/tintuc";
    }
    @GetMapping("/card/add")
    public String showAddCardForm(Model model) {
        model.addAttribute("cards", new TheTinDung());;
        return "/admin/card/AddCard";
    }
    @PostMapping("/card/add")
    public String addCard(@Valid TheTinDung card, BindingResult result, @RequestParam MultipartFile imageProduct) {
        if (result.hasErrors()) {
            return "/admin/card/AddCard";
        }
        cardService.updateImage(card, imageProduct);
        cardService.addCard(card);
        return "redirect:/admin/card";
    }
    @PostMapping("/hopdongvay/xacnhan/{hdvId}")
    public String xacnhan(@PathVariable Integer hdvId) {
        HopDongVay hopDongVay = hopdongvayService.getHopdongvayById(hdvId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
        ChiTietHDV chiTietHDV = chiTietHDVService.getCTHDVByHdvId(hdvId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
            Status newStatus = statusService.getStatusById(2);
        hopDongVay.setStatus(newStatus);
        chiTietHDV.setNgayTraTiepTheo(LocalDateTime.now().plusMonths(1));
        chiTietHDV.setNgayHetHan(LocalDateTime.now().plusMonths(chiTietHDV.getThoiHan()));
        chiTietHDVService.updateChiTietHDV(chiTietHDV);
        hopdongvayService.updateHopdongvay(hopDongVay);
        return "redirect:/admin/hopdongvay";
    }
    @PostMapping("/hopdongvay/tuchoi/{hdvId}")
    public String tuchoi(@PathVariable Integer hdvId) {
        HopDongVay hopDongVay = hopdongvayService.getHopdongvayById(hdvId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
        Status newStatus = statusService.getStatusById(5);
        hopDongVay.setStatus(newStatus);
        hopdongvayService.updateHopdongvay(hopDongVay);
        return "redirect:/admin/hopdongvay";
    }
}
