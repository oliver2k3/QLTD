package com.nhom7.qltd.mobile_service;

import com.nhom7.qltd.dao.SavingDAO;
import com.nhom7.qltd.dto.SavingDto;
import com.nhom7.qltd.entity.SavingEntity;
import com.nhom7.qltd.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingDAO savingDAO;
    private final UsersService userService;

//    public void createSaving(Integer userId, Double depositAmount, Integer depositDuration, Double interestRate) {
//        UserEntity user = userService.getUserById(userId);
//        if (user.getBalance() < depositAmount) {
//            throw new IllegalArgumentException("Insufficient balance");
//        }
//
//        user.setBalance(user.getBalance() - depositAmount);
//        userService.updateUser(user);
//
//        SavingEntity savingEntity = new SavingEntity();
//        savingEntity.setUserId(userId);
//        savingEntity.setDepositAmount(depositAmount);
//        savingEntity.setDepositDuration(depositDuration);
//        savingEntity.setInterestRate(interestRate);
//        savingEntity.setAmount(depositAmount);
//        savingEntity.setCreatedDate(LocalDateTime.now());
//        savingEntity.setMaturityDate(LocalDateTime.now().plusMonths(depositDuration));
//        savingEntity.setStatus("Active");
//
//        savingDAO.save(savingEntity);
//    }
    public void creatSaving(SavingDto savingDto) {
        UserEntity user = userService.getUserByEmail(savingDto.getEmail());
        if (user.getBalance() < savingDto.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - savingDto.getAmount());
        userService.updateUser(user);
        SavingEntity savingEntity = new SavingEntity();
        savingEntity.setEmail(savingDto.getEmail());
        savingEntity.setAmount(savingDto.getAmount());
        savingEntity.setDepositDuration(savingDto.getDepositDuration());
        savingEntity.setInterestRate(savingDto.getInterestRate());
        savingEntity.setDepositAmount(savingDto.getAmount()*savingDto.getInterestRate());
        savingEntity.setCreatedDate(LocalDateTime.now());
        savingEntity.setMaturityDate(LocalDateTime.now().plusMonths(savingDto.getDepositDuration()));
        savingEntity.setStatus("Active");
    }
}