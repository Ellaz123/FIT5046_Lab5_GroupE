package com.example.fit5046_lab5_groupe.repository;

import android.app.*;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.fit5046_lab5_groupe.dao.OrderDAO;
import com.example.fit5046_lab5_groupe.database.Database;
import com.example.fit5046_lab5_groupe.entity.Order;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class OrderRepository {
    private OrderDAO orderDao;
    private LiveData<List<Order>> allOrders;

    public OrderRepository(Application application){
        Database db = Database.getInstance(application);
        orderDao =db.orderDao();
        allOrders= orderDao.getAll();
    }

    public LiveData<List<Order>> getAllCustomers() {
        return allOrders;
    }

    public void insert(final Order order){
        Database.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            orderDao.insert(order);
        } });
    }

    public void deleteAll() {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderDao.deleteAll();
            }
        });
    }

    public void delete(final Order order){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderDao.delete(order);
            }
        });
    }

    public void updateOrder(final Order order){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { orderDao.updateOrder(order);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Order> findByIDFuture(final int orderId) {
        return CompletableFuture.supplyAsync(new Supplier<Order>() {
            @Override
            public Order get() {
                return orderDao.findByID(orderId);
            }
        }, Database.databaseWriteExecutor);
    }
}
