package com.example.task.service.impl;

import com.example.task.exception.EntityNotFoundException;
import com.example.task.model.binding.AppUserBindingModel;
import com.example.task.model.entity.AppUserEntity;
import com.example.task.model.entity.RoleEntity;
import com.example.task.model.enums.RoleEnum;
import com.example.task.repository.AppUserRepository;
import com.example.task.repository.RoleRepository;
import com.example.task.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService , UserDetailsService {

    private static final String ROLE_NOT_EXISTS= "Invalid role";
    private static final String USER_NOT_FOUND = "User not found";

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;



    public AppUserServiceImpl(AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }


    @Override
    public AppUserEntity createUser(AppUserBindingModel user) {
        AppUserEntity newUser = this.modelMapper.map(user, AppUserEntity.class);

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoles(new ArrayList<>());
        List<RoleEntity> roles = newUser.getRoles();
        roles.add(this.getRole("USER"));
        return this.appUserRepository.save(newUser);
    }

    @Override
    public AppUserEntity getUser(String email) {
        return this.appUserRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public void addRoleToAppUser(String email, String roleName) {
        AppUserEntity user = this.getUser(email);
        RoleEntity role = this.getRole(roleName);
        user.getRoles().add(role);
        this.appUserRepository.save(user);
    }

    protected RoleEntity getRole(String roleName){

       try{
           return this.roleRepository.findByRole(RoleEnum.valueOf(roleName.toUpperCase())).orElseThrow(() -> new EntityNotFoundException(ROLE_NOT_EXISTS));
       }catch (Exception e){
           throw new EntityNotFoundException(ROLE_NOT_EXISTS);
       }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUserEntity appUserEntity = this.appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        List<GrantedAuthority> authorities = appUserEntity.getRoles().stream()
                .map(role-> new SimpleGrantedAuthority("ROLE_"+ role.getRole().name())).collect(Collectors.toList());

       return new User(appUserEntity.getEmail(),appUserEntity.getPassword(),authorities);
    }
}
