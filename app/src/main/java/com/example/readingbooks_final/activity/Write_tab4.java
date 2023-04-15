package com.example.readingbooks_final.activity;

import static com.example.readingbooks_final.activity.Write_tab2.AUTHOR;
import static com.example.readingbooks_final.activity.Write_tab2.CATEGORY;
import static com.example.readingbooks_final.activity.Write_tab2.COVER;
import static com.example.readingbooks_final.activity.Write_tab2.DESCRIPTION;
import static com.example.readingbooks_final.activity.Write_tab2.STATUS;
import static com.example.readingbooks_final.activity.Write_tab2.TITLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.readingbooks_final.R;

public class Write_tab4 extends AppCompatActivity {
    private EditText title_chapter, content_chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab4);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);
        initView();
        recieveData();
    }
    private void initView(){
        title_chapter=findViewById(R.id.title_chapter);
        content_chapter=findViewById(R.id.content_chapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.add_chapter){
            intentToTab5();
        }
        return super.onOptionsItemSelected(item);
    }

    private void recieveData(){
        Intent intent = getIntent();
        String title_reply = intent.getStringExtra(TITLE);
        String author_reply = intent.getStringExtra(AUTHOR);
        String category_reply = intent.getStringExtra(CATEGORY);
        String status_reply = intent.getStringExtra(STATUS);
        String description_reply = intent.getStringExtra(DESCRIPTION);
        String cover_reply = intent.getStringExtra(COVER);

    }

    private void intentToTab5(){
        Intent intent= new Intent(Write_tab4.this, Write_tab5.class);
        startActivity(intent);
    }
}