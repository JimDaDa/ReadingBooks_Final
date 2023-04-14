package com.example.readingbooks_final.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.adapter.Library_Adapter;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;


public class Books extends Fragment {

    private RecyclerView book_list;

    private List<Books_data> books= new ArrayList<>();



    public Books() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_books,container,false);
        book_list= view.findViewById(R.id.book_list);
        //setAdapter();
        setAnimation(R.anim.layout_slide);
        addBooks();
        return view;
    }
    private void setAdapter(){
        Library_Adapter booksAdapter= new Library_Adapter(books);
        book_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        book_list.setHasFixedSize(true);
        book_list.setAdapter(booksAdapter);
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
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        book_list.setLayoutAnimation(layoutAnimationController);
        setAdapter();
    }
}