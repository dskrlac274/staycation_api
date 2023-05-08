package com.agency04.devcademy.staycation.converter;

import com.agency04.devcademy.staycation.dto.ImageDto;
import com.agency04.devcademy.staycation.model.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {

    public ImageDto convertToDto(Image image){
        return new ImageDto(image.getTitle(), image.getContent());
    }
    public Image convertToImage(ImageDto imageDto){
        return new Image(imageDto.getTitle(), imageDto.getContent());
    }
}
