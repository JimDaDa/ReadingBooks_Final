package com.example.readingbooks_final.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.call_interface.OnClickHotBookListener;
import com.example.readingbooks_final.database.Books_data;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.example.readingbooks_final.R;

public class Hot_Book_Adapter extends RecyclerView.Adapter<Hot_Book_Adapter.ViewHolder>  {
    private List<Books_data> dataList= new ArrayList<>();
    private OnClickHotBookListener onClickHotBookListener;

//    public Hot_Book_Adapter(List<Books_data> dataList) {
//        this.dataList = dataList;
//    }


    public Hot_Book_Adapter(List<Books_data> dataList, OnClickHotBookListener onClickHotBookListener) {
        this.dataList = dataList;
        this.onClickHotBookListener = onClickHotBookListener;
    }

    @NonNull
    @Override
    public Hot_Book_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // if (viewType == TYPE_F)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_item, parent,false);
        return new Hot_Book_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Hot_Book_Adapter.ViewHolder holder, int position) {
        Books_data books_data = dataList.get(position);
        if (books_data != null) {
            holder.book_title.setText(books_data.getTitle());

            Glide.with(holder.book_hot.getContext()).load(books_data.getImgUrl()).transform(new CenterCrop(), new RoundedCorners(16)).into(holder.book_hot);

            holder.hotFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onClickHotBookListener.onClickHotBook(books_data);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout hotFrame;
        ImageView book_hot;
        TextView book_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_hot = itemView.findViewById(R.id.book_hotitem);
            hotFrame= itemView.findViewById(R.id.hotFrame);
            book_title=itemView.findViewById(R.id.book_title_hot);

        }
    }

}

