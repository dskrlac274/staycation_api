package com.agency04.devcademy.staycation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class BookingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Booking booking;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryTimestamp;

    private String statusFrom;

    private String statusTo;

    public BookingHistory(Booking booking, Date entryTimestamp, String statusFrom, String statusTo) {
        this.booking = booking;
        this.entryTimestamp = entryTimestamp;
        this.statusFrom = statusFrom;
        this.statusTo = statusTo;
    }
}
