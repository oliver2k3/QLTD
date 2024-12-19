package com.nhom7.qltd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "status")
    private List<HopDongVay> hopDongVays;
    @OneToMany(mappedBy = "status")
    private List<HopDongMoThe> hopDongMoThes;

}
