package com.example.fit5046_lab5_groupe.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.example.fit5046_lab5_groupe.Login;
import com.example.fit5046_lab5_groupe.SignUp;
import com.example.fit5046_lab5_groupe.databinding.HomeFragmentBinding;
import com.example.fit5046_lab5_groupe.databinding.MapFragmentBinding;
import com.example.fit5046_lab5_groupe.entity.User;
import com.example.fit5046_lab5_groupe.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;


public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeBinding;
    private UserViewModel userViewModel;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();



        homeBinding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeFragment.this.getActivity(), Login.class));
            }
        });
        return view;
    }


}