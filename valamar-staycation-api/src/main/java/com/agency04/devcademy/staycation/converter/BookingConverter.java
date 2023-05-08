package com.agency04.devcademy.staycation.converter;

import com.agency04.devcademy.staycation.form.PaymentForm;
import com.agency04.devcademy.staycation.model.Accommodation;
import com.agency04.devcademy.staycation.model.Booking;
import com.agency04.devcademy.staycation.service.AccommodationService;
import com.agency04.devcademy.staycation.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    private final AccommodationService service;
    private final UserService userService;

    public BookingConverter(AccommodationService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public Booking convertPaymentToBooking(PaymentForm payment){
        Accommodation accommodation=this.service.findById(payment.getAccommodationId());
        return new Booking(accommodation, accommodation.getCategorisation(), payment.getCheckIn(), payment.getCheckOut(),
                userService.findByUsername(payment.getUser().getEmail()).orElse(null), "CREATED");
    }
}
