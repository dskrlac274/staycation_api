package com.agency04.devcademy.staycation.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @ManyToOne
    private Location location;
    private Boolean termsConditions = false;

    @OneToMany(mappedBy = "owner")
    private List<Accommodation> accommodations;

    @OneToMany(mappedBy = "user")
    List<Booking> bookings = new ArrayList<>();


    public User(String firstName, String lastName, String email, String password, Date dob, Location location/*, List<Accommodation> accommodations*/) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.location = location;
        //this.accommodations = accommodations;
    }

    public void mapFrom(User source) {
        this.setFirstName(source.getFirstName());
        this.setLastName(source.getLastName());
        this.setEmail(source.getEmail());
        this.setDob(source.getDob());
        this.setLocation(source.getLocation());
        this.setTermsConditions(source.getTermsConditions());
        this.setAccommodations(source.getAccommodations());
    }
}
