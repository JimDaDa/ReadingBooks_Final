package com.example.readingbooks_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.ListBook;
import com.example.readingbooks_final.activity.show_info_book;
import com.example.readingbooks_final.call_interface.OnClickItemBookListener;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;

public class List_Book_Adapter extends RecyclerView.Adapter<List_Book_Adapter.ViewHolder>{
    private List<Books_data> books= new ArrayList<>();

    private OnClickItemBookListener onClickItemBookListener;

    //private Context context;


//    public List_Book_Adapter(Context context, List<Books_data> books) {
//        this.context = context;
//        this.books = books;
//
//    }

//    public List_Book_Adapter(List<Books_data> books) {
//        this.books = books;
//    }


    public List_Book_Adapter(List<Books_data> books, OnClickItemBookListener onClickItemBookListener) {
        this.books = books;
        this.onClickItemBookListener = onClickItemBookListener;
    }

    @NonNull
    @Override
    public List_Book_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List_Book_Adapter.ViewHolder holder, int position) {
        Books_data books_data = books.get(position);
        if (books_data!= null){
            holder.title.setText(books_data.getTitle());
            holder.author.setText(books_data.getAuthors());
            Glide.with(holder.imgBook.getContext()).load(books_data.getImgUrl()).transform(new CenterCrop(),new RoundedCorners(16)).into(holder.imgBook);
            holder.frame_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemBookListener.onClickItemBook(books_data);
                }
            });
        }
       // Glide.with(holder.itemView.getContext()).load(books.get(position).getDrawableRes()).transform(new CenterCrop(),new RoundedCorners(16)).into(holder.imgBook);
    }

    @Override
    public int getItemCount() {
        return books.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgBook, imgFav;
        TextView title, author, chap, rate;
        RatingBar ratingBar;
        ConstraintLayout frame_book;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.my_list_item);
            title = itemView.findViewById(R.id.mylist_item_title);
            author = itemView.findViewById(R.id.mylist_item_author);
            chap = itemView.findViewById(R.id.mylist_item_chappter);
            rate = itemView.findViewById(R.id.mylist_item_score);
            frame_book= itemView.findViewById(R.id.frame_book);

        }
    }


}
