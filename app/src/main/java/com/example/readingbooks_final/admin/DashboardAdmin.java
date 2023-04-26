package com.example.readingbooks_final.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.readingbooks_final.MainActivity;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.login;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardAdmin extends AppCompatActivity {

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        initView();
        Logout();
    }
    private void initView(){
        logout= findViewById(R.id.btn_logout);
    }
    public void Logout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(DashboardAdmin.this, login.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}