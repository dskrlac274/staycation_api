package com.agency04.devcademy.staycation.dto;

import com.agency04.devcademy.staycation.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccommodationFilterDto {
    private final long id;
    private final String title;
    private final String locationMark;
    private final BigDecimal price;
    private final int categorization;
    private final String image;
}
