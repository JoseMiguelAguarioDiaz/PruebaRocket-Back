package com.example.demo.modules.student.controller;

import com.example.demo.kernel.ResponseApi;
import com.example.demo.modules.student.controller.dto.SaveStudentDto;
import com.example.demo.modules.student.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @PostMapping("/")
    public ResponseEntity<ResponseApi<?>> insertStudent(@RequestBody SaveStudentDto dto){
        ResponseApi<?> response = studentService.insertStudent(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/get-list")
    public ResponseEntity<ResponseApi<?>> getStudentsList(){
        ResponseApi<?> response = studentService.getStudentsList();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
