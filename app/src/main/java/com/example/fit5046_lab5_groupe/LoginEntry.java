package com.example.fit5046_lab5_groupe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fit5046_lab5_groupe.databinding.ActivityLoginDataEntryBinding;

public class LoginEntry extends AppCompatActivity {
    private ActivityLoginDataEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginDataEntryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}
