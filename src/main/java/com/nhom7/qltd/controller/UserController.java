package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.HopDongVay;
import com.nhom7.qltd.model.User;
import com.nhom7.qltd.service.HopdongvayService;
import com.nhom7.qltd.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {
@Autowired
    private final UserService userService;
@Autowired
    private final HopdongvayService hopdongvayService;
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

}
