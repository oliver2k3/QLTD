package com.nhom7.qltd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "content", nullable = false)
    @NotBlank(message = "Vui lòng nhập nội dung")
    private String content;
    @Column(name = "TimeUpload")
    private LocalDateTime timeUpload;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "ID")
    private TinTuc news;


}
