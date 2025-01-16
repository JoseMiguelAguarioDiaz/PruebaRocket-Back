package com.example.demo.modules.book.model;

import lombok.*;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer year;
}
