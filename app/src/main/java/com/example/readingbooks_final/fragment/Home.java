package com.example.readingbooks_final.fragment;

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
import com.example.readingbooks_final.adapter.Book_Home_Adapter;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    //Khai báo các biến
    private Bundle savedInstanceState;
    private RecyclerView bookRecyclerView;

    private final List<Books_data> books_headList= new ArrayList<>();

    private View view;

    public Home() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bookRecyclerView= view.findViewById(R.id.bookRecycler);
        //Gọi hàm
        //AnhXa();
        setAnimation(R.anim.layout_slide);
        Home_Books();
        return view;
    }
    //Hàm ánh xạ view



    private void  Home_Books(){
        books_headList.add(new Books_data(R.drawable.book1));
        books_headList.add(new Books_data(R.drawable.book2));
        books_headList.add(new Books_data(R.drawable.book3));
        books_headList.add(new Books_data(R.drawable.book4));
        books_headList.add(new Books_data(R.drawable.book5));
        books_headList.add(new Books_data(R.drawable.book6));
        books_headList.add(new Books_data(R.drawable.book7));

        //Gọi hàm setDiscountedRecycler
        // setAdapterBookHead();


    }

    private void setAdapterBookHead() {
        Book_Home_Adapter book_head_adapter= new Book_Home_Adapter(books_headList);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        bookRecyclerView.setHasFixedSize(true);
        bookRecyclerView.setAdapter(book_head_adapter);

    }


    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        bookRecyclerView.setLayoutAnimation(layoutAnimationController);
        setAdapterBookHead();
    }
}