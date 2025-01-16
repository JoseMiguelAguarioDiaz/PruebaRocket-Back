package com.example.demo.modules.inventory.respository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IInventoryRepository {
    @Insert("INSERT INTO INVENTORY (book_id, available) VALUES (#{bookId}, #{available})")
    void insertInventory(Long bookId, int available);
}
