package com.agency04.devcademy.staycation.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBookingDto {
    private String title;
    private String city;
    private int categorisation;
    private String checkIn;
    private String checkOut;
    private String status;
    private String image;
    private String code;
}
