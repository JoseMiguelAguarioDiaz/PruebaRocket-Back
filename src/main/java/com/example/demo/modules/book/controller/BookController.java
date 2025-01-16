package com.example.demo.modules.book.controller;

import com.example.demo.kernel.ResponseApi;
import com.example.demo.modules.book.controller.dto.SaveBookDto;
import com.example.demo.modules.book.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    @PostMapping("/")
    public ResponseEntity<ResponseApi<?>> insertBook(@RequestBody SaveBookDto dto){
        ResponseApi<?> response = bookService.insertBook(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
