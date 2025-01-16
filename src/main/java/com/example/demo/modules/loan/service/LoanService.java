package com.example.demo.modules.loan.service;

import com.example.demo.kernel.ErrorMessages;
import com.example.demo.kernel.ResponseApi;
import com.example.demo.modules.book.repository.IBookRepository;
import com.example.demo.modules.inventory.model.dto.DisponibilityDto;
import com.example.demo.modules.inventory.respository.IInventoryRepository;
import com.example.demo.modules.loan.controller.dto.GetLoansByBookIdDto;
import com.example.demo.modules.loan.controller.dto.ReturnBookDto;
import com.example.demo.modules.loan.controller.dto.SaveLoanDto;
import com.example.demo.modules.loan.model.Loan;
import com.example.demo.modules.loan.repository.ILoanRepository;
import com.example.demo.modules.student.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class LoanService {
    private final ILoanRepository iLoanRepository;
    private final IStudentRepository iStudentRepository;
    private final IBookRepository iBookRepository;
    private final IInventoryRepository iInventoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseApi<?> insertLoan(SaveLoanDto dto){
        try{
            if(dto.getStudentId() == null || dto.getBookId() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.MISSING_FIELDS.name());
            if(dto.getStudentId() <= 0 || dto.getBookId() <= 0) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if(!iStudentRepository.existsById(dto.getStudentId())) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.NO_DATA_FOUND.name());
            if(!iBookRepository.existsBookById(dto.getBookId())) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.NO_DATA_FOUND.name());


            DisponibilityDto disponibilityDto = iInventoryRepository.getDisponibility(dto.getBookId());
            if(disponibilityDto == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.NO_DATA_FOUND.name());
            if(disponibilityDto.getAvailable() <= 0) return new ResponseApi<>(HttpStatus.CONFLICT, ErrorMessages.NOT_ENOUGH_STOCK.name());

            disponibilityDto.setAvailable(disponibilityDto.getAvailable() - 1);
            disponibilityDto.setLoaned(disponibilityDto.getLoaned() + 1);

            iInventoryRepository.updateInventory(dto.getBookId(), disponibilityDto.getAvailable());
            iInventoryRepository.updateLoaned(dto.getBookId(), disponibilityDto.getLoaned());

            iLoanRepository.insertLoan(dto.getStudentId(), dto.getBookId());
            return new ResponseApi<>(HttpStatus.OK, "Loan created successfully");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }

    @Transactional(readOnly = true)
    public ResponseApi<?> getAllLoans(GetLoansByBookIdDto dto){
        try{
            if(dto.getBookId() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.MISSING_FIELDS.name());
            if(dto.getBookId() <= 0) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if(!iBookRepository.existsBookById(dto.getBookId())) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.NO_DATA_FOUND.name());


            return new ResponseApi<>(iLoanRepository.getAllLoansByBookId(dto.getBookId()), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public ResponseApi<?> returnBook(ReturnBookDto dto){
        try{
            if(dto.getLoanId() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.MISSING_FIELDS.name());
            if(dto.getLoanId()<=0) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if(!iLoanRepository.loanExistsById(dto.getLoanId())) return new ResponseApi<>(HttpStatus.NOT_FOUND, ErrorMessages.NO_DATA_FOUND.name());

            Loan loan = iLoanRepository.getById(dto.getLoanId());

            DisponibilityDto disponibilityDto = iInventoryRepository.getDisponibility(loan.getBookId());

            if(disponibilityDto == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.NO_DATA_FOUND.name());
            if(disponibilityDto.getAvailable() <= 0) return new ResponseApi<>(HttpStatus.CONFLICT, ErrorMessages.NOT_ENOUGH_STOCK.name());

            disponibilityDto.setAvailable(disponibilityDto.getAvailable() + 1);
            disponibilityDto.setLoaned(disponibilityDto.getLoaned() - 1);

            iInventoryRepository.updateInventory(loan.getBookId(), disponibilityDto.getAvailable());
            iInventoryRepository.updateLoaned(loan.getBookId(), disponibilityDto.getLoaned());

            iLoanRepository.returnBook(dto.getLoanId());

            return new ResponseApi<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }
}
