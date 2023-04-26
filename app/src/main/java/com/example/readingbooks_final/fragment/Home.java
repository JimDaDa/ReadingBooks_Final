package com.example.readingbooks_final.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.DetailBooks;
import com.example.readingbooks_final.activity.show_info_book;
import com.example.readingbooks_final.adapter.All_Book_Adapter;
import com.example.readingbooks_final.adapter.Hot_Book_Adapter;
import com.example.readingbooks_final.adapter.Love_Book_Adapter;
import com.example.readingbooks_final.adapter.Trending_Book_Adapter;
import com.example.readingbooks_final.call_interface.OnClickAllBookListener;
import com.example.readingbooks_final.call_interface.OnClickHomeBookListener;
import com.example.readingbooks_final.call_interface.OnClickHotBookListener;
import com.example.readingbooks_final.call_interface.OnClickLoveBookListener;
import com.example.readingbooks_final.database.Books_data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class Home extends Fragment {

    //Khai báo các biến
    private Bundle savedInstanceState;
    private RecyclerView bookRecyclerView, hotRecyclerView, loveRecycler, allRecycler;





    private List<Books_data> books_headList= new ArrayList<>();
    private  List<Books_data> hot_headList= new ArrayList<>();

    private List<Books_data> love_list= new ArrayList<>();
    private List<Books_data> all_books= new ArrayList<>();



    //Book có nhiều lượt xem
    private Trending_Book_Adapter trending_book_adapter= new Trending_Book_Adapter(books_headList, new OnClickHomeBookListener() {
       @Override
       public void onClickTrendingBook(Books_data books_data) {
           openInforBook(books_data);
       }
   });

    //Books vừa đăng

    private Hot_Book_Adapter hot_book_adapter= new Hot_Book_Adapter(hot_headList, new OnClickHotBookListener() {
        @Override
        public void onClickHotBook(Books_data books_data) {
            openInforBook(books_data);
        }
    });

    // Book có nhiều lượt vote

    private Love_Book_Adapter love_book_adapter = new Love_Book_Adapter(love_list, new OnClickLoveBookListener() {
        @Override
        public void OnClickLoveBookListener(Books_data books_data) {
            openInforBook(books_data);
        }
    });

    //Tất cả book được public
    private All_Book_Adapter all_book_adapter = new All_Book_Adapter(all_books, new OnClickAllBookListener() {
        @Override
        public void OnClickAllBookListener(Books_data books_data) {
            openInforBook(books_data);
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
        loveRecycler= view.findViewById(R.id.loveRecycler);
        allRecycler=view.findViewById(R.id.allRecycler);

        //Gọi hàm
        //AnhXa();
        setAnimation(R.anim.layout_slide);

        Home_Books();
        Hot_Books();
        Love_Books();
        All_Books();
        return view;
    }
    //Hàm ánh xạ view




    private void  Home_Books(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");
        PriorityQueue<Books_data> queue = new PriorityQueue<>(10, new Comparator<Books_data>() {
            @Override
            public int compare(Books_data o1, Books_data o2) {
                if (o1.getView() > o2.getView()){
                    return -1;
                } else if (o1.getView() < o2.getView()) {
                    return 1;
                }else {
                    return 0;
                }

            }
        });
        Set<String> bookIds= new HashSet<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot booksnapshot: snapshot.getChildren()){
                    Books_data books_data =booksnapshot.getValue(Books_data.class);
                    String id_books = books_data.getId();
                    long view= books_data.getView();


                    if (books_data.getPublishStatus().contains("public")){
//
                        if (!bookIds.contains(id_books)){
                            queue.offer(books_data);
                            bookIds.add(id_books);

                            if (queue.size() >10){
                                Books_data removeBook = queue.poll();
                                bookIds.remove(removeBook.getId());
                            }
                        }


                    }
                }
                books_headList.clear();
                while (!queue.isEmpty()){
                    books_headList.add(queue.poll());
                }
                trending_book_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
// Những Sách được đăng gần nhất
    private void  Hot_Books(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");
        Query hot= databaseReference.orderByChild("timestamp").limitToLast(10);
        hot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot booksnapshot: snapshot.getChildren()){
                  //  DataSnapshot lastbook= snapshot.getChildren().iterator().next();
                    Books_data books_data =booksnapshot.getValue(Books_data.class);
                    String publish = books_data.getPublishStatus();
                    if (publish.contains("public")){
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
    private void Love_Books(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot booksnapshot: snapshot.getChildren()){
                    Books_data books_data =booksnapshot.getValue(Books_data.class);
                    String id_books = books_data.getId();
                    long view= books_data.getView();
                    // String publicBook = books_data.getPublishStatus().contains("public");

                    DatabaseReference databaseReference = database.getReference("Books").child(id_books);


                    if(books_data.getTotal_rating() >=4.0){
                        love_list.add(books_data);
                    }
                }
                love_book_adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void All_Books(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot booksnapshot: snapshot.getChildren()){
                    Books_data books_data =booksnapshot.getValue(Books_data.class);
                    String id_books = books_data.getId();

                    if (books_data.getPublishStatus().contains("public")){

                        all_books.add(books_data);
                    }
                }
                all_book_adapter.notifyDataSetChanged();
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

    private void setAdapterLoveBook(){
        loveRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        loveRecycler.setHasFixedSize(true);
        loveRecycler.setAdapter(love_book_adapter);
    }

    private void setAdapterBooks(){
        allRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        allRecycler.setHasFixedSize(true);
        allRecycler.setAdapter(all_book_adapter);

    }
    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        bookRecyclerView.setLayoutAnimation(layoutAnimationController);
        hotRecyclerView.setLayoutAnimation(layoutAnimationController);
        loveRecycler.setLayoutAnimation(layoutAnimationController);
        allRecycler.setLayoutAnimation(layoutAnimationController);
        setAdapterBookHead();
        setAdapterHotHead();
        setAdapterLoveBook();
        setAdapterBooks();
    }
    private void openInforBook(Books_data books_data){
        Intent intent=new Intent(getActivity(), DetailBooks.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectBooks", books_data);
        intent.putExtras(bundle);

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

                        hot_book_adapter.notifyDataSetChanged();
                        love_book_adapter.notifyDataSetChanged();
                        trending_book_adapter.notifyDataSetChanged();
                        all_book_adapter.notifyDataSetChanged();

                    }

                }

            });
}