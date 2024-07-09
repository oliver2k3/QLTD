package com.nhom7.qltd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "CT_HDMT")
public class ChiTietMoThe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @OneToOne
    @JoinColumn(name = "ID_HDMT", referencedColumnName = "ID")
    private HopDongMoThe hopDongMoThe;
    @ManyToOne
    @JoinColumn(name = "ID_Card", referencedColumnName = "ID")
    private TheTinDung card;
    @Column(name = "TenKH")
    private String tenKH;
    @Column(name = "SoThe")
    private String soThe;
    @Column(name = "NgayMoThe")
    private LocalDateTime ngayMoThe;
    @Column(name = "NgayHetHan")
    private LocalDateTime ngayHetHan;
    @Column(name = "SoDu")
    private float soDu;
    @Column(name = "CCV")
    private int ccv;
    @Column(name = "DaSuDung")
    private float daSuDung;
    @Column(name = "GioiHan")
    private float gioiHan;
    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;
    @Column(name = "NextPayment")
    private LocalDateTime nextPaymentTime;


}
