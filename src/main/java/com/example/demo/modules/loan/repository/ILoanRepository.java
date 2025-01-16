package com.example.demo.modules.loan.repository;

import com.example.demo.modules.loan.controller.dto.GetAllLoansDto;
import com.example.demo.modules.loan.model.Loan;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ILoanRepository {
    @Insert("INSERT INTO LOANS (student_id, book_id) VALUES (#{studentId}, #{bookId})")
    void insertLoan(Long studentId, Long bookId);
    @Select("select l.id as loanId, s.fullname, l.loan_date, l.returned, l.return_date from loans l inner join students s on l.student_id = s.id where l.book_id = #{bookid}")
    List<GetAllLoansDto> getAllLoansByBookId(Long bookId);

    @Select("SELECT CASE " +
            "WHEN EXISTS (" +
            "  SELECT 1 FROM loans " +
            "  WHERE id = #{loanId}" +
            ") THEN 1 ELSE 0 END " +
            "FROM dual")
    boolean loanExistsById(Long loanId);

    @Update("UPDATE loans SET returned = 1, return_date = CURRENT_DATE WHERE id = #{loanId}")
    void returnBook(Long loanId);

    @Select("SELECT * FROM loans WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "loanDate", column = "loan_date"),
            @Result(property = "returned", column = "returned"),
            @Result(property = "returnDate", column = "return_date")
    })
    Loan getById(Long id);

}
