package com.example.fit5046_lab5_groupe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fit5046_lab5_groupe.databinding.ActivityRegisterDataEntryBinding;

public class RegisterEntry extends AppCompatActivity {
    private ActivityRegisterDataEntryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterDataEntryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

}
