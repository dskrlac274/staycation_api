package com.agency04.devcademy.staycation.form;


import com.agency04.devcademy.staycation.dto.UserPaymentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentForm {
   private LocalDateTime checkIn;

   private LocalDateTime checkOut;

   private Long accommodationId;

   private BillerForm biller;

   private CardForm card;

   private UserPaymentDto user;

   private String price;
}
