package com.example.task.model.entity;

import com.example.task.model.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class RoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public RoleEntity() {
    }

    public RoleEnum getRole() {

        return role;
    }

    public RoleEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
}
