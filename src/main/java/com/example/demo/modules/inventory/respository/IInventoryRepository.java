package com.example.demo.modules.inventory.respository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IInventoryRepository {
    @Insert("INSERT INTO INVENTORY (book_id, available) VALUES (#{bookId}, #{available})")
    void insertInventory(Long bookId, int available);
    @Update("UPDATE INVENTORY SET available = #{available} WHERE book_id = #{bookId}")
    void updateInventory(Long bookId, int available);
}
