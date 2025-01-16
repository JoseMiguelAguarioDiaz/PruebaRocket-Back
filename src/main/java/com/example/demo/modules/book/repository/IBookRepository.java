package com.example.demo.modules.book.repository;

import com.example.demo.modules.book.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IBookRepository {
    @Insert("INSERT INTO books (id, title, author, year) VALUES (BOOK_SEQ.NEXTVAL, #{title}, #{author}, #{year})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertBook(Book book);
    @Update("UPDATE books SET title = #{title}, author = #{author}, year = #{year} WHERE id = #{id}")
    void updateBook(Long id, String title, String author, Integer year);
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
    @Select("SELECT CASE " +
            "WHEN EXISTS (" +
            "  SELECT 1 FROM books " +
            "  WHERE LOWER(title) = LOWER(#{title}) " +
            "    AND LOWER(author) = LOWER(#{author})" +
            "    AND id <> #{id} " +
            ") THEN 1 ELSE 0 END " +
            "FROM dual")
    boolean existsBookByTitleAndAuthorExcludingId(@Param("title") String title, @Param("author") String author, @Param("id") Long id);
    @Select("SELECT * FROM books WHERE id = #{id}")
    Optional<Book> getOne(Long id);

}
