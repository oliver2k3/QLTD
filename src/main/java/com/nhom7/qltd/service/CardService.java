package com.nhom7.qltd.service;

import com.nhom7.qltd.model.GoiVay;
import com.nhom7.qltd.model.TheTinDung;
import com.nhom7.qltd.repository.TheTinDungRepository;
import lombok.RequiredArgsConstructor;
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
public class CardService {
    private final TheTinDungRepository cardRepository;
    public List<TheTinDung> getAllCard(){
        return cardRepository.findAll();
    }
    public TheTinDung addCard(TheTinDung card) {
        return cardRepository.save(card);
    }
    public void deleteCard(int id) {
        cardRepository.deleteById(id);
    }
    public Optional <TheTinDung> getCardById(int id) {
        return cardRepository.findById(id);
    }
    public TheTinDung updateCard(TheTinDung card) {
        return cardRepository.save(card);
    }
    public void updateImage(TheTinDung card, MultipartFile imageProduct)
    {
        if (!imageProduct.isEmpty()) {
            try
            {
                Path dirImages = Paths.get("static/images/Thetindung");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
                card.setImage(newFileName);
            }
            catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
}
