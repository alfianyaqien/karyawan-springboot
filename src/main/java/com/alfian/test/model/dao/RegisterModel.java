package com.alfian.test.model.dao;


import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class RegisterModel {
    public Long id;

    private String email;

    //    @ValidPassword
    @Size(min = 6, message = "Minimal 6 karakter")
    @NotNull(message =  "Password is mandatory")
    public String password;

//    @ValidPassword
    @Size(min = 6, message = "Minimal 6 karakter")
    @NotNull(message =  "Password is mandatory")
    private String confirmPassword;

    private String fullname;

}
