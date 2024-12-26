package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.*;
import com.nhom7.qltd.service.GoivayService;
import com.nhom7.qltd.service.HopdongvayService;
import com.nhom7.qltd.service.UserService;
import jakarta.persistence.GeneratedValue;
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

@Controller
@RequestMapping("/goivay")

public class GoiVayController {
    @Autowired
    private final GoivayService goivayService;
    @Autowired
    private final HopdongvayService hopdongvayService;
    @Autowired
    private final UserService userService;
    public GoiVayController(GoivayService goivayService, HopdongvayService hopdongvayService, UserService userService) {
        this.goivayService = goivayService;
        this.hopdongvayService = hopdongvayService;
        this.userService = userService;
    }
    @GetMapping("")
    public String goivay(Model model) {
        model.addAttribute("goivays", goivayService.getAllGoivay());
        return "goivay/index";
    }
    @GetMapping("/detail/{id}")
    public String goivayDetail(@PathVariable int id, Model model) {
        GoiVay goiVay = goivayService.getGoivayById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Course Id:" + id));
        model.addAttribute("goivays", goiVay);
        return "goivay/detail";
    }
    @GetMapping("/dangkivay")
    public String FormDangKiVay(Model model ) {
        model.addAttribute("hopdongvays", new HopDongVay());
        model.addAttribute("goivays", goivayService.getAllGoivay());
        return "goivay/dangkivay";
    }
    @PostMapping("/dangkivay")
    public String DangKiVay(@Valid HopDongVay hopDongVay, BindingResult result, @RequestParam MultipartFile CCCD1, @RequestParam MultipartFile CCCD2, @RequestParam MultipartFile docfile) {


        if (result.hasErrors()) {
            return "goivay/dangkivay";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));
       hopDongVay.setUser(user);
        Status newStatus = new Status();
        newStatus.setId(1);
        hopDongVay.setStatus(newStatus);
        hopdongvayService.updateCCCD1(hopDongVay, CCCD1);
        hopdongvayService.updateCCCD2(hopDongVay, CCCD2);
        hopdongvayService.updatedocfile(hopDongVay, docfile);
        hopdongvayService.addHopdongvay(hopDongVay);
        ChiTietHDV chiTietHDV = hopDongVay.getCT_HDV();
        chiTietHDV.setHopDongVay(hopDongVay);
        hopDongVay.setCT_HDV(chiTietHDV);
        float interest =  chiTietHDV.getGoiVay().getLaiSuatCoBan();
        float interest2 = chiTietHDV.getGoiVay().getLaiSuat2();
        float interest3 =chiTietHDV.getGoiVay().getLaiSuat3();
        if(chiTietHDV.getThoiHan() >= 1 && chiTietHDV.getThoiHan() <= 11)
        {
            chiTietHDV.setLaiSuat(interest);
        }
        else if(chiTietHDV.getThoiHan() >= 12 && chiTietHDV.getThoiHan() <= 17)
        {
            chiTietHDV.setLaiSuat(interest2);
        }
        else if(chiTietHDV.getThoiHan() >= 18 && chiTietHDV.getThoiHan() <= 24)
        {
            chiTietHDV.setLaiSuat(interest3);
        }
        float monthlyInterest =   (chiTietHDV.getLaiSuat() / 100 / 12);
        float tongLai = chiTietHDV.getSoTien() * monthlyInterest * chiTietHDV.getThoiHan();
        float tongTien = chiTietHDV.getSoTien() + tongLai;
        float emi = tongTien / chiTietHDV.getThoiHan();
        chiTietHDV.setTongLai(tongLai);
        chiTietHDV.setTongTien(tongTien);
        chiTietHDV.setEmi(emi);
        chiTietHDV.setDaTra(0);
        chiTietHDV.setConLai(tongTien);
        hopdongvayService.addChiTietHDV(chiTietHDV);

        return "redirect:/user/hopdongvay";

    }

}
