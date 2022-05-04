package com.example.task.service.impl;

import com.example.task.model.entity.RoleEntity;
import com.example.task.model.enums.RoleEnum;
import com.example.task.repository.RoleRepository;
import com.example.task.service.RoleService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initializeRoles() {
        if(roleRepository.count()!=RoleEnum.values().length){
            Arrays.stream(RoleEnum.values())
                    .map(role-> new RoleEntity().setRole(role))
                    .forEach(role->{
                        if(!roleRepository.exists(Example.of(role))){
                            roleRepository.save(role);
                        }
                    } );


        }

    }
}
