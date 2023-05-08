package com.agency04.devcademy.staycation.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CardForm {
    private String number;
    private LocalDate expDate;
    private String cvv;
}
