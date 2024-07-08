package com.nhom7.qltd.service;

import com.nhom7.qltd.model.ChiTietMoThe;
import com.nhom7.qltd.repository.ChiTietMoTheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietMoTheService {
    private final ChiTietMoTheRepository chiTietMoTheRepository;
    public Optional<ChiTietMoThe> getChiTietMoTheByHDMTId(int id) {
        return chiTietMoTheRepository.findByHopDongMoTheId(id);
    }

    public void updateChiTietMoThe(ChiTietMoThe chiTietMoThe) {
        chiTietMoTheRepository.save(chiTietMoThe);
    }
}
