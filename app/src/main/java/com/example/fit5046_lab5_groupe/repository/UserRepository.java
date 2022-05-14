package com.example.fit5046_lab5_groupe.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fit5046_lab5_groupe.dao.UserDAO;
import com.example.fit5046_lab5_groupe.database.Database;
import com.example.fit5046_lab5_groupe.entity.User;

import java.util.List;

public class UserRepository {

        private UserDAO userDao;
        private LiveData<List<User>> allUsers;
        private User user;

        public UserRepository(Application application){
            Database db = Database.getInstance(application);
            userDao=db.userDao();
            allUsers=userDao.getAll();
        }

        public LiveData<List<User>> getAllUsers() {
            return allUsers;
        }

        public void insert(final User user) {
            Database.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    userDao.insertUser(user);
                }
            });
        }

        public void deleteAll(){
            Database.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    userDao.deleteAll();
                }
            });
        }
}
