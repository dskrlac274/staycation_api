package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.exception.IdDoesNotExist;
import com.agency04.devcademy.staycation.model.Booking;
import com.agency04.devcademy.staycation.repository.AccommodationRepository;
import com.agency04.devcademy.staycation.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
    private AccommodationRepository accommodationRepository;
    private BookingRepository bookingRepository;


    private String reservationAlterType;
    //to-do
    //distinct update and add

    @Autowired
    public BookingServiceImpl(AccommodationRepository accommodationRepository,
                                  BookingRepository bookingRepository) {
        this.accommodationRepository = accommodationRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking deleteBooking(String id) {
        Booking booking = new Booking();

        booking = bookingRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        bookingRepository.deleteById(booking.getCode());
        return booking;
    }

    @Override
    public Booking getBooking(String id) {
        Booking booking = new Booking();

        booking = bookingRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        return booking;
    }


    @Override
    public Booking addBooking(Booking booking) {
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Booking updateBooking(String id, Booking bookingUpdate) {
        Booking booking = bookingRepository.findById(id).map(
                acc->{
                    acc.mapFrom(bookingUpdate);
                    return bookingRepository.save(bookingUpdate);
                }).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));
        return booking;
    }
    @Override
    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    @Override
    public List<Booking> deleteAllBookings() {
        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
        //bookingRepository.deleteAll();
        return bookings;
    }

    @Override
    public Booking findById(String id) {
        return bookingRepository.findById(id).orElseThrow(()-> new IdDoesNotExist("Id does not exist"));
    }

    @Override
    public List<Booking> findBookingsByAvailabilityTime(String checkIn, String checkOut) {
        return bookingRepository.findBookingsByAvailabilityTime(checkIn, checkOut);
    }

    @Override
    public int totalDays(LocalDateTime checkIn, LocalDateTime checkOut) {
        long getCheckIn = checkIn.toEpochSecond(ZoneOffset.UTC);
        long getCheckOut = checkOut.toEpochSecond(ZoneOffset.UTC);
        long secPerDay = 1*60*60*24;

        int numOfDays = (int) ((getCheckOut - getCheckIn) / secPerDay);
        return ( numOfDays + 1);

    }


}
