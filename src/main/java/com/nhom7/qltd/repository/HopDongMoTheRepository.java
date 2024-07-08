package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.HopDongMoThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HopDongMoTheRepository extends JpaRepository<HopDongMoThe, Integer> {
    List<HopDongMoThe> findAllByUser_Username(String username);
}
