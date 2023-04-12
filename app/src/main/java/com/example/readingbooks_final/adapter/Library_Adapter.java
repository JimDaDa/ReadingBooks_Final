package com.example.readingbooks_final.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;

public class Library_Adapter extends RecyclerView.Adapter<Library_Adapter.ViewHolder>{
    private List<Books_data> books= new ArrayList<>();

    public Library_Adapter(List<Books_data> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public Library_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Library_Adapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(books.get(position).getDrawableRes()).transform(new CenterCrop(),new RoundedCorners(16)).into(holder.imgBook);
    }

    @Override
    public int getItemCount() {
        return books.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgBook, imgFav;
        TextView title, author, chap, rate;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.library_item);
            title = itemView.findViewById(R.id.library_item_title);
            author = itemView.findViewById(R.id.library_item_author);
            chap = itemView.findViewById(R.id.library_item_chappter);
            rate = itemView.findViewById(R.id.library_item_score);
        }
    }
}
