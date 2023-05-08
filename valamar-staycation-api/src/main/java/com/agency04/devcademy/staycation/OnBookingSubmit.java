package com.agency04.devcademy.staycation;

import com.agency04.devcademy.staycation.model.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Data
public class OnBookingSubmit extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;
    public OnBookingSubmit(
            Object principal, Locale locale, String appUrl) {
        super(principal);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
