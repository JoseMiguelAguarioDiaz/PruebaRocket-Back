package com.example.demo.modules.book.repository;

import com.example.demo.modules.book.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IBookRepository {
    @Insert("INSERT INTO books (title, author, year) VALUES (#{title}, #{author}, #{year})")
    void insertBook(String title, String author, Integer year);
    @Update("UPDATE books SET title = #{title}, author = #{author}, year = #{year} WHERE id = #{id}")
    void updateBook(Integer id, String title, String author, Integer year);
    @Select("SELECT * FROM books")
    List<Book> getBooks();
    @Select("SELECT CASE " +
            "WHEN EXISTS (" +
            "  SELECT 1 FROM books " +
            "  WHERE LOWER(title) = LOWER(#{title}) " +
            "    AND LOWER(author) = LOWER(#{author})" +
            ") THEN 1 ELSE 0 END " +
            "FROM dual")
    boolean existsBookByTitleAndAuthor(@Param("title") String title, @Param("author") String author);

}
