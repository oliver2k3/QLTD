package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.GoiVay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  GoivayRepository extends JpaRepository<GoiVay, Integer> {

}
