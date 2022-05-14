package com.example.fit5046_lab5_groupe.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fit5046_lab5_groupe.entity.Order;
import com.example.fit5046_lab5_groupe.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user ORDER BY uid ASC")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    User findByID(int uid);

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);
}
