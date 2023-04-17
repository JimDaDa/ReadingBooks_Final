package com.example.readingbooks_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.readingbooks_final.R;

public class show_info_book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info_book);
        ActionBar actionBar=getSupportActionBar();
        // assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_info_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId()== R.id.add_chapter_show_info_book){
            openChater();
        }
        if (item.getItemId()== R.id.edit_chapter){
            openEdit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openChater(){

    }
    private void openEdit(){

    }
}