package com.example.demo.modules.loan.model;

import lombok.Data;
import java.util.Date;

@Data
public class Loan {
    private Long id;
    private Date loanDate;
    private Long studentId;
    private Long bookId;
    private boolean returned;
    private Date returnDate;
}
