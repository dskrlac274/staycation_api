package com.agency04.devcademy.staycation.dto;

import com.agency04.devcademy.staycation.model.AccommodationType;
import com.agency04.devcademy.staycation.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserAccommodationDetailsDto {
    private final long id;
    private final String title;
    private final String subtitle;
    private final String description;
    private final int categorization;
    private final AccommodationType typeId;
    private final AccommodationLocationDto location;
    private final List<ImageDto> images;
    private final int capacity;
    private final boolean freeCancel;
    private final BigDecimal price;
}
