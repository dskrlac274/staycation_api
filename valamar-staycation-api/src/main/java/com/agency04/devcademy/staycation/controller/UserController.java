package com.agency04.devcademy.staycation.controller;

import com.agency04.devcademy.staycation.converter.AccommodationConverter;
import com.agency04.devcademy.staycation.dto.*;
import com.agency04.devcademy.staycation.exception.BadRequestException;
import com.agency04.devcademy.staycation.exception.NotFoundException;
import com.agency04.devcademy.staycation.form.AccommodationForm;
import com.agency04.devcademy.staycation.model.*;
import com.agency04.devcademy.staycation.service.AccommodationService;
import com.agency04.devcademy.staycation.service.BookingService;
import com.agency04.devcademy.staycation.service.LocationService;
import com.agency04.devcademy.staycation.service.UserService;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;
    private final LocationService locationService;
    private final AccommodationService accommodationService;
    private final AccommodationConverter converter;

    private final BookingService bookingService;

    public UserController(UserService userService, LocationService locationService, AccommodationService accommodationService, AccommodationConverter converter, BookingService bookingService) {
        this.userService = userService;
        this.locationService = locationService;
        this.accommodationService = accommodationService;
        this.converter = converter;
        this.bookingService = bookingService;
    }

    @GetMapping("/user/data")
    public ResponseEntity<UserDto> fetchUser() {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
        System.out.println(userDetails.getUsername());
        User user = userService.findByUsername(userDetails.getUsername()).get();
        Location location = user.getLocation();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        UserDto dto = new UserDto(user.getFirstName(), user.getLastName(), user.getEmail(),
                formatter.format(user.getDob()), new UserLocationDataDto(location.getTitle(), location.getCountry()));

        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<UserProfileDto> getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = userService.findByUsername(userDetails.getUsername()).get();
        Location location = user.getLocation();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        UserProfileDto dto = new UserProfileDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                formatter.format(user.getDob()), new UserLocationDto(location.getTitle(), location.getPostalCode(), location.getCountry()),
                user.getTermsConditions());

        return new ResponseEntity(dto, HttpStatus.OK);
    }


    @PutMapping("/user/profile")
    public ResponseEntity<UserProfileDto> updateUser(@RequestBody UserProfileDto request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername()).get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setDob(formatter.parse(request.getDob()));
        } catch (ParseException e) {
            throw new BadRequestException(e);
        }
        UserLocationDto locationDto = request.getLocation();
        Location location = locationService.addLocation(new Location(locationDto.getTitle(), locationDto.getPostalCode(), locationDto.getCountry()));
        user.setLocation(location);

        if (user.getTermsConditions() != request.isTermsConditions())
            throw new BadRequestException();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("HEADER - X-User-Updated", user.getId().toString());

        userService.updateUser(user.getId(), user);
        return ResponseEntity.accepted().headers(responseHeaders).body(request);
    }

    @GetMapping("/user/accommodation")
    public ResponseEntity<List<UserAccommodationDto>>  getUserAccommodations(){
       List<Accommodation> accommodations=this.accommodationService.findByOwner(this.userService.getCurrentUser().get());
       if(accommodations.size()>0){
           return ResponseEntity.ok(accommodations.stream().map(converter::convertToUserAccommodationDto).collect(Collectors.toList()));
       }else{
           throw new NotFoundException("There is no accommodations for this user.");
       }
    }

    @GetMapping("/user/accommodation/{id}")
    public ResponseEntity<UserAccommodationDetailsDto> getUserAccommodationById(@PathVariable final long id){
        Accommodation accommodation=this.accommodationService.findById(id);
        if(!userService.getCurrentUser().isPresent() || !accommodation.getOwner().equals(userService.getCurrentUser().get())){
            throw new BadRequestException("Accommodation with desired id does not exist for current user.");
        }else{
            return ResponseEntity.ok(converter.convertToUserAccommodationDetailDto(accommodation));
        }
    }

    @DeleteMapping("/user/accommodation")
    public ResponseEntity delete(@RequestParam("id") final long id) {
        this.accommodationService.deleteAccommodationUnit(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/user/accommodation")
    public ResponseEntity<UserAccommodationDetailsDto> addNewUserAccommodation(@Valid @RequestBody AccommodationForm form){
       this.checkValidRequest(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convertToUserAccommodationDetailDto(accommodationService.addAccommodationUnit(converter.convertToAccommodation(form))));
    }

    @PutMapping("/user/accommodation")
    public ResponseEntity<UserAccommodationDetailsDto> updateUserAccommodation(@Valid @RequestBody AccommodationForm form, @RequestParam("id") long id){
        this.checkValidRequest(form);
        Accommodation accommodation= accommodationService.findById(id);
        accommodation.mapFrom(converter.convertToAccommodation(form));
        return ResponseEntity.status(HttpStatus.OK).body(converter.convertToUserAccommodationDetailDto(this.accommodationService.updateAccommodationUnit(id,accommodation)));
    }

    public void checkValidRequest(AccommodationForm form){
        try {
            form.getCategorisation();
        }
        catch(NullPointerException e) {
            throw new BadRequestException("You must add categorization!");
        }
        try{
            form.getSubtitle();
            form.getTitle();
        }catch(NullPointerException e){
            throw new BadRequestException("You must add title or subtitle!");
        }
        if(form.getImages().size()<1){
            throw new BadRequestException("You must add photos!");
        }
        Optional<Location> optionalLocation = locationService.findByPostalCode(form.getLocation().getPostalCode());
        if(optionalLocation.isEmpty()){
            throw new NotFoundException("There is no location with given postal code.");
        }
        if(!form.getLocation().getCity().equals(optionalLocation.get().getTitle())){
            throw new BadRequestException("You cannot add new city with the existing postal code!");
        }
    }

    @GetMapping("user/bookings")
    @SneakyThrows
    public ResponseEntity<UserBookingDto> reviewPastBookings(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername()).get();

        List<UserBookingDto> userBookingDtos = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

        for(Booking booking: user.getBookings())
        {
            List<Image> images = booking.getAccommodation().getImages();
            userBookingDtos.add(
                    new UserBookingDto(booking.getAccommodation().getTitle(),booking.getAccommodation().getLocation().getTitle(),
                            booking.getAccommodation().getCategorisation(), String.valueOf(booking.getCheckIn().format(formatter)),
                            String.valueOf(booking.getCheckOut().format(formatter)),booking.getStatus(),images.get(0).getContent(),booking.getCode()));
        }
        if(userBookingDtos.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity(userBookingDtos,HttpStatus.OK);
    }
    @GetMapping("user/bookings/{code}")
    public ResponseEntity<BookingCodeDto> getBookings(@PathVariable String code){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername()).get();

        Booking booking = bookingService.getBooking(code);
        List<Image> images = booking.getAccommodation().getImages();

        BookingCodeDto bookingCodeDto = new BookingCodeDto(booking.getAccommodation().getSubtitle(),booking.getAccommodation().getLocation().getTitle(),booking.getAccommodation().getLocation().getTitle(),booking.getAccommodation().getCategorisation(),
                booking.getCheckIn(),booking.getCheckOut(),booking.getStatus(),images.get(0).getContent(),booking.getCode(),booking.getUser(),booking.getAccommodation().getPersonCount()
                ,BigDecimal.valueOf(bookingService.totalDays(booking.getCheckIn(),booking.getCheckOut())).multiply(booking.getAccommodation().getPrice()));

        return new ResponseEntity(bookingCodeDto,HttpStatus.OK);
    }
    @SneakyThrows
    @DeleteMapping("user/bookings/{code}")
    public ResponseEntity reviewPastBookings(@PathVariable("code") String code){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(String.valueOf(bookingService.getBooking(code).getCheckIn()));
        Date date2 = sdf.parse(String.valueOf(new Date()));
        int result = date1.compareTo(date2);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (result > 0)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        responseHeaders.set("X-Canceled-Booking", code);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(bookingService.deleteBooking(code));
    }

}
