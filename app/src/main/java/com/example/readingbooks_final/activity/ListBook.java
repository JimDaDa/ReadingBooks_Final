package com.example.readingbooks_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.adapter.Library_Adapter;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;

public class ListBook extends AppCompatActivity {
    private RecyclerView story_list;

    private List<Books_data> books= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list);
        ActionBar actionBar=getSupportActionBar();
        story_list = findViewById(R.id.story_list);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // actionBar.setDisplayShowHomeEnabled(true);
        //setAdapter();
        setAnimation(R.anim.layout_slide);
        addBooks();
    }
    private void setAdapter(){
        Library_Adapter booksAdapter= new Library_Adapter(books);
        story_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        story_list.setHasFixedSize(true);
        story_list.setAdapter(booksAdapter);
    }

    private void addBooks(){
        books.add(new Books_data(R.drawable.book1));
        books.add(new Books_data(R.drawable.book2));
        books.add(new Books_data(R.drawable.book3));
        books.add(new Books_data(R.drawable.book4));
        books.add(new Books_data(R.drawable.book5));
        books.add(new Books_data(R.drawable.book6));
        books.add(new Books_data(R.drawable.book7));

    }

    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(this, animation);
        story_list.setLayoutAnimation(layoutAnimationController);
        setAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}