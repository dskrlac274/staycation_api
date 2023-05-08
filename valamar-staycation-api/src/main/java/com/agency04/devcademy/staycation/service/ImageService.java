package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.Image;

import java.util.Optional;

public interface ImageService {
    Optional<Image> findByContent(String content);
    Image saveImage(Image image);
}
