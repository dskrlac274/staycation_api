package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.BookingHistory;
import com.agency04.devcademy.staycation.repository.BookingHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingHistoryServiceImpl implements BookingHistoryService{
    private final BookingHistoryRepository repository;

    public BookingHistoryServiceImpl(BookingHistoryRepository repository) {
        this.repository = repository;
    }

    public Optional<BookingHistory> saveBookingHistory(BookingHistory log){
        return Optional.of(this.repository.save(log));
    }
}
