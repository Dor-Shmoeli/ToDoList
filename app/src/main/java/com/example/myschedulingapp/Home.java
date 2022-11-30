package com.example.myschedulingapp;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;



import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;



public class Home extends AppCompatActivity {

    private Toolbar toolbar;

    private FloatingActionButton fbSignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        setListeners();

    }

    private void findViews() {
        fbSignOut = findViewById(R.id.fb_signout);
        toolbar = findViewById(R.id.tb_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My To Do List");


    }

    private void setListeners() {

        fbSignOut.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

        });

    }



}





