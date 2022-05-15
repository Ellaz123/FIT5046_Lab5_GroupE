package com.example.fit5046_lab5_groupe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fit5046_lab5_groupe.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;

/**
 * When a user completes registration, autofill the email and password in login screen
 *
    private final ActivityResultLauncher<Intent> signUpLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                assert data != null;
                                String email=data.getStringExtra("email");
                                String pwd=data.getStringExtra("pwd");
                                //set textview and password: type?
                            }
                        }
                    });
 **/

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.reload();
        }
    }

    // Final version
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        EditText emailEditText = binding.emailAddressText;
        EditText passwordEditText = binding.passwordText;

        //Go to the sign up page
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        //Login with email and password
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Validate the input
                    String txt_Email = emailEditText.getText().toString();
                    String txt_Pwd = passwordEditText.getText().toString();
                    loginUser(txt_Email, txt_Pwd);
                } catch (IllegalArgumentException e) {
                    toastMsg("Email address or password is empty");
                }
            }
        });

        //Go to the reset password page
        binding.restPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });
    }

    //Login with email and password
    private void loginUser(String txt_email, String txt_pwd) {
        auth.signInWithEmailAndPassword(txt_email, txt_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String msg =  "Login Successful";
                    toastMsg(msg);
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("CurrentUserEmail",txt_email);
                    startActivity(intent);
                }
                else {
                    String msg = "Login failed. Please check your email and password";
                    toastMsg(msg);
                }
            }
        });
    }

    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
