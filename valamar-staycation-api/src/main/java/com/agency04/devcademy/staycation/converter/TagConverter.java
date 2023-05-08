package com.agency04.devcademy.staycation.converter;

import com.agency04.devcademy.staycation.dto.TagsDto;
import com.agency04.devcademy.staycation.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {
    public TagsDto convertToDto(Tag tag){
        return new TagsDto(tag.getName());
    }
}
