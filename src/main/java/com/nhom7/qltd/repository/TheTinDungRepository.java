package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.TheTinDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheTinDungRepository extends JpaRepository<TheTinDung, Integer> {
}
