package com.agency04.devcademy.staycation.controller;


import com.agency04.devcademy.staycation.OnBookingSubmit;
import com.agency04.devcademy.staycation.converter.AccommodationConverter;
import com.agency04.devcademy.staycation.converter.BookingConverter;
import com.agency04.devcademy.staycation.dto.*;
import com.agency04.devcademy.staycation.exception.NoAvailableOptionsException;
import com.agency04.devcademy.staycation.exception.NoSuchElement;
import com.agency04.devcademy.staycation.form.DateRangeForm;
import com.agency04.devcademy.staycation.form.PaymentForm;
import com.agency04.devcademy.staycation.model.Accommodation;
import com.agency04.devcademy.staycation.model.AccommodationType;
import com.agency04.devcademy.staycation.model.Booking;
import com.agency04.devcademy.staycation.model.Location;
import com.agency04.devcademy.staycation.service.AccommodationService;
import com.agency04.devcademy.staycation.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccommodationController {

    private AccommodationService accommodationService;

    private AccommodationConverter converter;

    private BookingConverter bookingConverter;

    private BookingService bookingService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    public AccommodationController(AccommodationService accommodationService, AccommodationConverter converter, BookingConverter bookingConverter, BookingService bookingService) {
        this.accommodationService = accommodationService;
        this.converter = converter;
        this.bookingService = bookingService;
        this.bookingConverter = bookingConverter;

    }

    @GetMapping("/accommodation/properties")
    public ResponseEntity<List<AccommodationPropertiesDto>> getPropertiesByType(@RequestHeader("X-Staycation-Location") String type) {

        List<AccommodationPropertiesDto> list = new ArrayList<>();
        for (AccommodationType current : AccommodationType.values()) {
            List<Accommodation> accommodations = accommodationService.findAccommodationsByType(current.toString());
            AccommodationPropertiesDto dto = new AccommodationPropertiesDto(current.getName(), accommodations.size(), "https://picsum.photos/200/300");
            list.add(dto);
        }

        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/accommodation/{id}")
    public ResponseEntity<AccommodationDetailsDto> popularLocationByCount(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.getAccommodationUnit(id);
        AccommodationDetailsDto accommodationDetailsDto = new AccommodationDetailsDto(accommodation.getTitle(),
                accommodation.getSubtitle(), accommodation.getDescription(), accommodation.getAccommodationType(),
                accommodation.getImages(), accommodation.getCategorisation(), accommodation.getPrice(),
                accommodation.getPersonCount(), accommodation.getLocation().getTitle(),
                accommodation.getFreeCancelation());

        return new ResponseEntity<AccommodationDetailsDto>(accommodationDetailsDto, HttpStatus.OK);

    }

    @GetMapping("/accommodation/recommendation/{id}")
    public ResponseEntity<List<AccommodationRecommendationDto>> reccommendSimilar(@PathVariable("id") Long id) {

        Accommodation myAccommodation = accommodationService.getAccommodationUnit(id);

        List<Accommodation> accommodations = accommodationService.getAllAccommodationUnits();
        accommodations = accommodations.stream().filter(accommodation -> {
            return accommodation.getAccommodationType().equals(myAccommodation.getAccommodationType()) &&
                    accommodation.getLocation().equals(myAccommodation.getLocation());
        }).collect(Collectors.toList());

        accommodations = accommodations.stream().filter(accommodation -> {
            return Math.abs(accommodation.getBookings().size() - myAccommodation.getBookings().size()) < 5;
        }).collect(Collectors.toList());

        List<AccommodationRecommendationDto> list = new ArrayList<>();
        accommodations.forEach(accommodation -> {
            list.add(new AccommodationRecommendationDto(accommodation.getTitle(), accommodation.getLocation().getTitle(),
                    accommodation.getPrice(), accommodation.getCategorisation(), accommodation.getImages().get(0).getContent()));
        });

        return new ResponseEntity(list, HttpStatus.OK);

    }

    @PostMapping("/accommodation/booking")
    public ResponseEntity<?> makeBooking(@Valid @RequestBody PaymentForm paymentForm) {

        for (Booking booking : bookingService.findBookingsByAvailabilityTime(String.valueOf(paymentForm.getCheckIn()), String.valueOf(paymentForm.getCheckIn()))) {
            if (booking.getAccommodation().getId().equals(paymentForm.getAccommodationId()))
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(bookingService.addBooking(bookingConverter.convertPaymentToBooking(paymentForm)), HttpStatus.CREATED);

    }

    @GetMapping("/accommodation/confirm")
    public ResponseEntity<?> submitBooking(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(principal);

        String url = request.getContextPath();

        applicationEventPublisher.publishEvent(new OnBookingSubmit(principal,
                request.getLocale(), url));


        return ResponseEntity.ok().body(applicationEventPublisher);
    }

    @GetMapping("/accommodation/filter")
    public ResponseEntity<List<AccommodationFilterDto>> filterAccommodations(
            @RequestParam(value = "location", required = false) Location location,
            @RequestParam(value = "checkIn", required = false) LocalDateTime checkIn,
            @RequestParam(value = "checkOut", required = false) LocalDateTime checkOut,
            @RequestParam(value = "people", required = false) Integer people,
            @RequestParam("type") AccommodationType type) {
        List<Accommodation> filtered = this.accommodationService.getAllAccommodationUnits().stream().filter(a -> a.getAccommodationType().equals(type)).toList();
        if (people != null) {
            filtered = filtered.stream().filter(a -> a.getPersonCount() > people).collect(Collectors.toList());
        }
        if (location != null) {
            filtered = filtered.stream().filter(a -> a.getLocation().equals(location)).collect(Collectors.toList());
        }
        if (checkIn != null && checkOut != null) {
            for (Accommodation accommodation : filtered) {
                for (Booking booking : accommodation.getBookings()) {
                    if (!((checkIn.isBefore(checkOut) && checkOut.isBefore(booking.getCheckIn())) || (checkIn.isAfter(booking.getCheckOut()) && checkOut.isAfter(checkIn)))) {
                        filtered.remove(accommodation);
                    }
                }
            }
        }
        if (filtered.size() <= 0)
            throw new NoSuchElement("There is no filter match.");

        return ResponseEntity.ok(filtered.stream().map(converter::convertToAccommodationFilterDto).collect(Collectors.toList()));
    }

    @PutMapping("/accommodation/option/{id}")
    public ResponseEntity<AccommodationOptionsDto> getAvailableOptions(@PathVariable("id") Long id, @RequestBody DateRangeForm form) {
        Accommodation accommodation = accommodationService.findById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        boolean flag = true;
        for (Booking booking : accommodation.getBookings()) {
            LocalDateTime checkIn = LocalDate.parse(form.getCheckIn(), formatter).atStartOfDay();
            LocalDateTime checkOut = LocalDate.parse(form.getCheckOut(), formatter).atStartOfDay();
            if (!booking.getCheckIn().isAfter(checkOut) && !booking.getCheckOut().isBefore(checkIn)) {
                flag = false;
            }
        }

        if (!flag)
            throw new NoAvailableOptionsException("No available option in defined time span");

        return ResponseEntity.status(HttpStatus.OK).body(converter.convertToAccommodationOptionsDto(accommodation));
    }
}
