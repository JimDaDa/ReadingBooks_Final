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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.DetailBooks;
import com.example.readingbooks_final.adapter.Search_Adapter;
import com.example.readingbooks_final.call_interface.OnClickAllBookListener;
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
import java.util.List;


public class Search extends Fragment {


    private SearchView search_view;

    private RecyclerView allRecycler;

    private List<Books_data> all_books= new ArrayList<>();

   // private List<Books_data> dataListOld = new ArrayList<>();



    private Search_Adapter all_book_adapter = new Search_Adapter(all_books, new OnClickAllBookListener() {
        @Override
        public void OnClickAllBookListener(Books_data books_data) {
            openInforBook(books_data);
        }

    });

    public Search() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        search_view= view.findViewById(R.id.search_view);
        allRecycler=view.findViewById(R.id.allRecyclersearch);

        setAnimation(R.anim.layout_slide);
        Search_Books();
        All_Books();
        return view;


    }


    private void Search_Books(){

        search_view.setQueryHint("Type here to search");
        search_view.setMaxWidth(Integer.MAX_VALUE);



        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                all_book_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                all_book_adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

//    private void  processSearch(String search){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref= database.getReference("Books");
//        Query query = ref.orderByChild("publishStatus").equalTo("public");
//        Query searchQ = query.startAt(search).endAt(search+"\uf8ff");
//
//
//        searchQ.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                FirebaseRecyclerOptions<Books_data> options = new FirebaseRecyclerOptions.Builder<Books_data>()
//                        .setQuery(searchQ, Books_data.class)
//                        .build();
//                Log.d("", "search "+options);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
    private void setAdapterBooks(){
        allRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        allRecycler.setHasFixedSize(true);
        allRecycler.setAdapter(all_book_adapter);

    }
    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(getActivity(), animation);
        allRecycler.setLayoutAnimation(layoutAnimationController);


        setAdapterBooks();
    }
    private void openInforBook(Books_data books_data){
        Intent intent=new Intent(getActivity(), DetailBooks.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectBooks", books_data);
        intent.putExtras(bundle);

        startActivityForResult.launch(intent);

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


                        all_book_adapter.notifyDataSetChanged();

                    }

                }

            });
}