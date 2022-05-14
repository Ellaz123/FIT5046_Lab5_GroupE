package com.example.fit5046_lab5_groupe.entity;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "email")
    @NonNull
    public String email;

    @ColumnInfo(name = "address")
    @NonNull
    public String address;

    public User(@NonNull String email,
                @NonNull String address) {
        this.email = email;
        this.address = address;
    }
}
