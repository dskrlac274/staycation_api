package com.agency04.devcademy.staycation.form;

import com.agency04.devcademy.staycation.dto.UserLocationDto;
import com.agency04.devcademy.staycation.validation.EmailConstraint;
import com.agency04.devcademy.staycation.validation.PasswordConstraint;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class RegistrationForm {
    private String firstName;
    private String lastName;

    @EmailConstraint
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private UserLocationDto location;
    private Boolean termsConditions;

    @PasswordConstraint
    private String password;
}
