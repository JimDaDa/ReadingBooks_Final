package com.example.readingbooks_final.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.database.Chapter;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.example.readingbooks_final.R;

public class Chapter_Book_Adapter extends RecyclerView.Adapter<Chapter_Book_Adapter.ViewHolder>  {
    private List<Chapter> dataList= new ArrayList<>();

    public Chapter_Book_Adapter(List<Chapter> dataList) {
        this.dataList = dataList;
    }



    @NonNull
    @Override
    public Chapter_Book_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item, parent,false);
        return new Chapter_Book_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chapter_Book_Adapter.ViewHolder holder, int position) {
        Chapter chapter =dataList.get(position);
        holder.chapter.setText(chapter.getTitle_chap());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
       TextView chapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter=itemView.findViewById(R.id.chapter_title);
        }
    }
}

