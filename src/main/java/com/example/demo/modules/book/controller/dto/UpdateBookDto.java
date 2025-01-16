package com.example.demo.modules.book.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookDto {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private Integer quantity;
}
