package com.nhom7.qltd.repository;

import com.nhom7.qltd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserById(UUID id);
    boolean existsByUsername(String email);
    void deleteUserByUsername(String username);
}