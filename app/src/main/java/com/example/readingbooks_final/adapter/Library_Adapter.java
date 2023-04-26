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
import com.example.readingbooks_final.database.Books_data;

import java.util.ArrayList;
import java.util.List;

public class Library_Adapter extends RecyclerView.Adapter<Library_Adapter.ViewHolder>{
    private List<Books_data> books= new ArrayList<>();
    private OnClickLibraryBookListener onClickLibraryBook;

//    public Library_Adapter(List<Books_data> books) {
//        this.books = books;
//    }

    public Library_Adapter(List<Books_data> books, OnClickLibraryBookListener onClickLibraryBook) {
        this.books = books;
        this.onClickLibraryBook = onClickLibraryBook;
    }

    @NonNull
    @Override
    public Library_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Library_Adapter.ViewHolder holder, int position) {
        Books_data books_data = books.get(position);
        if (books_data!= null){
            holder.title.setText(books_data.getTitle());
            holder.author.setText(books_data.getAuthors());
            float getVote = books_data.getTotal_rating();
            String getVoteString = String.valueOf(getVote);
            if (getVoteString == null){
                holder.rate.setText("0.0/5.0");
            }else {
                holder.rate.setText(getVoteString+ " /5.0");
            }

            holder.status.setText(books_data.getStatus());
            Glide.with(holder.imgBook.getContext()).load(books_data.getImgUrl()).transform(new CenterCrop(),new RoundedCorners(16)).into(holder.imgBook);
            holder.library_frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLibraryBook.onClickLibraryBook(books_data);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return books.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout library_frame;
        ImageView imgBook;
        TextView title, author, status, rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBook = itemView.findViewById(R.id.my_list_item);
            title = itemView.findViewById(R.id.mylist_item_title);
            author = itemView.findViewById(R.id.mylist_item_author);
            status = itemView.findViewById(R.id.mylist_item_chappter);
            rate = itemView.findViewById(R.id.mylist_item_score);
            library_frame=itemView.findViewById(R.id.library_frame);
        }
    }
}
