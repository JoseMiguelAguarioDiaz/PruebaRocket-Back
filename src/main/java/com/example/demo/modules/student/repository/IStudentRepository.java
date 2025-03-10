package com.example.demo.modules.student.repository;

import com.example.demo.modules.student.controller.dto.GetStudentsListDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IStudentRepository {
    @Select("SELECT CASE " +
            "WHEN EXISTS (" +
            "  SELECT 1 FROM students " +
            "  WHERE LOWER(email) = LOWER(#{email})" +
            ") THEN 1 ELSE 0 END " +
            "FROM dual")
    boolean existsByEmail(String email);
    @Insert("INSERT INTO students (fullname, email, phone) VALUES (#{name}, #{email}, #{phone})")
    void insertStudent(String name, String email, String phone);
    @Select("SELECT id, fullname FROM students")
    List<GetStudentsListDto> getStudentsList();
    @Select("SELECT CASE " +
            "WHEN EXISTS (" +
            "  SELECT 1 FROM students " +
            "  WHERE id = #{id}" +
            ") THEN 1 ELSE 0 END " +
            "FROM dual")
    boolean existsById(Long id);

}
