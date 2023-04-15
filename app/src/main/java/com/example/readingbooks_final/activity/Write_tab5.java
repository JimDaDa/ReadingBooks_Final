package com.example.readingbooks_final.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.readingbooks_final.R;

public class Write_tab5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab5);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);
    }
}