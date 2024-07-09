package com.nhom7.qltd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "user_name")
    private User user;
    @ManyToOne
    @JoinColumn(name = "admin", referencedColumnName = "user_name")
    private User admin;
    @Column(name = "message", nullable = false)
    private String message;
  @Column(name = "SendTime")
    private LocalDateTime sendTime;
}
