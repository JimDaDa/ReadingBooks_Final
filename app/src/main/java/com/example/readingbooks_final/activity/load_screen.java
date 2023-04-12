package com.example.readingbooks_final.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.readingbooks_final.MainActivity;
import com.example.readingbooks_final.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class load_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();

            }
        },3000);

    }
    private void nextActivity (){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            //Chưa login
            Intent intent=new Intent(load_screen.this,login.class);
            startActivity(intent);
        } else {
            Intent intent=new Intent(load_screen.this, MainActivity.class);
            startActivity(intent);

        }
    }
    }
