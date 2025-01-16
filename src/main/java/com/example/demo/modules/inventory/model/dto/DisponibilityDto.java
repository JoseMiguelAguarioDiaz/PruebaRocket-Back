package com.example.demo.modules.inventory.model.dto;

import lombok.Data;

@Data
public class DisponibilityDto {
    private int available;
    private int loaned;
}
