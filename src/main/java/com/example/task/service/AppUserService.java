package com.example.task.service;

import com.example.task.model.binding.AppUserBindingModel;
import com.example.task.model.entity.AppUserEntity;
import org.springframework.stereotype.Service;

@Service
public interface AppUserService {
    // todo: add view model to return after successful save

    AppUserEntity createUser(AppUserBindingModel user);
    AppUserEntity getUser(String email);
    void addRoleToAppUser(String email , String roleName);
}
