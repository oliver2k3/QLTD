package com.nhom7.qltd.dao;

import com.nhom7.qltd.entity.TransitionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransitionDao extends CrudRepository<TransitionEntity, Integer> {
    List<TransitionEntity> findByFromUser(String fromUser);
}
