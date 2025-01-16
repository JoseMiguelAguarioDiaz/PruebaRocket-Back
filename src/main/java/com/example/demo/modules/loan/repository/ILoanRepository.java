package com.example.demo.modules.loan.repository;

import com.example.demo.modules.loan.controller.dto.GetAllLoansDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ILoanRepository {
    @Insert("INSERT INTO LOANS (student_id, book_id) VALUES (#{studentId}, #{bookId})")
    void insertLoan(Long studentId, Long bookId);
    @Select("select l.id as loanId, s.fullname, l.loan_date, l.returned, l.return_date from loans l inner join students s on l.student_id = s.id where l.book_id = #{bookid}")
    List<GetAllLoansDto> getAllLoansByBookId(Long bookId);

}
