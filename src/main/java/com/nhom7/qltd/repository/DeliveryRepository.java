package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
