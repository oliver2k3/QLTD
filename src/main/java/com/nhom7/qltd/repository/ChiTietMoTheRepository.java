package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.ChiTietHDV;
import com.nhom7.qltd.model.ChiTietMoThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChiTietMoTheRepository extends JpaRepository<ChiTietMoThe, Integer> {
    Optional<ChiTietMoThe> findByHopDongMoTheId(int id);
}
