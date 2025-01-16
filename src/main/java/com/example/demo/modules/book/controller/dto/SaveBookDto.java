package com.example.demo.modules.book.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveBookDto {
    private String title;
    private String author;
    private Integer year;
    private Integer quantity;
}
