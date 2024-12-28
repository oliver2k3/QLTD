package com.nhom7.qltd.mobile_service;

import com.nhom7.qltd.dao.LoanContractDAO;
import com.nhom7.qltd.dto.LoanDto;
import com.nhom7.qltd.entity.LoanContract;
import com.nhom7.qltd.entity.UserEntity;
import com.nhom7.qltd.model.GoiVay;
import com.nhom7.qltd.model.Status;
import com.nhom7.qltd.service.GoivayService;
import com.nhom7.qltd.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanContractDAO loanContractDAO;
    private final UsersService userService;
    private final GoivayService goiVayService;
    private final StatusService statusService;

    public void createLoan(LoanDto loanDto) {
        UserEntity user = userService.getUserByEmail(loanDto.getEmail());
        Optional<GoiVay> goiVay = goiVayService.getGoivayById(loanDto.getGoiVayId());
        Status status = statusService.getStatusById(1);

        LoanContract loanContract = new LoanContract();
        loanContract.setUser(user);
        goiVay.ifPresent(loanContract::setGoiVay);        loanContract.setStatus(status);
        loanContract.setSoTien(loanDto.getLoanAmount());
        loanContract.setLaiSuat(loanDto.getInterestRate());
        loanContract.setThoiHan(loanDto.getLoanDuration());
        loanContract.setCreatedDate(LocalDateTime.now());
        loanContract.setNgayHetHan(LocalDateTime.now().plusMonths(loanDto.getLoanDuration()));
        float monthlyInterest =  (loanDto.getInterestRate() / 100 / 12);
        float tongLai = loanContract.getSoTien() * monthlyInterest * loanContract.getThoiHan();
        float tongTien = loanContract.getSoTien() + tongLai;
        float emi = tongTien / loanContract.getThoiHan();
        loanContract.setTongLai(tongLai);
        loanContract.setTongTien(tongTien);
        loanContract.setEmi(emi);
        loanContract.setDaTra(0);
        loanContract.setConLai(tongTien);
        loanContractDAO.save(loanContract);
    }
}