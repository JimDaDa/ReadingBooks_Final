package com.example.readingbooks_final.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.call_interface.OnClickHomeBookListener;
import com.example.readingbooks_final.database.Books_data;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.example.readingbooks_final.R;

public class Trending_Book_Adapter extends RecyclerView.Adapter<Trending_Book_Adapter.ViewHolder>  {
    private List<Books_data> dataList= new ArrayList<>();
    private OnClickHomeBookListener onClickHomeBookListener;

    public Trending_Book_Adapter(List<Books_data> dataList, OnClickHomeBookListener onClickHomeBookListener) {
        this.dataList = dataList;
        this.onClickHomeBookListener = onClickHomeBookListener;
    }

//    public Trending_Book_Adapter(List<Books_data> dataList) {
//        this.dataList = dataList;
//    }


    @NonNull
    @Override
    public Trending_Book_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent,false);
        return new Trending_Book_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Trending_Book_Adapter.ViewHolder holder, int position) {
        Books_data books_data = dataList.get(position);
        if (books_data != null) {
            holder.book_title.setText(books_data.getTitle());

            Glide.with(holder.book_item1View.getContext()).load(books_data.getImgUrl()).transform(new CenterCrop(), new RoundedCorners(16)).into(holder.book_item1View);

         holder.trendingFrame.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onClickHomeBookListener.onClickTrendingBook(books_data);
             }
         });

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout trendingFrame;
        ImageView book_item1View;
        TextView book_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_item1View = itemView.findViewById(R.id.book_item1);
            trendingFrame= itemView.findViewById(R.id.trendingFrame);
            book_title=itemView.findViewById(R.id.book_title);

        }
    }
}
