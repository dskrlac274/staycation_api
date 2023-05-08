package com.agency04.devcademy.staycation.scheduler;

import com.agency04.devcademy.staycation.model.Booking;
import com.agency04.devcademy.staycation.model.BookingHistory;
import com.agency04.devcademy.staycation.service.BookingHistoryService;
import com.agency04.devcademy.staycation.service.BookingService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

public class BookingSchedulerJob extends QuartzJobBean {
    private final BookingService service;
    private final BookingHistoryService historyService;

    public BookingSchedulerJob(BookingService service, BookingHistoryService historyService) {
        this.service = service;
        this.historyService = historyService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<Booking> bookings = service.getAllBookings();
        for (Booking booking : bookings) {
            if (booking.getStatus().equals("SUBMITTED")) {
                booking.setStatus("CONFIRMED");
                service.updateBooking(booking.getCode(), booking);
                this.historyService.saveBookingHistory(new BookingHistory(booking, new Date(), "SUBMITTED", "CONFIRMED"));
                System.out.println("SCHEDULER: Confirmed booking with code: " + booking.getCode());
            }
        }
    }
}
