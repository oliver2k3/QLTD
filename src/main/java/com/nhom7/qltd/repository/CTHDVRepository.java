package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.ChiTietHDV;
import com.nhom7.qltd.model.HopDongVay;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CTHDVRepository extends JpaRepository<ChiTietHDV, Integer> {
    Optional<ChiTietHDV> findByHopDongVayId(int hdvId);
}
