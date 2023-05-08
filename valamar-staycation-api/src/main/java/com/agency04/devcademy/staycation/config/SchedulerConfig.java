package com.agency04.devcademy.staycation.config;

import com.agency04.devcademy.staycation.scheduler.BookingSchedulerJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
    @Bean
    public JobDetail BookingScheduleJobDetail() {
        return JobBuilder.newJob(BookingSchedulerJob.class).storeDurably().build();
    }

    @Bean
    public SimpleTrigger BookingScheduleTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(15).repeatForever();

        return TriggerBuilder.newTrigger().forJob(BookingScheduleJobDetail())
                .withSchedule(scheduleBuilder).build();
    }
}
