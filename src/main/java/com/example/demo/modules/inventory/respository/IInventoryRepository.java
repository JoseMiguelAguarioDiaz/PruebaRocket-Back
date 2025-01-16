package com.example.demo.modules.inventory.respository;

import com.example.demo.modules.inventory.model.dto.DisponibilityDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IInventoryRepository {
    @Insert("INSERT INTO INVENTORY (book_id, available) VALUES (#{bookId}, #{available})")
    void insertInventory(Long bookId, int available);
    @Update("UPDATE INVENTORY SET available = #{available} WHERE book_id = #{bookId}")
    void updateInventory(Long bookId, int available);
    @Update("UPDATE INVENTORY SET loaned = #{loaned} WHERE book_id = #{bookId}")
    void updateLoaned(Long bookId, int loaned);

    @Select("SELECT available, loaned FROM INVENTORY WHERE book_id = #{bookId}")
    DisponibilityDto getDisponibility(Long bookId);
}
