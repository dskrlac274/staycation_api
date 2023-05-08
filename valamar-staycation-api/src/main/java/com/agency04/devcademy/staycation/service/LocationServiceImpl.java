package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.exception.IdDoesNotExist;
import com.agency04.devcademy.staycation.model.Location;
import com.agency04.devcademy.staycation.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class LocationServiceImpl implements LocationService{
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getLocation(long id) {
        Location location = new Location();

        location = locationRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        return location;
    }

    @Override
    public Location addLocation(Location location) {
        for (Location other : locationRepository.findAll()) {
            if (location.equals(other))
                return other;
        }
        locationRepository.save(location);
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
        return (List<Location>) locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow(()-> new IdDoesNotExist("Id does not exist"));
    }

    @Override
    public Optional<Location> findByPostalCode(String postalCode) {
        return this.locationRepository.findByPostalCode(postalCode);
    }

    @Override
    public List<String> getAllCountries() {
        Set<String> set = new HashSet<>();
        locationRepository.findAll().forEach(location -> set.add(location.getCountry()));
        return List.copyOf(set);
    }

}
