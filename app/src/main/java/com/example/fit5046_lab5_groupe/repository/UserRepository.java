package com.example.fit5046_lab5_groupe.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.fit5046_lab5_groupe.dao.UserDAO;
import com.example.fit5046_lab5_groupe.database.Database;
import com.example.fit5046_lab5_groupe.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class UserRepository {
    private UserDAO UserDao;
    private LiveData<List<User>> allUsers;
    public UserRepository(Application application){
        Database db = Database.getInstance(application);
        UserDao =db.userDao();
        allUsers= UserDao.getAll();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    public void insert(final User User){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                UserDao.insertUser(User);
            }
        });
    }
    public void deleteAll(){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                UserDao.deleteAll();
            }
        });
    }
    public void delete(final User User){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                UserDao.deleteUser(User);
            }
        });
    }
    public void updateUser(final User User){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                UserDao.updateUser(User);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<User> findByIDFuture(final int UserId) {
        return CompletableFuture.supplyAsync(new Supplier<User>() {
            @Override
            public User get() {
                return UserDao.findByID(UserId);
            }
        }, Database.databaseWriteExecutor);
    }
}

