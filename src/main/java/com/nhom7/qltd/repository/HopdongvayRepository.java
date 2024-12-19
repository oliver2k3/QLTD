package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.HopDongVay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HopdongvayRepository extends JpaRepository<HopDongVay, Integer> {
    List<HopDongVay> findAllByUser_UsernameOrderByIdDesc(String username);
    List<HopDongVay> findAllByStatusIdOrderByIdDesc(int  statusid);

  //  List<HopDongVay> findByPaymentDeadlineBeforeAndStatusNot(LocalDateTime now, int i);

}
