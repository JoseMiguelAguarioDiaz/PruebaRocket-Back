package com.example.demo.modules.student.service;

import com.example.demo.kernel.ErrorMessages;
import com.example.demo.kernel.ResponseApi;
import com.example.demo.kernel.Validations;
import com.example.demo.modules.student.controller.dto.SaveStudentDto;
import com.example.demo.modules.student.repository.IStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class StudentService {
    private final IStudentRepository iStudentRepository;
    private final Validations validations = new Validations();

    @Transactional(rollbackFor = Exception.class)
    public ResponseApi<?> insertStudent(SaveStudentDto dto){
        try{
            if(dto.getFullname() == null || dto.getEmail() == null || dto.getPhone() == null) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.MISSING_FIELDS.name());
            if(dto.getFullname().isBlank() ||dto.getPhone().isBlank() || dto.getEmail().isBlank()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_FIELDS.name());

            if(validations.isInvalidEmail(dto.getEmail())) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.INVALID_EMAIL.name());

            if(iStudentRepository.existsByEmail(dto.getEmail())) return new ResponseApi<>(HttpStatus.BAD_REQUEST, ErrorMessages.ALREADY_EXISTS.name());

            iStudentRepository.insertStudent(dto.getFullname(), dto.getEmail(), dto.getPhone());

            return new ResponseApi<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }
    @Transactional(readOnly = true)
    public ResponseApi<?> getStudentsList(){
        try{
            return new ResponseApi<>(iStudentRepository.getStudentsList(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR.name());
        }
    }

}
