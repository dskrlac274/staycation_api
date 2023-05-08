package com.agency04.devcademy.staycation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    @Override
    public void initialize(EmailConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!s.contains("@") || s.indexOf("@") != s.lastIndexOf("@")) return false;
        int at = s.indexOf("@");
        s = s.substring(at+1);
        if (!s.contains(".") || s.indexOf(".") != s.lastIndexOf(".") || s.indexOf(".") == 0 || s.indexOf(".") == s.length()-1)
            return false;
        return true;
    }
}
