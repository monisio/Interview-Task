package com.example.task.model.binding;

public class LoginBindingModel {

    private String email;
    private String password;


    public LoginBindingModel() {
    }


    public String getEmail() {
        return email;
    }

    public LoginBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
