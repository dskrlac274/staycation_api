package com.agency04.devcademy.staycation.form;

import com.agency04.devcademy.staycation.dto.AccommodationLocationDto;
import com.agency04.devcademy.staycation.dto.ImageDto;
import com.agency04.devcademy.staycation.model.AccommodationType;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class AccommodationForm {
    @Length(max = 100)
    private String title;
    @Length(max = 150)
    private  String subtitle;
    private  String description;
    @NotNull
    @Min(1)
    @Max(5)
    private  int categorisation;
    private  AccommodationType typeId;
    @NotNull
    private  AccommodationLocationDto location;
    private  List<ImageDto> images;
    @Min(1)
    private int capacity;
    private boolean freeCancel;
    @NotNull
    private String price;
}
