package com.example.readingbooks_final.fragment;

import android.annotation.SuppressLint;
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
import com.example.readingbooks_final.adapter.Library_Adapter;
import com.example.readingbooks_final.call_interface.OnClickLibraryBookListener;
import com.example.readingbooks_final.database.Books_data;
import com.example.readingbooks_final.database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Books extends Fragment {

    private RecyclerView book_list;

    private List<Books_data> books= new ArrayList<>();
    private  Library_Adapter booksAdapter= new Library_Adapter(books, new OnClickLibraryBookListener() {
        @Override
        public void onClickLibraryBook(Books_data books_data) {
            openShowInfor(books_data);
        }
    });



    public Books() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_books,container,false);
        book_list= view.findViewById(R.id.book_list);
        //setAdapter();
        setAnimation(R.anim.layout_slide);
        addBooks();
        return view;
    }
    private void setAdapter(){

        book_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        book_list.setHasFixedSize(true);
        book_list.setAdapter(booksAdapter);
    }

    private void addBooks(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");
        Query query_bookLike = database.getReference("Users").orderByChild("Fav_Books");

        query_bookLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    User book_user = snapshot1.getValue(User.class);
                   String book_like_user = book_user.getId_book();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snapshot2 : snapshot.getChildren()){
                                Books_data books_data = snapshot2.getValue(Books_data.class);
                               String id_book_table = books_data.getId();
                               if (id_book_table.equals(book_like_user)){
                                   books.add(books_data);
                               }
                            }
                            booksAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot booksnap: snapshot.getChildren()){
//                    Books_data books_data =booksnap.getValue(Books_data.class);
//                    String id_books= books_data.getId();
//                    String id_user_inTable= books_data.getId_user();
//                    String  id_user=auth.getCurrentUser().getUid();
//                   // DatabaseReference refUser = database.getReference("Users").child(id_user).child("Fav_Books");
//
//
//
//
//
//                    if (id_user_inTable.equals(id_user)){
//
//                        books.add(books_data);
//                    }
//                }
//                booksAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

            }

    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        book_list.setLayoutAnimation(layoutAnimationController);
        setAdapter();
    }

    private void openShowInfor(Books_data books_data){

    }
}