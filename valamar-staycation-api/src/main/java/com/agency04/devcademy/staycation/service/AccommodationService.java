package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.Accommodation;
import com.agency04.devcademy.staycation.model.User;

import java.util.List;

public interface AccommodationService {
    Accommodation deleteAccommodationUnit(long id);
    Accommodation getAccommodationUnit(long id);

    Accommodation addAccommodationUnit(Accommodation accommodation);
    Accommodation updateAccommodationUnit(long id,Accommodation accommodation);

    List<Accommodation> getAllAccommodationUnits();
    List<Accommodation> deleteAllAccommodationUnits();
    Accommodation findById(Long id);

    List<Accommodation> findAccommodationsByLocationCountry(String country);
    List<Accommodation> findAccommodationsByLocationTitle(String title);
    List<Accommodation> findAccommodationsByType(String type);

    Accommodation findByLocation(long id);

    Accommodation findById(long id);

    List<Accommodation> findByOwner(User owner);
    List<Accommodation> findByTitle(String title);
}
