package com.example.demo.modules.loan.controller;

import com.example.demo.kernel.ResponseApi;
import com.example.demo.modules.loan.controller.dto.SaveLoanDto;
import com.example.demo.modules.loan.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/")
    public ResponseEntity<ResponseApi<?>> insert (@RequestBody SaveLoanDto dto){
        ResponseApi<?> response = loanService.insertLoan(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
