package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.*;
import com.nhom7.qltd.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {
@Autowired
    private final UserService userService;
@Autowired
    private final HopdongvayService hopdongvayService;
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private ChiTietHDVService chiTietHDVService;
    @Autowired
    private HopDongMoTheService hopDongMoTheService;
    @Autowired
    private ChiTietMoTheService chiTietMoTheService;
    @Autowired
    private TinTucService tinTucService;
    @Autowired
    private TinTucCategoryService tinTucCategoryService;
@GetMapping("/profile")
    public String profile( Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));
    model.addAttribute("users", user);
    return "users/profile";
}
@GetMapping("/profile/edit")
    public String editProfile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));
    model.addAttribute("users", user);
    return "users/editProfile";
}
@GetMapping("/hopdongvay")
    public String hopdongvay(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));
    List<HopDongVay> hopdongvays = hopdongvayService.getAllHopdongvayByUser(username);
    model.addAttribute("hopdongvays", hopdongvays);
    return "users/hopdongvay";

}
    @GetMapping("/hopdongmothe")
    public String hopdongmothe(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));
        List<HopDongMoThe> hopDongMoThes = hopDongMoTheService.getAllHDMTByUser(username);
        model.addAttribute("hopdongmothes", hopDongMoThes);
        return "users/hopdongmothe";

    }
@PostMapping("/payment/{hdvId}")
    public  String thanhtoanVNPay(@PathVariable int hdvId, Model model, HttpServletRequest request, HttpSession session){
    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    ChiTietHDV chiTietHDV = chiTietHDVService.getCTHDVByHdvId(hdvId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
    int orderTotal = (int) chiTietHDV.getEmi();
    String orderInfo = "Thanh toan hop dong vay voi id: " + hdvId;
    String vnpayUrl = vnPayService.createOrder(orderTotal,orderInfo, baseUrl);
    session.setAttribute("hdvId", hdvId);
    return "redirect:" + vnpayUrl;
    }
    @PostMapping("/paymentmothe/{id}")
    public  String thanhtoanVNPayThe(@PathVariable int id, Model model, HttpServletRequest request, HttpSession session){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        ChiTietMoThe chiTietMoThe = chiTietMoTheService.getChiTietMoTheByHDMTId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        int orderTotal = (int) chiTietMoThe.getDaSuDung();
        String orderInfo = "Thanh toan the tin dung voi id: " + id;
        String vnpayUrl = vnPayService.createOrder2(orderTotal,orderInfo, baseUrl);
        session.setAttribute("hdmtId", id);
        return "redirect:" + vnpayUrl;
    }
    @GetMapping("/tintuc/add")
    public String showAddFormTinTuc(Model model) {
        model.addAttribute("tintucs", new TinTuc());;
        model.addAttribute("categories", tinTucCategoryService.getAllTinTucCategories());
        return "/users/Addtintuc";
    }
    @PostMapping("/tintuc/add")
    public String addTintuc(@Valid TinTuc tinTuc, BindingResult result, @RequestParam MultipartFile img1, @RequestParam MultipartFile img2, @RequestParam MultipartFile img3) {
        if (result.hasErrors()) {
            return  "/users/Addtintuc";
        }
        tinTuc.setHide(true);
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
        tinTucService.addTinTuc(tinTuc);
        return "redirect:/user/tintuc";
    }




}
