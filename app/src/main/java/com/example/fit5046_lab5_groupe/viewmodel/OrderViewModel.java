package com.example.fit5046_lab5_groupe.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fit5046_lab5_groupe.entity.Order;
import com.example.fit5046_lab5_groupe.repository.OrderRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository oRepository;
    private LiveData<List<Order>> allCustomers;
    public OrderViewModel (Application application) {
        super(application);
        oRepository = new OrderRepository(application);
        allCustomers = oRepository.getAllCustomers();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<Order> findByIDFuture(final int customerId){
        return oRepository.findByIDFuture(customerId);
    }
    public LiveData<List<Order>> getAllCustomers() {
        return allCustomers;
    }

    public void insert(Order order) {
        oRepository.insert(order);
    }
    public void deleteAll()
    {
        oRepository.deleteAll();
    }
    public void update(Order customer) {
        oRepository.updateOrder(customer);
    }
}
