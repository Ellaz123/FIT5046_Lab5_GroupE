package com.example.fit5046_lab5_groupe.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fit5046_lab5_groupe.entity.Order;
import com.example.fit5046_lab5_groupe.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user ORDER BY uid ASC")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user ORDER BY uid ASC")
    List<User> getAllInList();

    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    User findByID(int uid);

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    User findByEmail(String email);

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);
}
