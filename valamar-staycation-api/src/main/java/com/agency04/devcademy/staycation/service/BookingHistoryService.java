package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.BookingHistory;

import java.util.Optional;

public interface BookingHistoryService {
    Optional<BookingHistory> saveBookingHistory(BookingHistory log);
}
