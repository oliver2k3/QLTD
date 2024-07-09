package com.nhom7.qltd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "HopDongVay")
public class HopDongVay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "fullname", nullable = false)
    @NotBlank(message = "Vui lòng nhập tên")
    private String name;
    @Column(name = "email", nullable = false)
    @NotBlank(message = "Vui lòng nhập email")
    private String email;
    @Column(name = "phone", nullable = false)
    @Size(min = 10, max = 11, message = "Số điện thoại phải từ 10 đến 11 số")
    private String phone;
    @Column(name = "address", nullable = false)
    @NotBlank(message = "Vui lòng nhập địa chỉ")
    private String address;
    @Column(name = "salary", nullable = false)
    @NotNull(message = "Vui lòng nhập mức lương")
    private float salary;
    @Column(name = "maritalStatus")
    private String maritalStatus;
    @Column(name = "job", nullable = false)
    private String job;
    @Column(name = "IndustryName")
    private String industryName;
    @Column(name = "IndustryPosition")
    private String industryPosition;
    @Column(name = "BirthDay")
    private LocalDateTime birthDay;
    @Column(name = "CCCD_Image1")
    private String cccdImage1;
    @Column(name = "CCCD_Image2")
    private String cccdImage2;
    @Column(name = "document_file")
    private String documentFile;
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private User user;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "ID")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "ID")
    private Payment payment;
@OneToOne(cascade = CascadeType.ALL)
    private ChiTietHDV CT_HDV;
}
