package com.nhom7.qltd.service;

import com.nhom7.qltd.model.HopDongVay;
import com.nhom7.qltd.model.TinTuc;
import com.nhom7.qltd.repository.TintucRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TinTucService {
    private final TintucRepository tinTucRepository;
    public List<TinTuc> getAllTinTuc(){
        return tinTucRepository.findAll();
    }
    public Optional<TinTuc> getTinTucById(Integer id) {
        return tinTucRepository.findById(id);
    }
    public TinTuc saveTinTuc(TinTuc tinTuc) {
        return tinTucRepository.save(tinTuc);
    }
    public List<TinTuc> getTop3TinTuc() {
        return tinTucRepository.findTop3ByHideFalseOrderByTimeActiveDesc(PageRequest.of(0, 3));
    }
    public List<TinTuc> getActiveNews(){
        return tinTucRepository.findByHideFalseOrderByTimeActiveDesc();
    }
    public List<TinTuc> getHideNews(){
        return tinTucRepository.findByHideTrueOrderByTimeActiveDesc();
    }
    public List<TinTuc> findTinTucsByUser(String username) {
        return tinTucRepository.findTinTucsByUserUsername(username);
    }
    public void deleteTintucById(int id) {
        if (!tinTucRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        tinTucRepository.deleteById(id);
    }
    public TinTuc addTinTuc(TinTuc tinTuc) {
        return tinTucRepository.save(tinTuc);
    }
    public void updateImg1(TinTuc tinTuc, MultipartFile img1)
    {
        if (!img1.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/tintuc");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + img1.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(img1.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                tinTuc.setImage1(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void updateImg2(TinTuc tinTuc, MultipartFile img2)
    {
        if (!img2.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/tintuc");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + img2.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(img2.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                tinTuc.setImage2(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void updateImg3(TinTuc tinTuc, MultipartFile img3)
    {
        if (!img3.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/tintuc");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + img3.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(img3.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                tinTuc.setImage3(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }

    public List<TinTuc> getPostsByCategory(Integer categoryId) {
        return tinTucRepository.findByCategoryIdAndHideFalseOrderByTimeActive(categoryId);
    }

    public Optional<TinTuc> getPostById(Integer id) {
        return tinTucRepository.findById(id);
    }
}
