package com.nhom7.qltd.service;

import com.nhom7.qltd.model.Status;
import com.nhom7.qltd.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusService {
    private final StatusRepository statusRepository;
    public Status getStatusById(int id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Status Id:" + id));
    }
}
