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


public class ListBook extends Fragment {

    private RecyclerView story_list;

    private List<Books_data> books= new ArrayList<>();



    public ListBook() {
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
        View view=inflater.inflate(R.layout.my_list,container,false);
        story_list = view.findViewById(R.id.story_list);
        //setAdapter();
        setAnimation(R.anim.layout_slide);
        addBooks();
        return view;
    }
    private void setAdapter(){
        Library_Adapter booksAdapter= new Library_Adapter(books);
        story_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
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
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        story_list.setLayoutAnimation(layoutAnimationController);
        setAdapter();
    }
}
