package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.*;
import com.nhom7.qltd.service.*;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    @Autowired
    private HopDongMoTheService hopDongMoTheService;
    @Autowired
    private ChiTietMoTheService chiTietMoTheService;
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
    public String hopdongvay(Model model) {
        model.addAttribute("hopdongvays", hopdongvayService.getAllHopdongvay());
        return "admin/hopdongvay/index";
    }
    @GetMapping("/hopdongvay1")
    public String hopdongvaychuaxacnhan(Model model) {

        model.addAttribute("hopdongvays", hopdongvayService.getHDVchuaxacnhan());
        return "admin/hopdongvay/index";
    }
    @GetMapping("/hopdongvay2")
    public String hopdongvaychuathanhtoan(Model model) {

        model.addAttribute("hopdongvays", hopdongvayService.getHDVchuathanhtoan());
        return "admin/hopdongvay/index";
    }
    @GetMapping("/hopdongvay3")
    public String hopdongvaydatuchoi(Model model) {

        model.addAttribute("hopdongvays", hopdongvayService.getHDVdatuchoi());
        return "admin/hopdongvay/index";
    }
    @GetMapping("/goivay")
    public String goivay(Model model) {
        model.addAttribute("goivays", goivayService.getAllGoivay());
        return "admin/goivay/index";
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
    @GetMapping("/goivay/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        GoiVay goiVay = goivayService.getGoivayById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Goivay Id:" + id));
        model.addAttribute("goivays", goiVay);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/goivay/Updategoivay";
    }
    @PostMapping("/goivay/edit/{id}")
    public String updateCourse(@PathVariable int id, @Valid GoiVay goivays, BindingResult result,@RequestParam MultipartFile imageProduct) {
        if (result.hasErrors()) {
            goivays.setMaGoiVay(id);
            return "/admin/goivay/Updategoivay";
        }
        goivayService.updateImage(goivays, imageProduct);
        goivayService.updateGoivay(goivays);
        return "redirect:/admin/goivay";
    }
    @GetMapping("/tintuc")
    public String tintuc(Model model) {
        model.addAttribute("tintucs", tinTucService.getAllTinTuc());
        return "admin/tintuc/index";
    }
    @GetMapping("/tintuc2")
    public String tintuc2(Model model) {
        model.addAttribute("tintucs", tinTucService.getActiveNews());
        return "admin/tintuc/index";
    }
    @GetMapping("/tintuc3")
    public String tintuc3(Model model) {
        model.addAttribute("tintucs", tinTucService.getHideNews());
        return "admin/tintuc/index";
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
    @GetMapping("/card")
    public String card(Model model) {
        model.addAttribute("cards", cardService.getAllCard());
        return "admin/card/index";
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
    @PostMapping("/hopdongvay/editstatus/{id}")
    public String editstatus(@PathVariable Integer id, @RequestParam Integer status) {
        HopDongVay hopDongVay = hopdongvayService.getHopdongvayById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        Status newStatus = statusService.getStatusById(status);
        hopDongVay.setStatus(newStatus);
        hopdongvayService.updateHopdongvay(hopDongVay);
        return "redirect:/admin/hopdongvay";
    }
    @GetMapping("/download/{docfile}")
    public void downloadFile(HttpServletResponse response, @PathVariable String docfile) {
        // Base directory where the files are located
        String baseDir = "static/files/hopdongvay/";

        // Construct the file path
        Path filePath = Paths.get(baseDir + docfile);

        // Set the content type and header for file download
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath.getFileName().toString() + "\"");

        // Read the file and write it to the response's output stream
        try {
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error here
        }
    }
    @GetMapping("/hopdongmothe/taothe/{id}")
    public String taothe(Model model, @PathVariable Integer id) {
        ChiTietMoThe chiTietMoThe = chiTietMoTheService.getChiTietMoTheByHDMTId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
       model.addAttribute("ctmt", chiTietMoThe);
        return "admin/hopdongmothe/taothe";
    }
    @PostMapping("/hopdongmothe/taothe/{id}")
    public String taothe2(@PathVariable Integer id, @RequestParam String SoThe, @RequestParam String TenTrenThe, @RequestParam int Ccv,@RequestParam int cardMonth,@RequestParam int cardYear,@RequestParam float CardLimit) {
        ChiTietMoThe chiTietMoThe = chiTietMoTheService.getChiTietMoTheByHDMTId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        chiTietMoThe.setSoThe(SoThe);
        chiTietMoThe.setTenKH(TenTrenThe);
        chiTietMoThe.setCcv(Ccv);
        chiTietMoThe.setGioiHan(CardLimit);
        chiTietMoThe.setNgayMoThe(LocalDateTime.now());
        chiTietMoThe.setNgayHetHan(calculateExpirationDate(cardMonth,cardYear));
        chiTietMoTheService.updateChiTietMoThe(chiTietMoThe);
        return "redirect:/admin/hopdongmothe";
    }
    @GetMapping("/hopdongmothe")
    public String hopdongmothe(Model model) {
        model.addAttribute("hopdongmothes", hopDongMoTheService.getAllHDMT());
        return "admin/hopdongmothe/index";
    }
    @GetMapping("/hopdongmothe1")
    public String hopdongmothechuaxacnhan(Model model) {

        model.addAttribute("hopdongmothes", hopDongMoTheService.getHDMTchuaxacnhan());
        return "admin/hopdongmothe/index";
    }
    @GetMapping("/hopdongmothe2")
    public String hopdongmothechuathanhtoan(Model model) {

        model.addAttribute("hopdongmothes", hopDongMoTheService.getHDMTchuathanhtoan());
        return "admin/hopdongmothe/index";
    }
    @GetMapping("/hopdongmothe3")
    public String hopdongmothedatuchoi(Model model) {

        model.addAttribute("hopdongmothes", hopDongMoTheService.getHDMTdatuchoi());
        return "admin/hopdongmothe/index";
    }
    @PostMapping("/hopdongmothe/xacnhan/{hdvId}")
    public String xacnhan2(@PathVariable Integer hdvId) {
        HopDongMoThe hopDongMoThe = hopDongMoTheService.getHopDongMoTheById(hdvId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
        ChiTietMoThe chiTietMoThe = chiTietMoTheService.getChiTietMoTheByHDMTId(hdvId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
        Status newStatus = statusService.getStatusById(2);
        hopDongMoThe.setStatus(newStatus);
        chiTietMoThe.setNextPaymentTime(LocalDateTime.now().plusMonths(1));

        chiTietMoTheService.updateChiTietMoThe(chiTietMoThe);
        hopDongMoTheService.updateHDMT(hopDongMoThe);
        return "redirect:/admin/hopdongmothe";
    }
    @PostMapping("/hopdongmothe/tuchoi/{hdvId}")
    public String tuchoi2(@PathVariable Integer hdvId) {
        HopDongMoThe hopDongMoThe = hopDongMoTheService.getHopDongMoTheById(hdvId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
        Status newStatus = statusService.getStatusById(5);
        hopDongMoThe.setStatus(newStatus);
        hopDongMoTheService.updateHDMT(hopDongMoThe);
        return "redirect:/admin/hopdongmothe";
    }
    @PostMapping("/hopdongmothe/editstatus/{id}")
    public String editstatus2(@PathVariable Integer id, @RequestParam Integer status) {
        HopDongMoThe hopDongMoThe = hopDongMoTheService.getHopDongMoTheById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        Status newStatus = statusService.getStatusById(status);
        hopDongMoThe.setStatus(newStatus);
        hopDongMoTheService.updateHDMT(hopDongMoThe);
        return "redirect:/admin/hopdongmothe";
    }
    @GetMapping("/download2/{docfile}")
    public void downloadFile2(HttpServletResponse response, @PathVariable String docfile) {
        // Base directory where the files are located
        String baseDir = "static/files/hopdongmothe/";

        // Construct the file path
        Path filePath = Paths.get(baseDir + docfile);

        // Set the content type and header for file download
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath.getFileName().toString() + "\"");

        // Read the file and write it to the response's output stream
        try {
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error here
        }
    }
    public LocalDateTime calculateExpirationDate(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        LocalDate expirationDate = LocalDate.of(year, month, lastDayOfMonth);
        LocalDateTime expirationDateTime = expirationDate.atStartOfDay();
        return expirationDateTime;
    }
}
