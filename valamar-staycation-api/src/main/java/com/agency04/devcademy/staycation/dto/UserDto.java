package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String dob;
    private UserLocationDataDto location;
}
