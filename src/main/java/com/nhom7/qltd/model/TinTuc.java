package com.nhom7.qltd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "news")
public class TinTuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "title", nullable = false)
    @NotBlank(message = "Vui lòng nhập tiêu đề")
    private String title;
    @Column(name = "image1")
    private String image1;
    @Column(name = "image2")
    private String image2;
    @Column(name = "image3")
    private String image3;
    @Column(name = "paragraph1", nullable = false)
    private String paragraph1;
    @NotBlank(message = "Vui lòng nhập đoạn văn 1")
    @Column(name = "paragraph2")
    private String paragraph2;
    @Column(name = "paragraph3")
    private String paragraph3;
    @Column(name = "paragraph4")
    private String paragraph4;
    @Column(name = "TimeUpload")
    private Date timeUpload;
    @Column(name = "hide")
    private boolean hide;
    @OneToMany(mappedBy = "news")
    List<Comment> comment;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "ID")
    private TinTucCategory category;

}
