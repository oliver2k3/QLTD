package com.nhom7.qltd.entity;//package com.nhom7.qltd.API.entity;

import com.nhom7.qltd.model.GoiVay;
import com.nhom7.qltd.model.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "LoanContract")
@Data
public class LoanContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "User_ID", referencedColumnName = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "ID_GoiVay", referencedColumnName = "ID")
    private GoiVay goiVay;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "ID")
    private Status status;

    @Column(name = "LoanAmount")
    private float soTien;
    @Column(name = "InterestRate")
    private float laiSuat;
    @Column(name = "LoanTerm", nullable = false)
    private int thoiHan;
    @Column(name = "EMI")
    private float emi;
    @Column(name = "TotalPayment")
    private float tongTien;
    @Column(name = "TotalInterest")
    private float tongLai;
    @Column(name = "DaTra")
    private float daTra;
    @Column(name = "ConLai")
    private float conLai;
    @Column(name = "LastPayment")
    private LocalDateTime ngayTraGanNhat;
    @Column(name = "NextPayment")
    private LocalDateTime ngayTraTiepTheo;
    @Column(name = "NgayHetHan")
    private LocalDateTime ngayHetHan;
    @Column(name = "SotienKinay")
    private float soTienKinay;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
