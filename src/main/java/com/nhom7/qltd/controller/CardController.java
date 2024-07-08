package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.*;
import com.nhom7.qltd.repository.DeliveryRepository;
import com.nhom7.qltd.service.CardService;
import com.nhom7.qltd.service.HopDongMoTheService;
import com.nhom7.qltd.service.UserService;
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
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {
    @Autowired
    private final CardService cardService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final HopDongMoTheService hopDongMoTheService;
    @Autowired
    private final DeliveryRepository deliveryRepository;
    @GetMapping("")
    public String showCard(Model model) {
        model.addAttribute("cards", cardService.getAllCard());
        return "Card/index";
    }
    @GetMapping("/detail/{id}")
    public String showCardDetail(@PathVariable int id, Model model) {
        TheTinDung card = cardService.getCardById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Card Id:" + id));
        model.addAttribute("cards", card);
        return "Card/detail";
    }
    @GetMapping("/mothe")
    public String FormDangMoThe(Model model ) {
        model.addAttribute("hopdongmothes", new HopDongMoThe());
        model.addAttribute("cards", cardService.getAllCard());
        model.addAttribute("deliveries", deliveryRepository.findAll());
        return "Card/mothe";
    }
    @PostMapping("/mothe")
    public String DangKiMoThe(@Valid HopDongMoThe hopDongMoThe, BindingResult result, @RequestParam MultipartFile CCCD1, @RequestParam MultipartFile CCCD2, @RequestParam MultipartFile docfile) {


        if (result.hasErrors()) {
            return "Card/mothe";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + username));
        hopDongMoThe.setUser(user);
        Status newStatus = new Status();
        newStatus.setId(1);
        hopDongMoThe.setStatus(newStatus);
        hopDongMoTheService.updateCCCD1(hopDongMoThe, CCCD1);
        hopDongMoTheService.updateCCCD2(hopDongMoThe, CCCD2);
        hopDongMoTheService.updatedocfile(hopDongMoThe, docfile);
        hopDongMoTheService.addHDMT(hopDongMoThe);
        ChiTietMoThe chiTietMoThe = hopDongMoThe.getChiTietMoThe();
        chiTietMoThe.setHopDongMoThe(hopDongMoThe);
        hopDongMoThe.setChiTietMoThe(chiTietMoThe);
        hopDongMoTheService.addCTMT(chiTietMoThe);

        return "redirect:/admin/goivay";
    }
}
