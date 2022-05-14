package com.example.fit5046_lab5_groupe.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithOrders {
        @Embedded
        public User user;

        @Relation(
                parentColumn = "uid",
                entityColumn = "orderId"
        )

        public List<Order> Orders;
}
