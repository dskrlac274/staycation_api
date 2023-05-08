package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.exception.IdDoesNotExist;
import com.agency04.devcademy.staycation.model.Accommodation;
import com.agency04.devcademy.staycation.model.User;
import com.agency04.devcademy.staycation.repository.AccommodationRepository;
import com.agency04.devcademy.staycation.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService{
    private AccommodationRepository accommodationRepository;
    private LocationRepository locationRepository;

    public AccommodationServiceImpl(AccommodationRepository accommodationRepository,LocationRepository locationRepository){
        this.accommodationRepository = accommodationRepository;
        this.locationRepository = locationRepository;
    }
    @Override
    public Accommodation deleteAccommodationUnit(long id){
        Accommodation accommodation = new Accommodation();

        accommodation = accommodationRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        accommodationRepository.deleteById(id);
        return accommodation;
    }

    @Override
    public Accommodation getAccommodationUnit(long id) {
        Accommodation accommodation = new Accommodation();

        accommodation = accommodationRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        return accommodation;
    }

    @Override
    public Accommodation addAccommodationUnit(Accommodation unit){
        return accommodationRepository.save(unit);
    }

    @Override
    public Accommodation updateAccommodationUnit(long id, Accommodation accommodationUpdate) {
        Accommodation accommodation = accommodationRepository.findById(id).map(
                acc->{
                    acc.mapFrom(accommodationUpdate);
                    return accommodationRepository.save(acc);
                }).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));
        return accommodation;
    }

    @Override
    public List<Accommodation> getAllAccommodationUnits() {
        return (List<Accommodation>) accommodationRepository.findAll();
    }

    @Override
    public List<Accommodation> deleteAllAccommodationUnits() {
        List<Accommodation> accommodations = (List<Accommodation>) accommodationRepository.findAll();
        accommodationRepository.deleteAll();
        return accommodations;
    }

    @Override
    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElseThrow(()-> new IdDoesNotExist("Id does not exist"));
    }

    @Override
    public List<Accommodation> findAccommodationsByLocationCountry(String country) {
        return accommodationRepository.findByLocation_CountryIgnoreCase(country);
    }

    @Override
    public List<Accommodation> findAccommodationsByLocationTitle(String title) {
        return accommodationRepository.findByLocation_TitleIgnoreCase(title);
    }

    @Override
    public List<Accommodation> findAccommodationsByType(String type) {
        return accommodationRepository.findByTypeIgnoreCase(type);
    }

    @Override
    public Accommodation findByLocation(long id) {
        return accommodationRepository.findByLocation(id);
    }

    @Override
    public Accommodation findById(long id) {
        return accommodationRepository.findById(id).orElseThrow(
                ()->new IdDoesNotExist("Id does not exist")
        );

    }

    @Override
    public List<Accommodation> findByOwner(User owner){
        return this.accommodationRepository.findByOwner(owner);
    }

    @Override
    public List<Accommodation> findByTitle(String title) {
        return accommodationRepository.findByTitle(title);
    }

}

