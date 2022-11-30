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

public class Register extends AppCompatActivity {


    EditText etEmail;
    EditText etPassword;


    Button btnRegister;
    TextView tvLoginNow;

    Toolbar tbToolBar;

    ProgressDialog pdLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        setListeners();
    }


    private void findViews() {

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_register_password);

        tbToolBar = findViewById(R.id.tb_register);
        setSupportActionBar(tbToolBar);
        getSupportActionBar().setTitle("Registration");

        pdLoader = new ProgressDialog(this);



        btnRegister = findViewById(R.id.btn_register);
        tvLoginNow = findViewById(R.id.tv_login_now);

    }

    private void setListeners() {
        btnRegister.setOnClickListener(v -> {

            pdLoader.setMessage("Registration in progress...");
            pdLoader.setCanceledOnTouchOutside(false);
            pdLoader.show();

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        startActivity(new Intent(this, Home.class));
                        Toast.makeText(this, "Created: " + authResult.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                        pdLoader.dismiss();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        pdLoader.dismiss();
                    });
        });

        tvLoginNow.setOnClickListener(v->{
            finish();
        });
    }

}