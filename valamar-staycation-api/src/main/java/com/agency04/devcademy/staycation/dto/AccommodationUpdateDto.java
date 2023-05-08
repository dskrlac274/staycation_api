package com.agency04.devcademy.staycation.dto;

import com.agency04.devcademy.staycation.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccommodationUpdateDto {
    private final long id;
    private final String title;
    private final String subtitle;
    private final String description;
    private final int categorization;
    private final String typeId;
    private final Location location;
    private final List<String> images;
    private final int capacity;
    private final boolean freeCancel;
    private final BigDecimal price;
}
