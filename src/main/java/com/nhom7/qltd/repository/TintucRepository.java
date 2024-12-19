package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.TinTuc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TintucRepository extends JpaRepository<TinTuc, Integer>{
    List<TinTuc> findByHideFalseOrderByTimeActiveDesc();
    List<TinTuc> findTop3ByHideFalseOrderByTimeActiveDesc(Pageable pageable);
    List<TinTuc> findByCategoryIdAndHideFalseOrderByTimeActive(Integer categoryId);
    List<TinTuc> findByHideTrueOrderByTimeActiveDesc();
    List<TinTuc> findTinTucsByUserUsername(String username);
}
