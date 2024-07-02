package com.nhom7.qltd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "CT_HDV")
public class ChiTietHDV {
    @Id
    @OneToOne
    private HopDongVay hopDongVay;
    @ManyToOne
    @JoinColumn(name = "ID_GoiVay", referencedColumnName = "ID")
    private GoiVay goiVay;
    @Column(name = "LoanAmount", nullable = false)
    private float soTien;
    @Column(name = "InterestRate", nullable = false)
    private float laiSuat;
    @Column(name = "LoanTerm", nullable = false)
    private int thoiHan;
    @Column(name = "EMI", nullable = false)
    private float emi;
    @Column(name = "TotalPayment", nullable = false)
    private float tongTien;
    @Column(name = "TotalInterest", nullable = false)
    private float tongLai;
    @Column(name = "DaTra")
    private float daTra;
    @Column(name = "ConLai")
    private float conLai;
    @Column(name = "LastPayment")
    private Date ngayTraGanNhat;
    @Column(name = "NextPayment")
    private Date ngayTraTiepTheo;
    @Column(name = "To_Bank")
    private String tenNganHang;
    @Column(name = "AccountNumber")
    private String soTaiKhoan;
    @Column(name = "AccountName")
    private String tenTaiKhoan;
    @Column(name = "NgayHetHan")
    private Date ngayHetHan;
}
