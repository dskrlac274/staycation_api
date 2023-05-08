package com.agency04.devcademy.staycation.converter;

import com.agency04.devcademy.staycation.dto.LocationDto;
import com.agency04.devcademy.staycation.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {
    public LocationDto convertToLocationDto(Location location){
        return new LocationDto(location.getId(), location.getTitle(), location.getCountry());
    }
}
