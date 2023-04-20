package com.example.readingbooks_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;

public class DetailBooks extends AppCompatActivity {

    private ImageView cover_detail;
    private TextView title_details, author_details, description_details;
    private Button read_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_book);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        recieveData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void initView(){
        cover_detail=findViewById(R.id.cover_detail);
        title_details=findViewById(R.id.title_details);
        author_details=findViewById(R.id.author_details);
        description_details=findViewById(R.id.description_details);
        read_books=findViewById(R.id.read_books);
    }
    private void recieveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            title_details.setText(books_data.getTitle());
            author_details.setText(books_data.getAuthors());
//            category_book.setText(books_data.getCategory());
//            status_book.setText(books_data.getStatus());
            description_details.setText(books_data.getDescription());
            description_details.setMovementMethod(new ScrollingMovementMethod());
            Glide.with(DetailBooks.this).load(books_data.getImgUrl()).into(cover_detail);
        }
    }

}