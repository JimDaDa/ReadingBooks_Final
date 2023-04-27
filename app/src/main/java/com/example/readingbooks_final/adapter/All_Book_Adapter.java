package com.example.readingbooks_final.adapter;

import android.animation.AnimatorInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.call_interface.OnClickAllBookListener;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;

public class All_Book_Adapter extends RecyclerView.Adapter<All_Book_Adapter.ViewHolder>{
    private List<Books_data> dataList= new ArrayList<>();
    private OnClickAllBookListener onClickAllBookListener;

    public All_Book_Adapter(List<Books_data> dataList, OnClickAllBookListener onClickAllBookListener) {
        this.dataList = dataList;
        this.onClickAllBookListener = onClickAllBookListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_item, parent,false);
        return new All_Book_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Books_data books_data = dataList.get(position);
        if (books_data != null) {
            holder.book_title.setText(books_data.getTitle());

            Glide.with(holder.book_all.getContext()).load(books_data.getImgUrl()).transform(new CenterCrop(), new RoundedCorners(16)).into(holder.book_all);

            holder.allFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.btn_click_anim));
                    onClickAllBookListener.OnClickAllBookListener(books_data);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout allFrame;
        ImageView book_all;
        TextView book_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_all = itemView.findViewById(R.id.book_item5);
            allFrame = itemView.findViewById(R.id.allFrame);
            book_title=itemView.findViewById(R.id.book_title);

        }
    }
}
