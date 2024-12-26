package com.nhom7.qltd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "Goivay")
public class GoiVay {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private int maGoiVay;

        @Column(name = "name", nullable = false)
        @NotBlank(message = "Vui lòng nhập tên gói vay")
        private String tenGoiVay;
        @Column(name = "BaseInterestRate", nullable = false)
        private float laiSuatCoBan;
        @Column(name = "InterestRate2")
        private float laiSuat2;
        @Column(name = "InterestRate3")
        private float laiSuat3;
        @Column(name = "description1", nullable = false)
        @NotBlank(message = "Vui lòng nhập mô tả 1")
        private String moTa1;
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
    @Column(name = "image")
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "ID")
    private Category category;
}