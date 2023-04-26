package com.example.readingbooks_final.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.ViewHolder> implements Filterable {
    private List<Books_data> dataList= new ArrayList<>();
    private final List<Books_data> dataListOld;


    private OnClickAllBookListener onClickAllBookListener;

    public Search_Adapter(List<Books_data> dataList, OnClickAllBookListener onClickAllBookListener) {
        this.dataList = dataList;
        this.dataListOld = dataList;
        this.onClickAllBookListener = onClickAllBookListener;
    }

//    public Search_Adapter(List<Books_data> dataList, OnClickAllBookListener onClickAllBookListener) {
//        this.dataList = dataList;
//        this.onClickAllBookListener = onClickAllBookListener;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item, parent,false);
        return new Search_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Books_data books_data = dataList.get(position);
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
            holder.frame_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickAllBookListener.OnClickAllBookListener(books_data);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchKey = constraint.toString();
                if (searchKey.isEmpty()){
                    dataList= dataListOld;
                }
                else {
                    List<Books_data> list = new ArrayList<>();
                    for (Books_data books_data1: dataListOld){
                        if (books_data1.getTitle().toLowerCase().contains(searchKey.toLowerCase()))
                            list.add(books_data1);
                    }
                    dataList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                    dataList= (List<Books_data>) results.values;
                    notifyDataSetChanged();


            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBook, imgFav;
        TextView title, author, status, rate;
        RatingBar ratingBar;
        ConstraintLayout frame_book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.my_list_item);
            title = itemView.findViewById(R.id.mylist_item_title);
            author = itemView.findViewById(R.id.mylist_item_author);
            status = itemView.findViewById(R.id.mylist_item_status);
            rate = itemView.findViewById(R.id.mylist_item_score);
            frame_book= itemView.findViewById(R.id.frame_book);

        }
    }
}
