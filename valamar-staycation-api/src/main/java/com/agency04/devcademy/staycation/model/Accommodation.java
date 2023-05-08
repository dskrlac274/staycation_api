package com.agency04.devcademy.staycation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User owner;

    private String title;
    private String subtitle;
    private String description;

    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    @OneToMany
    @JoinColumn(name="accommodation_id")
    private List<Image> images;

    private Integer categorisation;
    private BigDecimal price;
    private Integer personCount;

    @OneToMany
    @JoinColumn(name="accommodation_id")
    private List<Tag> tags=new ArrayList<>();

    @ManyToOne
    private Location location;
    private Boolean freeCancelation = true;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<Booking> bookings;

    public Accommodation(User owner, String title, String subtitle, AccommodationType accommodationType, List<Image> images, Integer categorisation, BigDecimal price, Integer personCount, Location location) {
        this.owner = owner;
        this.title = title;
        this.subtitle = subtitle;
        this.accommodationType = accommodationType;
        this.images = images;
        this.categorisation = categorisation;
        this.price = price;
        this.personCount = personCount;
        this.location = location;
    }

    public void mapFrom(Accommodation source) {
        this.setAccommodationType(source.getAccommodationType());
        this.setBookings(source.getBookings());
        this.setCategorisation(source.getCategorisation());
        this.setDescription(source.getDescription());
        this.setFreeCancelation(source.getFreeCancelation());
        this.setImages(source.getImages());
        this.setLocation(source.getLocation());
        this.setOwner(source.getOwner());
        this.setPersonCount(source.getPersonCount());
        this.setPrice(source.getPrice());
        this.setTitle(source.getTitle());
        this.setSubtitle(source.getSubtitle());
    }
}
