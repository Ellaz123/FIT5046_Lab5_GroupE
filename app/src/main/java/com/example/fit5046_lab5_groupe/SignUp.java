package com.example.fit5046_lab5_groupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fit5046_lab5_groupe.dao.UserDAO;
import com.example.fit5046_lab5_groupe.databinding.ActivitySignUpBinding;
import com.example.fit5046_lab5_groupe.entity.User;
import com.example.fit5046_lab5_groupe.viewmodel.OrderViewModel;
import com.example.fit5046_lab5_groupe.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        Button registerButton = binding.registerBtn;
        Button backButton = binding.backBtn;
        EditText emailEditText= binding.EmailAddress;
        EditText passwordEditText= binding.password;
        EditText confirmPasswordEditText = binding.confirmPassword;
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = emailEditText.getText().toString();
                String password_txt = passwordEditText.getText().toString();
                String confirmPwd_txt = confirmPasswordEditText.getText().toString();
                String address_txt = binding.editTextTextPostalAddress.getText().toString();
                //Input validation
                if (TextUtils.isEmpty(email_txt) ||
                        TextUtils.isEmpty(password_txt)) {
                    String msg = "Empty Username or Password";
                    toastMsg(msg);
                } else if (password_txt.length() < 6) {
                    String msg = "Password is too short";
                    toastMsg(msg);
                } else if (! password_txt.equals(confirmPwd_txt)) {
                    String msg = "Confirm password fail";
                    toastMsg(msg);
                } else if (TextUtils.isEmpty(address_txt)) {
                    String msg = "Empty Address";
                    toastMsg(msg);
                }
                else
                    registerUser(email_txt, password_txt, address_txt);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(SignUp.this, Login.class));
            }
        });
    }

    //Register user on firebase authentication, and save the info in database
    //Then jump to login page
    private void registerUser(String email_txt, String password_txt, String address_txt) {
        auth.createUserWithEmailAndPassword(email_txt,password_txt).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String msg = "Registration Successful";
                    writeNewUser(email_txt, address_txt);
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    toastMsg(msg);
                    startActivity(new Intent(SignUp.this, Login.class));
                }else {
                    String msg = "Registration Unsuccessful";
                    toastMsg(msg);
                }
            }
        });
    }

    // Write user information to database
    public void writeNewUser(String email, String address) {
        email = email.replace(".", "");
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://test-487f4-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference mDatabase = database.getReference();
        mDatabase.child("users").child(email).child("address").setValue(address);
        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);
        User user = new User(email, address);
        userViewModel.insert(user);
    }


    public void toastMsg(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}