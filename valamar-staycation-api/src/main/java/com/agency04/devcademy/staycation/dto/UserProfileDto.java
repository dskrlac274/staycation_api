package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileDto {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String dob;
    private final UserLocationDto location;
    private final boolean termsConditions;
}
