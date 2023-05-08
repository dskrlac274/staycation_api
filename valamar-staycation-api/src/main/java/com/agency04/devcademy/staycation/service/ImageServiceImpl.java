package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.Image;
import com.agency04.devcademy.staycation.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    private ImageRepository repository;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Image> findByContent(String content) {
        return repository.findByContent(content);
    }

    @Override
    public Image saveImage(Image image) {
        return repository.save(image);
    }
}
