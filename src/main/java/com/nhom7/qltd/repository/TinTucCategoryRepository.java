package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.TinTucCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TinTucCategoryRepository  extends JpaRepository<TinTucCategory, Integer> {
}
