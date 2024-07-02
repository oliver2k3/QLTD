package com.nhom7.qltd.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "message", nullable = false)
    private String message;
   @Column(name = "job", nullable = false)
    private String job;
}
