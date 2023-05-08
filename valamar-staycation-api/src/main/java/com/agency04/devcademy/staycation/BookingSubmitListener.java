package com.agency04.devcademy.staycation;

import com.agency04.devcademy.staycation.model.User;
import com.agency04.devcademy.staycation.service.UserService;
import lombok.SneakyThrows;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

@Component("test")
public class BookingSubmitListener implements ApplicationListener<OnBookingSubmit> {
  /*  @Autowired
    private IUserService service;*/

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

/*    @Autowired
    private JavaMailSender mailSender ;*/
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return mailSender;
    }
    @Override
    public void onApplicationEvent(OnBookingSubmit event) {
        this.confirmBooking(event);
    }

    //@SneakyThrows
    @SneakyThrows
    public void confirmBooking(OnBookingSubmit event) {


        MimeMessagePreparator preparator = mimeMessage -> {


            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setTo("");
            message.setFrom(new InternetAddress("admin@gmail.com"));
            message.setSubject("subject");
            message.setText("body");

        };

        try {
            getJavaMailSender().send(preparator);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
