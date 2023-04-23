package com.example.readingbooks_final.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.call_interface.OnClickHotBookListener;
import com.example.readingbooks_final.call_interface.OnClickLoveBookListener;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;

public class Love_Book_Adapter extends RecyclerView.Adapter<Love_Book_Adapter.ViewHolder>{
    private List<Books_data> dataList= new ArrayList<>();
    private OnClickLoveBookListener onClickLoveBookListener;

    public Love_Book_Adapter(List<Books_data> dataList, OnClickLoveBookListener onClickLoveBookListener) {
        this.dataList = dataList;
        this.onClickLoveBookListener = onClickLoveBookListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.love_item, parent,false);
        return new Love_Book_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Books_data books_data = dataList.get(position);
        if (books_data != null) {
            holder.book_title.setText(books_data.getTitle());

            Glide.with(holder.book_hot.getContext()).load(books_data.getImgUrl()).transform(new CenterCrop(), new RoundedCorners(16)).into(holder.book_hot);

            holder.hotFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLoveBookListener.OnClickLoveBookListener(books_data);
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
            book_hot = itemView.findViewById(R.id.book_item4);
            hotFrame= itemView.findViewById(R.id.loveFrame);
            book_title=itemView.findViewById(R.id.book_title);

        }
    }
}
