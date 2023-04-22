package com.example.readingbooks_final.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.adapter.Set_Chapter_Adapter;
import com.example.readingbooks_final.database.Set_Chapter;

import java.util.ArrayList;
import java.util.List;

public class list_chapter extends AppCompatActivity {

    List<Set_Chapter> dataList= new ArrayList<>();
    Set_Chapter_Adapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_book);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerViewChap);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dataList.add(new Set_Chapter("Chương 1"));
        dataList.add(new Set_Chapter("Chương 2"));
        dataList.add(new Set_Chapter("Chương 3"));
        dataList.add(new Set_Chapter("Chương 4"));

        adapter = new Set_Chapter_Adapter(dataList, this);
        recyclerView.setAdapter(adapter);
    }
}