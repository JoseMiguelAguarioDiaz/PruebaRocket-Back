package com.example.demo.modules.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Student {
    private Long id;
    private String fullname;
    private String phone;
    private String email;
}
