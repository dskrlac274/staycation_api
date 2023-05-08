package com.agency04.devcademy.staycation.dto;

import com.agency04.devcademy.staycation.model.AccommodationType;
import com.agency04.devcademy.staycation.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccommodationDetailsDto {
    private final String title;
    private final String subtitle;
    private final String description;
    private final AccommodationType accommodationType;
    private final List<Image> Images;
    private final int categorization;
    private final BigDecimal price;
    private final int personCount;
    private final String city;
    private final Boolean freeCancelation;
}
