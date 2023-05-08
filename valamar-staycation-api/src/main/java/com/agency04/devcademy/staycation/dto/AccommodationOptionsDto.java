package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccommodationOptionsDto {
    private final String title;
    private final String Description;
    private final List<TagsDto> tags;
    private final int personCount;
    private final BigDecimal price;
}
