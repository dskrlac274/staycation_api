package com.agency04.devcademy.staycation.dto;

import com.agency04.devcademy.staycation.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookingCodeDto {
    private final String title;
    private final String parentTitle;
    private final String city;
    private final int categorization;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final String status;
    private final String image;
    private final String code;
    private final User user;
    private final int personCount;
    private final BigDecimal totalCost;
}
