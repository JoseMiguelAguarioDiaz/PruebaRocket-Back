package com.example.demo.modules.student.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
