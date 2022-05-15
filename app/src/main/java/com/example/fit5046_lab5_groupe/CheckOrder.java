package com.example.fit5046_lab5_groupe;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fit5046_lab5_groupe.databinding.ActivityCheckOrderBinding;
import com.example.fit5046_lab5_groupe.databinding.ActivityMainBinding;
import com.example.fit5046_lab5_groupe.databinding.DataEntryFragmentBinding;
import com.example.fit5046_lab5_groupe.entity.Order;
import com.example.fit5046_lab5_groupe.viewmodel.OrderViewModel;

import java.util.List;

public class CheckOrder extends AppCompatActivity {
    private ActivityCheckOrderBinding binding;
    private OrderViewModel orderViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        orderViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).
                create(OrderViewModel.class);

        orderViewModel.getAllCustomers().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                String allOrders = "";
                int count = 1;
                for(Order temp: orders){
                    String orderDetails = (count + " " + temp.orderDate+" "+temp.orderTime+" "
                            +temp.numRat + " " + temp.ratType + " " + temp.customerName + " " +
                            temp.customerPhone);
                    allOrders = allOrders + System.getProperty("line.separator") + orderDetails;
                    count += 1;
                }
                binding.orderView.setText(allOrders);

            }
        });



    }
}
