package com.example.demo.modules.loan.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetAllLoansDto {
    private Long loanId;
    private String fullname;
    private Date loan_date;
    private boolean returned;
    private Date return_date;
}
