package com.agency04.devcademy.staycation.controller;

import com.agency04.devcademy.staycation.converter.LocationConverter;
import com.agency04.devcademy.staycation.dto.LocationDto;
import com.agency04.devcademy.staycation.dto.LocationPopularCountDto;
import com.agency04.devcademy.staycation.dto.LocationPopularDto;
import com.agency04.devcademy.staycation.exception.BadRequestException;
import com.agency04.devcademy.staycation.exception.NoSuchElement;
import com.agency04.devcademy.staycation.model.Accommodation;
import com.agency04.devcademy.staycation.model.Location;
import com.agency04.devcademy.staycation.service.AccommodationService;
import com.agency04.devcademy.staycation.service.LocationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LocationController {

    private final LocationService locationService;
    private final AccommodationService accommodationService;
    private final LocationConverter converter;

    public LocationController(LocationService locationService, AccommodationService accommodationService, LocationConverter converter) {
        this.locationService = locationService;
        this.accommodationService = accommodationService;
        this.converter = converter;
    }

    @GetMapping("/accommodation/location")
    public ResponseEntity<List<LocationDto>> getLocations(){
        List<LocationDto> locations= this.locationService.getAllLocations().stream().map(converter::convertToLocationDto).toList();
        if(locations.size()>0){
            return ResponseEntity.ok(locations);
        }else{
            throw new NoSuchElement("There is no locations in DB.");
        }
    }

    @GetMapping("/locations/popular/{count}")
    public ResponseEntity<List<LocationPopularCountDto>> popularLocationByCount(@PathVariable Integer count) {
        List<String> cities = locationService.getAllLocations().stream().map(Location::getTitle).collect(Collectors.toList());
        if (cities.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        List<LocationPopularCountDto> list = new ArrayList<>();
        for (String city : cities) {
            List<Accommodation> accommodations = accommodationService.findAccommodationsByLocationTitle(city);
            if (accommodations.size() >= count) {
                LocationPopularCountDto dto = new LocationPopularCountDto(city, accommodations.size(), "https://picsum.photos/200/300");
                list.add(dto);
            }
        }
        if (list.isEmpty())
            throw new BadRequestException();

        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/locations/popular")
    @SneakyThrows
    public ResponseEntity<List<LocationPopularDto>> postPopularLocationsByAttribute(HttpServletRequest request) {

        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        List<LocationPopularDto> locationPopularDtos = new ArrayList<>();

        ObjectMapper mapper = new JsonMapper();
        JsonNode json = mapper.readTree(body);
        String type = json.get("type").asText();
        String value = json.get("value").asText();

        HttpHeaders responseHeaders = new HttpHeaders();


        if(type.equals("ALL")){
            for (String country : locationService.getAllCountries()) {
                if (accommodationService.findAccommodationsByLocationCountry(country).size() > 0)
                    locationPopularDtos.add(
                        new LocationPopularDto(type,country,"https://picsum.photos/200/300",accommodationService.findAccommodationsByLocationCountry(country).size()));
            }
        } else if (type.equals("COUNTRY")) {
            if (accommodationService.findAccommodationsByLocationCountry(value).size() > 0)
                locationPopularDtos.add(
                        new LocationPopularDto(type, value, "https://picsum.photos/200/300", accommodationService.findAccommodationsByLocationCountry(value).size()));
        } else if (type.equals("CITY")) {
            if (accommodationService.findAccommodationsByLocationTitle(value).size() > 0)
                locationPopularDtos.add(
                        new LocationPopularDto(type,value,"https://picsum.photos/200/300",accommodationService.findAccommodationsByLocationTitle(value).size()));
        } else {
            if (accommodationService.findByTitle(value).size() > 0)
                locationPopularDtos.add(
                        new LocationPopularDto(type,value,"https://picsum.photos/200/300",accommodationService.findByTitle(value).size()));
        }

        if (locationPopularDtos.size() == 0)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        responseHeaders.set("X-Total-Count", String.valueOf(locationPopularDtos.stream().mapToInt(dto -> dto.getPropertiesCount()).sum()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(locationPopularDtos);
    }
}
