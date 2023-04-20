package com.example.readingbooks_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;

public class Read_Books extends AppCompatActivity {

    private TextView title, author, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_books);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        recieveData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        title=findViewById(R.id.title_read);
        author=findViewById(R.id.author_read);
        description=findViewById(R.id.description_read);
    }
    private void recieveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            title.setText(books_data.getTitle());
            author.setText(books_data.getAuthors());
            description.setText(books_data.getDescription());

        }
    }
}