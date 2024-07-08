package com.nhom7.qltd.service;

import com.nhom7.qltd.model.ChiTietHDV;
import com.nhom7.qltd.model.GoiVay;
import com.nhom7.qltd.repository.CTHDVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ChiTietHDVService {
    @Autowired
    private final CTHDVRepository chiTietHDVRepository;
    public Optional<ChiTietHDV> getCTHDVByHdvId(int hdvId) {
        return chiTietHDVRepository.findByHopDongVayId(hdvId);
    }
    public ChiTietHDV updateChiTietHDV(ChiTietHDV chiTietHDV) {
        return chiTietHDVRepository.save(chiTietHDV);
    }
}
