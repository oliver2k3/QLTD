package com.nhom7.qltd.service;

import com.nhom7.qltd.model.ChiTietHDV;
import com.nhom7.qltd.model.GoiVay;
import com.nhom7.qltd.model.HopDongVay;
import com.nhom7.qltd.repository.CTHDVRepository;
import com.nhom7.qltd.repository.HopdongvayRepository;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HopdongvayService {
    @Autowired
    private  final HopdongvayRepository hopdongvayRepository;
   @Autowired
   private CTHDVRepository chiTietHDVRepository;
    public HopDongVay addHopdongvay(HopDongVay hopDongVay) {
        return hopdongvayRepository.save(hopDongVay);
    }
    public void updateCCCD1(HopDongVay hopDongVay, MultipartFile CCCD1)
    {
        if (!CCCD1.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/hopdongvay");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + CCCD1.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(CCCD1.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                hopDongVay.setCccdImage1(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void updateCCCD2(HopDongVay hopDongVay, MultipartFile CCCD2)
    {
        if (!CCCD2.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/hopdongvay");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + CCCD2.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(CCCD2.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                hopDongVay.setCccdImage2(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void updatedocfile(HopDongVay hopDongVay, MultipartFile docfile)
    {
        if (!docfile.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/files/hopdongvay");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + docfile.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(docfile.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                hopDongVay.setDocumentFile(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void addChiTietHDV(ChiTietHDV chiTietHDV) {
        chiTietHDVRepository.save(chiTietHDV);
    }

    public List<HopDongVay> getAllHopdongvayByUser(String username) {
        return hopdongvayRepository.findAllByUser_Username(username);
    }
}
