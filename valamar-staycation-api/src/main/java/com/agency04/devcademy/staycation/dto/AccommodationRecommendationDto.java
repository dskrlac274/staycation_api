package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AccommodationRecommendationDto {
    private final String title;
    private final String city;
    private final BigDecimal minPricePerNight;
    private final int categorization;
    private final String image;
}
