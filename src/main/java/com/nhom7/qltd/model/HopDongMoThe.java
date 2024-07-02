package com.nhom7.qltd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "HD_MoThe")
public class HopDongMoThe {
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
    @NotBlank(message = "Vui lòng nhập mức lương")
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
    private Date birthDay;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "ID")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "ID")
    private Delivery delivery;
    @OneToOne
    private ChiTietMoThe chiTietMoThe;

}
