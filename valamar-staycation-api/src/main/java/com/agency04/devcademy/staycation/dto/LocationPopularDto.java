package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationPopularDto {
    private final String type;
    private final String value;
    private final String imageUrl;
    private final int propertiesCount;
}
