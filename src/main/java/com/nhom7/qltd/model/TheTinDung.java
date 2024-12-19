package com.nhom7.qltd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "Card")
public class TheTinDung {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private int maThe;
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Vui lòng nhập tên thẻ")
    private String tenThe;
    @Column(name= "image")
    private String image;
    @Column(name = "description1", nullable = false)
    private String moTa1;
    @NotBlank(message = "Vui lòng nhập mô tả 1")
    @Column(name = "description2")
    private String moTa2;
    @Column(name = "description3")
    private String moTa3;
    @Column(name = "description4")
    private String moTa4;
    @Column(name = "description5")
    private String moTa5;
    @Column(name = "description6")
    private String moTa6;

}
