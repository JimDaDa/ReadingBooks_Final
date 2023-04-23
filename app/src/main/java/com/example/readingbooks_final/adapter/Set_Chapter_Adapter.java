package com.example.readingbooks_final.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.Read_Books;
import com.example.readingbooks_final.database.Set_Chapter;

import java.util.ArrayList;
import java.util.List;

public class Set_Chapter_Adapter  extends RecyclerView.Adapter<Set_Chapter_Adapter.ViewHolder>  {
    private List<Set_Chapter> dataList= new ArrayList<>();
    Context context;

    public Set_Chapter_Adapter(List<Set_Chapter> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }



    @NonNull
    @Override
    public Set_Chapter_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setchap, parent, false);
        return new Set_Chapter_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Set_Chapter_Adapter.ViewHolder holder, int position) {
        Set_Chapter chapter =dataList.get(position);
        holder.set_chap.setText(chapter.getSet_chap());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Read_Books.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView set_chap;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            set_chap = itemView.findViewById(R.id.set_chap);
        }
    }
}





