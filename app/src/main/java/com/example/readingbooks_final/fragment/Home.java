package com.example.readingbooks_final.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.adapter.Hot_Book_Adapter;
import com.example.readingbooks_final.adapter.Trending_Book_Adapter;
import com.example.readingbooks_final.call_interface.OnClickHomeBookListener;
import com.example.readingbooks_final.call_interface.OnClickHotBookListener;
import com.example.readingbooks_final.database.Books_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    //Khai báo các biến
    private Bundle savedInstanceState;
    private RecyclerView bookRecyclerView, hotRecyclerView;




    private List<Books_data> books_headList= new ArrayList<>();
    private  List<Books_data> hot_headList= new ArrayList<>();

    private Hot_Book_Adapter hot_book_adapter= new Hot_Book_Adapter(hot_headList, new OnClickHotBookListener() {
        @Override
        public void onClickHotBook(Books_data books_data) {

        }
    });
    private Trending_Book_Adapter trending_book_adapter= new Trending_Book_Adapter(books_headList, new OnClickHomeBookListener() {
       @Override
       public void onClickTrendingBook(Books_data books_data) {

       }
   });




    private View view;

    public Home() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bookRecyclerView= view.findViewById(R.id.bookRecycler);
        hotRecyclerView= view.findViewById(R.id.hotRecycler);
        //Gọi hàm
        //AnhXa();
        setAnimation(R.anim.layout_slide);
        Home_Books();
        Hot_Books();
        return view;
    }
    //Hàm ánh xạ view



    private void  Home_Books(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");

    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot booksnapshot: snapshot.getChildren()){
                Books_data books_data =booksnapshot.getValue(Books_data.class);
                String id_books = books_data.getId();
                String id_user_inTable= books_data.getId_user();
                String  id_user=auth.getCurrentUser().getUid();

                DatabaseReference databaseReference = database.getReference("Books").child(id_books);
                System.out.println(books_data.getCategory().contains("Detective"));
                if (books_data.getCategory().contains("Detective")){

                    books_headList.add(books_data);
                }
            }
           trending_book_adapter.notifyDataSetChanged();
            }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    }

    private void  Hot_Books(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot booksnapshot: snapshot.getChildren()){
                    Books_data books_data =booksnapshot.getValue(Books_data.class);
                    String id_books = books_data.getId();
                    String id_user_inTable= books_data.getId_user();
                    String  id_user=auth.getCurrentUser().getUid();

                    DatabaseReference databaseReference = database.getReference("Books").child(id_books);
                    System.out.println(books_data.getCategory().contains("Detective"));
                    if (books_data.getCategory().contains("Detective") || books_data.getCategory().contains("Science Fiction")){

                        hot_headList.add(books_data);
                    }
                }
                hot_book_adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setAdapterBookHead() {

        bookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        bookRecyclerView.setHasFixedSize(true);
        bookRecyclerView.setAdapter(trending_book_adapter);
    }

    private void setAdapterHotHead() {

        hotRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        hotRecyclerView.setHasFixedSize(true);
        hotRecyclerView.setAdapter(hot_book_adapter);
    }

    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        bookRecyclerView.setLayoutAnimation(layoutAnimationController);
        hotRecyclerView.setLayoutAnimation(layoutAnimationController);
        setAdapterBookHead();
        setAdapterHotHead();
    }
    private void openInforBook(Books_data books_data){

    }
}