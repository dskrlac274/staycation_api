package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPaymentDto {
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
}
