package com.example.demo.modules.loan.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Loan {
    private Long id;
    private Date loanDate;
    private Date returnDate;
    private Long studentId;
    private Long bookId;
}
