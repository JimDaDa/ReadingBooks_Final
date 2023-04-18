package com.example.readingbooks_final.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.adapter.Library_Adapter;
import com.example.readingbooks_final.adapter.List_Book_Adapter;
import com.example.readingbooks_final.call_interface.OnClickItemBookListener;
import com.example.readingbooks_final.database.Books_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListBook extends AppCompatActivity {
    private RecyclerView story_list;

    private List<Books_data> books= new ArrayList<>();
    private  List_Book_Adapter booksAdapter= new List_Book_Adapter(books, new OnClickItemBookListener() {
        @Override
        public void onClickItemBook(Books_data books_data) {
            openShowInfor(books_data);
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list);
        ActionBar actionBar=getSupportActionBar();
        story_list = findViewById(R.id.story_list);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // actionBar.setDisplayShowHomeEnabled(true);
        //setAdapter();
        setAnimation(R.anim.layout_slide);
        showBooks();
    }
    private void setAdapter(){

        story_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        story_list.setHasFixedSize(true);
        story_list.setAdapter(booksAdapter);
    }

    private void showBooks(){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot booksnap: snapshot.getChildren()){
                    Books_data books_data =booksnap.getValue(Books_data.class);
                    String id_books= books_data.getId();
                    String id_user_inTable= books_data.getId_user();
                    String  id_user=auth.getCurrentUser().getUid();
                    DatabaseReference databaseReference = database.getReference("Books").child(id_books);


                    if (id_user_inTable.equals(id_user)){

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

    private void setAnimation(int animation){
        LayoutAnimationController layoutAnimationController= AnimationUtils.loadLayoutAnimation(this, animation);
        story_list.setLayoutAnimation(layoutAnimationController);
        setAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void openShowInfor(Books_data books_data){
        Intent intent=new Intent(this, show_info_book.class);
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

                       booksAdapter.notifyDataSetChanged();

                        }








                    }

            });

}