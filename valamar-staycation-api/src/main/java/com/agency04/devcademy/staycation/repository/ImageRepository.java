package com.agency04.devcademy.staycation.repository;

import com.agency04.devcademy.staycation.model.Image;
import com.agency04.devcademy.staycation.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByContent(String content);
}
