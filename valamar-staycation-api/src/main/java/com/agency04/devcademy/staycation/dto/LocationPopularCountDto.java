package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationPopularCountDto {
    private final String title;
    private final int propertiesCount;
    private final String imageUrl;
}
