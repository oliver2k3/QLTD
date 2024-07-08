package com.nhom7.qltd.controller;

import com.nhom7.qltd.model.*;
import com.nhom7.qltd.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller

public class VNPayController {
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private ChiTietHDVService chiTietHDVService;
    @Autowired
    private HopdongvayService hopdongvayService;
    @Autowired
    private  StatusService statusService;
    @Autowired
    private ChiTietMoTheService chiTietMoTheService;
    @Autowired
    private HopDongMoTheService hopDongMoTheService;
    @GetMapping("/vnpay")
    public String home(){
        return "Vnpay/index";
    }
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, HttpSession session){
        int paymentStatus =vnPayService.orderReturn(request);
        int hdvId = (int) session.getAttribute("hdvId");
        if (paymentStatus == 1) {
            ChiTietHDV chiTietHDV = chiTietHDVService.getCTHDVByHdvId(hdvId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
            HopDongVay hopDongVay = hopdongvayService.getHopdongvayById(hdvId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdvId));
            if (hopDongVay.getStatus() == null) {
                Status newStatus = new Status();
                newStatus.setId(4);
                hopDongVay.setStatus(newStatus);
            } else {
                Status newStatus = statusService.getStatusById(4);
                hopDongVay.setStatus(newStatus);
            }
            chiTietHDV.setDaTra(chiTietHDV.getDaTra() + chiTietHDV.getEmi());
            chiTietHDV.setConLai(chiTietHDV.getTongTien() - chiTietHDV.getDaTra());
            chiTietHDV.setNgayTraGanNhat(LocalDateTime.now());
            chiTietHDV.setNgayTraTiepTheo(chiTietHDV.getNgayTraTiepTheo().plusMonths(1));
            chiTietHDVService.updateChiTietHDV(chiTietHDV);
        }
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "Vnpay/ordersuccess" : "Vnpay/orderfail";
    }
    @GetMapping("/vnpay-payment2")
    public String GetMapping2(HttpServletRequest request, Model model, HttpSession session) {
        int paymentStatus =vnPayService.orderReturn(request);
        int hdmtId = (int) session.getAttribute("hdmtId");
        if (paymentStatus == 1) {
            ChiTietMoThe chiTietMoThe = chiTietMoTheService.getChiTietMoTheByHDMTId(hdmtId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdmtId));
            HopDongMoThe hopDongMoThe = hopDongMoTheService.getHopDongMoTheById(hdmtId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + hdmtId));
            if (hopDongMoThe.getStatus() == null) {
                Status newStatus = new Status();
                newStatus.setId(4);
                hopDongMoThe.setStatus(newStatus);
            } else {
                Status newStatus = statusService.getStatusById(4);
                hopDongMoThe.setStatus(newStatus);
            }
            chiTietMoThe.setSoDu(chiTietMoThe.getGioiHan());
            chiTietMoThe.setDaSuDung(0);
            chiTietMoThe.setPaymentDate(LocalDateTime.now());
            chiTietMoThe.setNextPaymentTime(chiTietMoThe.getNextPaymentTime().plusMonths(1));
            chiTietMoTheService.updateChiTietMoThe(chiTietMoThe);
        }
        return paymentStatus == 1 ? "Vnpay/ordersuccess" : "Vnpay/orderfail";
    }

}
