package com.example.fit5046_lab5_groupe.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fit5046_lab5_groupe.entity.User;
import com.example.fit5046_lab5_groupe.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class UserViewModel extends AndroidViewModel {
    private UserRepository cRepository;
    private LiveData<List<User>> allUsers;
    public UserViewModel (Application application) {
        super(application);
        cRepository = new UserRepository(application);
        allUsers = cRepository.getAllUsers();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<User> findByIDFuture(final int UserId){
        return cRepository.findByIDFuture(UserId);
    }
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    public void insert(User User) {
        cRepository.insert(User);
    }

    public void deleteAll() {
        cRepository.deleteAll();
    }
    public void update(User User) {
        cRepository.updateUser(User);
    }
}


