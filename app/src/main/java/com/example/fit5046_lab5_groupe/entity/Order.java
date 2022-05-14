package com.example.fit5046_lab5_groupe.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order {
    @PrimaryKey (autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "order_date")
    @NonNull
    public String orderDate;

    @ColumnInfo(name = "order_time")
    @NonNull
    public String orderTime;

    @ColumnInfo(name = "num_of_rats")
    @NonNull
    public String numRat;

    @ColumnInfo(name = "rat_Type")
    @NonNull
    public String ratType;

    @ColumnInfo(name = "customer_name")
    @NonNull
    public String customerName;

    @ColumnInfo(name = "customer_phone")
    @NonNull
    public String customerPhone;

    public Order(@NonNull String orderDate, @NonNull String orderTime, @NonNull String numRat,
                 @NonNull String ratType, @NonNull String customerName,
                 @NonNull String customerPhone){
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.numRat = numRat;
        this.ratType = ratType;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }
}
