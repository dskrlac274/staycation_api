package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking deleteBooking(String id);
    Booking getBooking(String id);

    Booking addBooking(Booking booking);
    Booking updateBooking(String id,Booking booking);

    List<Booking> getAllBookings();
    List<Booking> deleteAllBookings();
    Booking findById(String id);

    List<Booking> findBookingsByAvailabilityTime(String checkIn, String checkOut);

    int totalDays(LocalDateTime checkIn, LocalDateTime checkOut);


}
