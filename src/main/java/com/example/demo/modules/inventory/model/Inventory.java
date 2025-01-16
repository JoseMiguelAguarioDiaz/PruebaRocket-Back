package com.example.demo.modules.inventory.model;

import lombok.*;

@Data
public class Inventory {
    private Long id;
    private Long bookId;
    private int available;
    private int loaned;
}
