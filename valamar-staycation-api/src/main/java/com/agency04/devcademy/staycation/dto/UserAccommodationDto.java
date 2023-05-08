package com.agency04.devcademy.staycation.dto;

import com.agency04.devcademy.staycation.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserAccommodationDto {
    private final long id;
    private final String title;
    private final String city;
    private final List<Image> images;
    private final int categorization;
    private final String disclaimer;
}
