package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLocationDto {
    private String title;
    private String postalCode;
    private String country;
}
