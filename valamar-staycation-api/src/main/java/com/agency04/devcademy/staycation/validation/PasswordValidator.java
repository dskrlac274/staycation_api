package com.agency04.devcademy.staycation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : s.toCharArray()) {
            if(Character.isDigit(c))
                hasNumber = true;
            if("!?#$%^&*".contains("" + c))
                hasSpecial=true;
        }

        if (s.length() >= 8 && hasNumber && hasSpecial)
            return true;
        else
            return false;
    }
}
