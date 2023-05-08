package com.agency04.devcademy.staycation.form;

import com.agency04.devcademy.staycation.validation.EmailConstraint;
import com.agency04.devcademy.staycation.validation.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginForm {
    @EmailConstraint
    private String username;
    @PasswordConstraint
    private String password;
}
