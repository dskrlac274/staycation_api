package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccommodationLocationDto {
    private final String countryIso;
    private final String city;
    private final String postalCode;
}
