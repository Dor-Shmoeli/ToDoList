package com.example.myschedulingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myschedulingapp.controllers.Login;
import com.example.myschedulingapp.ui.main.MainFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        goToAuthIfNotLogin();

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {

            goToAuthIfNotLogin();

        });


    }

    private boolean isLogin() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
    }

    private void goToAuthIfNotLogin() {
        if (!isLogin()) {
            startActivity(new Intent(this, Login.class));
            finish();
        }else {startActivity(new Intent(this, Home.class));}


    }

}