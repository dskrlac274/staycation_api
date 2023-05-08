package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccommodationPropertiesDto {
    private final String type;
    private final int propertiesCount;
    private final String imageUrl;
}
