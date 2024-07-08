package com.nhom7.qltd.service;

import com.nhom7.qltd.model.Category;
import com.nhom7.qltd.model.TinTuc;
import com.nhom7.qltd.model.TinTucCategory;
import com.nhom7.qltd.repository.CategoryRepository;
import com.nhom7.qltd.repository.TintucRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private  final CategoryRepository categoryRepository;
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
