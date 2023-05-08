package com.agency04.devcademy.staycation.repository;

import com.agency04.devcademy.staycation.model.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
}
