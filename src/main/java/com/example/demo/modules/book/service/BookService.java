package com.example.demo.modules.book.service;

import com.example.demo.kernel.ErrorMessages;
import com.example.demo.kernel.ResponseApi;
import com.example.demo.modules.book.controller.dto.SaveBookDto;
import com.example.demo.modules.book.controller.dto.UpdateBookDto;
import com.example.demo.modules.book.model.Book;
import com.example.demo.modules.book.repository.IBookRepository;
import com.example.demo.modules.inventory.respository.IInventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BookService {
    private final IBookRepository iBookRepository;
    private final IInventoryRepository iInventoryRepository;
    @Transactional(rollbackFor = Exception.class)
    public ResponseApi<?> insertBook(SaveBookDto dto){
        try{
            if(dto.getTitle() == null || dto.getAuthor() == null || dto.getYear() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.MISSING_FIELDS.name());
            if(dto.getTitle().isBlank() ||dto.getAuthor().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if (dto.getYear() < 0 || dto.getYear() > Year.now().getValue()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if(dto.getQuantity()<0) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if(iBookRepository.existsBookByTitleAndAuthor(dto.getTitle(), dto.getAuthor())) return new ResponseApi<>(HttpStatus.CONFLICT, ErrorMessages.ALREADY_EXISTS.name());

            Book book = new Book(dto.getTitle(),dto.getAuthor(),dto.getYear());
            iBookRepository.insertBook(book);

            System.out.println("id "+book.getId());
            iInventoryRepository.insertInventory(book.getId(), dto.getQuantity());

            return new ResponseApi<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }

    @Transactional(readOnly = true)
    public ResponseApi<?> getAllBooks(){
        try{
            return new ResponseApi<>(iBookRepository.getBooks(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseApi<?> updateBook(UpdateBookDto dto){
        try{
            if(dto.getId() == null || dto.getTitle() == null || dto.getAuthor() == null || dto.getYear() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.MISSING_FIELDS.name());
            if(dto.getId()<=0 || dto.getTitle().isBlank() ||dto.getAuthor().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if (dto.getYear() < 0 || dto.getYear() > Year.now().getValue()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            Optional<Book> optionalBook = iBookRepository.getOne(dto.getId());
            if(optionalBook.isEmpty()) return new ResponseApi<>(HttpStatus.NOT_FOUND, ErrorMessages.NO_DATA_FOUND.name());

            if(iBookRepository.existsBookByTitleAndAuthorExcludingId(dto.getTitle(), dto.getAuthor(), dto.getId())) return new ResponseApi<>(HttpStatus.CONFLICT, ErrorMessages.ALREADY_EXISTS.name());

            iBookRepository.updateBook(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.getYear());

            return new ResponseApi<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }

    }

}
