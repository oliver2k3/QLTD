package com.nhom7.qltd.service;

import com.nhom7.qltd.model.TinTucCategory;
import com.nhom7.qltd.repository.TinTucCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TinTucCategoryService {
    private final TinTucCategoryRepository tinTucCategoryRepository;
    public List<TinTucCategory> getAllTinTucCategories(){
        return tinTucCategoryRepository.findAll();
    }
}
