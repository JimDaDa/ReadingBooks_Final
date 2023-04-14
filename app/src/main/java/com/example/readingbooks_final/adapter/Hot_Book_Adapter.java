package com.example.readingbooks_final.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.database.Books_data;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import com.example.readingbooks_final.R;

public class Hot_Book_Adapter extends RecyclerView.Adapter<Hot_Book_Adapter.ViewHolder>  {
    private List<Books_data> dataList= new ArrayList<>();

    public Hot_Book_Adapter(List<Books_data> dataList) {
        this.dataList = dataList;
    }



    @NonNull
    @Override
    public Hot_Book_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent,false);
        return new Hot_Book_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Hot_Book_Adapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(dataList.get(position)
                        .getDrawableRes()).transform(new CenterCrop(),new RoundedCorners(16))
                .into(holder.book_item1View);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView book_item1View;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_item1View = itemView.findViewById(R.id.book_item1);
        }
    }
}

