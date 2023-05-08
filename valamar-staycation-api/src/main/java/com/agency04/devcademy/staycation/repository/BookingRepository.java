package com.agency04.devcademy.staycation.repository;

import com.agency04.devcademy.staycation.model.Booking;
import com.agency04.devcademy.staycation.model.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String>{

    @Query("select f FROM BookingHistory f " +
            "where f.entryTimestamp = " +
            "     (select max(ff.entryTimestamp) from BookingHistory ff where ff.id = f.id)" +
            "AND f.booking.id = :id")
    Optional<BookingHistory> findNewestBookingHistoryByDate(@Param("id") String id);

    @Query(" FROM Booking " +
            "WHERE checkIn >= :checkIn " +
            "AND person_count <= :checkOut")
    List<Booking> findBookingsByAvailabilityTime(@Param("checkIn") String checkIn, @Param("checkOut") String checkOut);

    @Query("select b from Booking b where b.accommodation = :id")
    List<Booking> findByAccommodation(@Param("id") Long id);


}
