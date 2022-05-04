package com.example.task.repository;

import com.example.task.model.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity,Long> {

   Optional<AppUserEntity> findByEmail(String email);
}
