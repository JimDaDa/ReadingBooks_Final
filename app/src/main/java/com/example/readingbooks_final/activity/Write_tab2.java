package com.example.readingbooks_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.readingbooks_final.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class Write_tab2 extends AppCompatActivity {

    private EditText title_book, author_book, category_book, status_book, description_book;
    private RoundedImageView roundedImageView;


    public static final String TITLE = "TITLE";
    public static final String AUTHOR = "AUTHOR";
    public static final String CATEGORY = "CATEGORY";
    public static final String STATUS = "STATUS";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String COVER = "COVER";
    private TextView add_cover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab2);
        ActionBar actionBar=getSupportActionBar();
       // assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

       // actionBar.setDisplayShowHomeEnabled(true);
        initView();
    }
    private void initView(){
        title_book= findViewById(R.id.tv_title_book);
        author_book=findViewById(R.id.tv_author_book);
        category_book=findViewById(R.id.tv_category_book);
        status_book=findViewById(R.id.tv_status_book);
        description_book=findViewById(R.id.tv_description_book);
        add_cover=findViewById(R.id.add_cover);
        roundedImageView=findViewById(R.id.roundedImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== R.id.add_books){

            putDatatoTab3();

        }
        return super.onOptionsItemSelected(item);
    }

    private void putDatatoTab3(){
        Intent intent = new Intent(Write_tab2.this, Write_tab3.class);

        String content_title= title_book.getText().toString().trim();
        String content_author= author_book.getText().toString().trim();
        String content_category= category_book.getText().toString().trim();
        String content_status= status_book.getText().toString().trim();
        String content_description= description_book.getText().toString().trim();
        // String content_author= add_cover.getText().toString().trim();


        intent.putExtra(TITLE, content_title);
        intent.putExtra(AUTHOR, content_author);
        intent.putExtra(CATEGORY, content_category);
        intent.putExtra(STATUS, content_status);
        intent.putExtra(DESCRIPTION, content_description);

        startActivity(intent);
    }
}