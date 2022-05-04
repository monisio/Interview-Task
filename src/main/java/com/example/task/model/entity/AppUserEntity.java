package com.example.task.model.entity;


import com.example.task.model.enums.RoleEnum;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class AppUserEntity extends BaseEntity {


    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true ,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles;

    public AppUserEntity() {
    }


    public String getFirstName() {
        return firstName;
    }

    public AppUserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public AppUserEntity setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

}
