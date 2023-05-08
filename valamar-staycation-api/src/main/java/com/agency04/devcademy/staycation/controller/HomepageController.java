package com.agency04.devcademy.staycation.controller;

import com.agency04.devcademy.staycation.dto.HomepageImageDto;
import com.agency04.devcademy.staycation.exception.BadRequestException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

@Controller
public class HomepageController {

    @GetMapping("/landing")
    public ResponseEntity<HomepageImageDto> listImages() throws IOException {
        File imageFolder = null;
        try {
            imageFolder = new ClassPathResource("static/images").getFile();
        }
        catch (Exception e) {
            throw new BadRequestException();
        }
        HomepageImageDto images = new HomepageImageDto(new ArrayList<>());

        for (File file : imageFolder.listFiles()) {
            byte[] fileContent = Files.readAllBytes(file.toPath());

            int tocka = file.toPath().toString().lastIndexOf(".");
            String extension = file.toPath().toString().substring(tocka);

            String base64;
            if (extension.equals(".png"))
                base64 = "data:image/png;base64,";
            else
                base64 = "data:image/jpeg;base64,";
            base64 += Base64.getEncoder().encodeToString(fileContent);
            images.getImages().add(base64);
        }
        return new ResponseEntity(images, HttpStatus.OK);
    }
}
