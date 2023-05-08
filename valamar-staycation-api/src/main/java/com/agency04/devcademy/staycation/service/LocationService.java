package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService{
    Location getLocation(long id);
    Location addLocation(Location location);
    List<Location> getAllLocations();

    Location findById(Long id);

    Optional<Location> findByPostalCode(String postalCode);

    List<String> getAllCountries();

}
