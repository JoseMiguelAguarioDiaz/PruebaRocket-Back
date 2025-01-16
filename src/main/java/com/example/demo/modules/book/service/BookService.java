package com.example.demo.modules.book.service;

import com.example.demo.kernel.ErrorMessages;
import com.example.demo.kernel.ResponseApi;
import com.example.demo.modules.book.controller.dto.SaveBookDto;
import com.example.demo.modules.book.repository.IBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

@Service
@AllArgsConstructor
@Transactional
public class BookService {
    private final IBookRepository iBookRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseApi<?> insertBook(SaveBookDto dto){
        try{
            if(dto.getTitle() == null || dto.getAuthor() == null || dto.getYear() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.MISSING_FIELDS.name());
            if(dto.getTitle().isBlank() ||dto.getAuthor().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if (dto.getYear() < 0 || dto.getYear() > Year.now().getValue()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if(iBookRepository.existsBookByTitleAndAuthor(dto.getTitle(), dto.getAuthor())) return new ResponseApi<>(HttpStatus.CONFLICT, ErrorMessages.ALREADY_EXISTS.name());

            iBookRepository.insertBook(dto.getTitle(), dto.getAuthor(), dto.getYear());
            return new ResponseApi<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }

}
