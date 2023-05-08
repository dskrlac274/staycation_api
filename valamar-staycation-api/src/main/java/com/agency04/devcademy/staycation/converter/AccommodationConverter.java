package com.agency04.devcademy.staycation.converter;

import com.agency04.devcademy.staycation.dto.*;
import com.agency04.devcademy.staycation.form.AccommodationForm;
import com.agency04.devcademy.staycation.model.Accommodation;
import com.agency04.devcademy.staycation.model.Image;
import com.agency04.devcademy.staycation.model.Location;
import com.agency04.devcademy.staycation.service.ImageService;
import com.agency04.devcademy.staycation.service.LocationService;
import com.agency04.devcademy.staycation.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccommodationConverter {
    private final ImageConverter converter;
    private final UserService userService;
    private final LocationService locationService;
    private final TagConverter tagConverter;
    private final ImageService imageService;

    public AccommodationConverter(ImageConverter converter, UserService userService, LocationService locationService, TagConverter tagConverter, ImageService imageService) {
        this.converter = converter;
        this.userService = userService;
        this.locationService = locationService;
        this.tagConverter = tagConverter;
        this.imageService = imageService;
    }

    public AccommodationFilterDto convertToAccommodationFilterDto(Accommodation accommodation){
        return new AccommodationFilterDto(accommodation.getId(),
                accommodation.getTitle(),
                accommodation.getLocation().getTitle(),
                accommodation.getPrice(),
                accommodation.getCategorisation(),
                accommodation.getImages().get(0).getContent());
    }
    public UserAccommodationDto convertToUserAccommodationDto(Accommodation accommodation){
        return new UserAccommodationDto(accommodation.getId(),
                accommodation.getTitle(),
                accommodation.getLocation().getTitle(),
                accommodation.getImages(),
                accommodation.getCategorisation(),
                accommodation.getDescription());
    }
    public UserAccommodationDetailsDto convertToUserAccommodationDetailDto(Accommodation accommodation){
        AccommodationLocationDto locationDto=new AccommodationLocationDto(accommodation.getLocation().getCountry(), accommodation.getLocation().getTitle(), accommodation.getLocation().getPostalCode());
        List<ImageDto> images=accommodation.getImages().stream().map(this.converter::convertToDto).collect(Collectors.toList());
        return new UserAccommodationDetailsDto(accommodation.getId(), accommodation.getTitle(),
                accommodation.getSubtitle(), accommodation.getDescription(),
                accommodation.getCategorisation(),
                accommodation.getAccommodationType(),
                locationDto,images,accommodation.getPersonCount(), accommodation.getFreeCancelation(), accommodation.getPrice());
    }

    public Accommodation convertToAccommodation(AccommodationForm form){
        Optional<Location> location=this.locationService.findByPostalCode(form.getLocation().getPostalCode());
        List<Image> unsavedImages = form.getImages().stream().map(converter::convertToImage).collect(Collectors.toList());
        List<Image> images = new ArrayList<>();
        for (Image image : unsavedImages) {
            images.add(imageService.saveImage(image));
        }

        return new Accommodation(userService.getCurrentUser().get(), form.getTitle(),
                form.getSubtitle(), form.getTypeId(),
                images, form.getCategorisation(),
                new BigDecimal(form.getPrice()), form.getCapacity(),
                location.get());
    }

    public AccommodationOptionsDto convertToAccommodationOptionsDto(Accommodation accommodation){
        return new AccommodationOptionsDto(accommodation.getTitle(), accommodation.getDescription(),
                accommodation.getTags().stream().map(tagConverter::convertToDto).collect(Collectors.toList()), accommodation.getPersonCount(),
                        accommodation.getPrice());
    }

}
