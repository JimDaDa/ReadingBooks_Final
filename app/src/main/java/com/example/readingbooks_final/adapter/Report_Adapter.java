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
import com.example.readingbooks_final.call_interface.OnClickLibraryBookListener;
import com.example.readingbooks_final.call_interface.OnClickReportBookListener;
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;

public class Report_Adapter extends RecyclerView.Adapter<Report_Adapter.ViewHolder>{
    private List<Books_data> books= new ArrayList<>();
    private OnClickReportBookListener onClickReportBookListener;

//    public Library_Adapter(List<Books_data> books) {
//        this.books = books;
//    }


    public Report_Adapter(List<Books_data> books, OnClickReportBookListener onClickReportBookListener) {
        this.books = books;
        this.onClickReportBookListener = onClickReportBookListener;
    }

    @NonNull
    @Override
    public Report_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Report_Adapter.ViewHolder holder, int position) {
        Books_data books_data = books.get(position);
        if (books_data!= null){
            holder.title.setText(books_data.getTitle());
            holder.author.setText(books_data.getAuthors());
            holder.admin_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickReportBookListener.OnClickReportBookListener(books_data);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return books.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout admin_frame;
        ImageView imgBook, imgFav;
        TextView title, author, chap, rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.my_list_item);
            title = itemView.findViewById(R.id.mylist_item_title);
            author = itemView.findViewById(R.id.mylist_item_author);
            chap = itemView.findViewById(R.id.mylist_item_chappter);
            rate = itemView.findViewById(R.id.mylist_item_score);
            admin_frame = itemView.findViewById(R.id.admin_frame);
        }
    }
}
