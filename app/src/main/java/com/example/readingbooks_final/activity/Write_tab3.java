package com.example.readingbooks_final.activity;

import static com.example.readingbooks_final.activity.Write_tab2.AUTHOR;
import static com.example.readingbooks_final.activity.Write_tab2.CATEGORY;
import static com.example.readingbooks_final.activity.Write_tab2.DESCRIPTION;
import static com.example.readingbooks_final.activity.Write_tab2.STATUS;
import static com.example.readingbooks_final.activity.Write_tab2.TITLE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.readingbooks_final.R;

public class Write_tab3 extends AppCompatActivity {

    private TextView title_book, author_book, category_book, status_book, description_book;
    private  Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab3);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        // actionBar.setDisplayShowHomeEnabled(true);
        initView();
        initView();
        RecieveData();
    }

    private void initView(){
        title_book=findViewById(R.id.tv_title_book);
        author_book=findViewById(R.id.tv_author_book);
        category_book=findViewById(R.id.tv_category_book);
        status_book=findViewById(R.id.tv_status_book);
        description_book=findViewById(R.id.tv_description_book);
        create=findViewById(R.id.create);
    }

    private void RecieveData(){
        Intent intent = getIntent();
        String title_reply = intent.getStringExtra(TITLE);
        String author_reply = intent.getStringExtra(AUTHOR);
        String category_reply = intent.getStringExtra(CATEGORY);
        String status_reply = intent.getStringExtra(STATUS);
        String description_reply = intent.getStringExtra(DESCRIPTION);
        title_book.setText(title_reply);
        author_book.setText(author_reply);
        category_book.setText(category_reply);
        status_book.setText(status_reply);
        description_book.setText(description_reply);
    }
}