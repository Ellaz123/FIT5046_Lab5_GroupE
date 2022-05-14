package com.example.fit5046_lab5_groupe.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fit5046_lab5_groupe.entity.Order;

import java.util.*;

@Dao
public interface OrderDAO {
    @Query("SELECT * FROM `order` ORDER BY orderId ASC")
    LiveData<List<Order>> getAll();

    @Query("SELECT * FROM `order` WHERE orderId = :orderId LIMIT 1")
    Order findByID(int orderId);

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);

    @Update
    void updateOrder(Order order);

    @Query("DELETE FROM `order`")
    void deleteAll();
}
