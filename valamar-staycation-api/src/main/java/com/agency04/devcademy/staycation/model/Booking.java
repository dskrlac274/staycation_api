package com.agency04.devcademy.staycation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String code;


    @ManyToOne
    private Accommodation accommodation;

    private Integer categorisation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime checkIn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime checkOut;

    private String status;

    @ManyToOne
    private User user;

    public Booking(Accommodation accommodation, Integer categorisation, LocalDateTime checkIn, LocalDateTime checkOut, User user, String status) {
        this.accommodation = accommodation;
        this.categorisation = categorisation;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.user = user;
    }

    @OneToMany(mappedBy = "booking")
    List<BookingHistory> bookingHistories = new ArrayList<>();



    public void mapFrom(Booking source) {
        this.setAccommodation(source.getAccommodation());
        this.setCategorisation(source.getCategorisation());
        this.setCheckIn(source.getCheckIn());
        this.setCheckOut(source.getCheckOut());
        this.setStatus(source.getStatus());
    }
}
