package com.agency04.devcademy.staycation;

import com.agency04.devcademy.staycation.service.DataInitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class StaycationApplication {

    private final ApplicationContext context;

    public StaycationApplication(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(StaycationApplication.class, args);
    }

    @PostConstruct
    public void init() {
        DataInitService dataInitService = context.getBean(DataInitService.class);
        dataInitService.init();
    }

}
