package com.example.fit5046_lab5_groupe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fit5046_lab5_groupe.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = getIntent();
                //get email and pwd
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}