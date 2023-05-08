package com.agency04.devcademy.staycation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Lob
    private  String content;

    public Image(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
