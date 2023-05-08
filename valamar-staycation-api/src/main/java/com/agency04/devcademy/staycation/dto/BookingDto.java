package com.agency04.devcademy.staycation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingDto {
    private final String title;
    private final String city;
    private final int categorization;
    private final String checkIn;
    private final String checkOut;
    private final String status;
    private final String image;
    private final String code;
}
