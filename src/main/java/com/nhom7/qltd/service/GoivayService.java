package com.nhom7.qltd.service;

import com.nhom7.qltd.model.GoiVay;
import com.nhom7.qltd.repository.GoivayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class GoivayService {

    private final GoivayRepository goivayRepository;
    public List<GoiVay> getAllGoivay() {
        return goivayRepository.findAll();
    }
    public Optional<GoiVay> getCourseById(int id) {
        return goivayRepository.findById(id);
    }
    public GoiVay addGoivay(GoiVay goiVay) {
        return goivayRepository.save(goiVay);
    }
    public void deleteGoivayById(int id) {
        if (!goivayRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        goivayRepository.deleteById(id);
    }
    public void updateImage(GoiVay newGoivay, MultipartFile imageProduct)
    {
        if (!imageProduct.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/goivay");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                newGoivay.setImage(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }

    public Optional<GoiVay> getGoivayById(int id) {
        return goivayRepository.findById(id);
    }
}
