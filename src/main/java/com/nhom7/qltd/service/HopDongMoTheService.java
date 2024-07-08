package com.nhom7.qltd.service;

import com.nhom7.qltd.model.ChiTietHDV;
import com.nhom7.qltd.model.ChiTietMoThe;
import com.nhom7.qltd.model.HopDongMoThe;
import com.nhom7.qltd.model.HopDongVay;
import com.nhom7.qltd.repository.ChiTietMoTheRepository;
import com.nhom7.qltd.repository.HopDongMoTheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HopDongMoTheService {
    @Autowired
    private final HopDongMoTheRepository hopDongMoTheRepository;
    @Autowired
    private final ChiTietMoTheRepository chiTietMoTheRepository;
    public HopDongMoThe addHDMT(HopDongMoThe hopDongMoThe) {
        return hopDongMoTheRepository.save(hopDongMoThe);
    }
    public void updateCCCD1(HopDongMoThe hopDongMoThe, MultipartFile CCCD1)
    {
        if (!CCCD1.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/hopdongmothe");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + CCCD1.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(CCCD1.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                hopDongMoThe.setCccdImage1(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void updateCCCD2(HopDongMoThe hopDongMoThe, MultipartFile CCCD2)
    {
        if (!CCCD2.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/Images/hopdongmothe");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + CCCD2.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(CCCD2.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                hopDongMoThe.setCccdImage2(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void updatedocfile(HopDongMoThe hopDongMoThe, MultipartFile docfile)
    {
        if (!docfile.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/files/hopdongmothe");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + docfile.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(docfile.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                hopDongMoThe.setDocumentFile(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void addCTMT(ChiTietMoThe chiTietMoThe) {
        chiTietMoTheRepository.save(chiTietMoThe);
    }

    public List<HopDongMoThe> getAllHDMTByUser(String username) {
        return hopDongMoTheRepository.findAllByUser_Username(username);
    }

    public Optional<HopDongMoThe> getHopDongMoTheById(Integer hdmtId) {
        return hopDongMoTheRepository.findById(hdmtId);
    }
}
