package com.example.task.model.binding;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AppUserBindingModel {
    @NotBlank
    @Size(min=2)
    private String firstName;

    @NotBlank
    @Size(min=2)
    private String lastName;


    @Email
    private String email;

    @NotBlank
    @Size(min=4)
    private String password;

    @NotBlank
    @Size(min = 4)
    //todo: add repeat password validation with annotation
    private String passwordRepeat;




    public AppUserBindingModel() {
    }


    public String getFirstName() {
        return firstName;
    }

    public AppUserBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppUserBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppUserBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppUserBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public AppUserBindingModel setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
        return this;
    }
}
