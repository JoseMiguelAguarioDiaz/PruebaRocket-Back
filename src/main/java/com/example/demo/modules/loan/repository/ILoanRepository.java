package com.example.demo.modules.loan.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILoanRepository {
    @Insert("INSERT INTO LOANS (student_id, book_id) VALUES (#{studentId}, #{bookId})")
    void insertLoan(Long studentId, Long bookId);
}
