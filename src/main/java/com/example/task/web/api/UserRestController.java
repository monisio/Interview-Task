package com.example.task.web.api;


import com.example.task.model.binding.AppUserBindingModel;
import com.example.task.model.entity.AppUserEntity;
import com.example.task.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final AppUserService appUserService;

    public UserRestController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody @Valid AppUserBindingModel user){

        AppUserEntity newUser = this.appUserService.createUser(user);

        //todo: recheck the return response
       return ResponseEntity.status(201).build();

    }

    // refresh token endpoint
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){

    }

}
