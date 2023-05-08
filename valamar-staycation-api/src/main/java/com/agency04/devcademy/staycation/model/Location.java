package com.agency04.devcademy.staycation.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String postalCode;
    private String country;

    public Location(String title, String postalCode, String country) {
        this.title = title;
        this.postalCode = postalCode;
        this.country = country;
    }

    public void mapFrom(Location source) {
        this.setTitle(source.getTitle());
        this.setPostalCode(source.getPostalCode());
        this.setCountry(source.getCountry());
    }
}
