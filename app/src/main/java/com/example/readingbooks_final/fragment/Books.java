package com.example.readingbooks_final.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.readingbooks_final.activity.DetailBooks;
import com.example.readingbooks_final.activity.show_info_book;
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
import java.util.Objects;


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
//        Intent intent = requireActivity().getIntent();
//        Bundle bundle = intent.getExtras();
////        Bundle bundle = getArguments();
//        Books_data books_data= (Books_data) bundle.get("objectBooks");
//        String books_id = books_data.getId();

     //   DatabaseReference databaseReference=database.getReference("Books");
        DatabaseReference userRef = database.getReference("Users").child(auth.getCurrentUser().getUid()).child("Fav_Books");

        // lấy id books từ bảng Books
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    String id_like = user.getId_like();
                    String id_book_user = user.getId_book();
                    DatabaseReference bookRef = database.getReference("Books");
                    bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot bookSnap: snapshot.getChildren()){
                                Books_data books_data1 = bookSnap.getValue(Books_data.class);
                                String id_books = books_data1.getId();
                                if (id_books.equals(id_book_user)){
                                    books.add(books_data1);
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




            }


    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        book_list.setLayoutAnimation(layoutAnimationController);
        setAdapter();
    }

    private void openShowInfor(Books_data books_data){
        Intent intent=new Intent(getActivity(), DetailBooks.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectBooks", books_data);
        intent.putExtras(bundle);
        // startActivity(intent);
        startActivityForResult.launch(intent);
    }

    final ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//
                    if(result.getResultCode() == Activity.RESULT_OK) {

                        Intent intent = result.getData();
                        if (intent==null){
                            return;
                        }

                        for(int i=0 ; i <books.size(); i++){
                            if (books.get(i).getId().equals(intent.getStringExtra("removelike")) ){
                                books.remove(i);
                                break;
                            }
                        }
                        booksAdapter.notifyDataSetChanged();

                    }








                }

            });


}