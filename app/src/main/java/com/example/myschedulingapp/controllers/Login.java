package com.example.myschedulingapp.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschedulingapp.Home;
import com.example.myschedulingapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    TextView btnRegisterNow;
    ProgressDialog pdLoader;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();
        setListeners();

    }

    private void findView() {

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegisterNow = findViewById(R.id.btn_register_now);

//


        pdLoader = new ProgressDialog(this);

    }

    private void setListeners() {

        btnLogin.setOnClickListener(v -> {
            pdLoader.setMessage("Login in progress...");
            pdLoader.setCanceledOnTouchOutside(false);
            pdLoader.show();

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();



            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        startActivity(new Intent(this, Home.class));
                        Toast.makeText(this, "Welcome: " + authResult.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                        pdLoader.dismiss();



                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        pdLoader.dismiss();

                    });

        });
        btnRegisterNow.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }
}