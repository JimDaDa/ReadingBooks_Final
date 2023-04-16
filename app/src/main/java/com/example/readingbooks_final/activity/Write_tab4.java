package com.example.readingbooks_final.activity;

import static com.example.readingbooks_final.activity.Write_tab2.AUTHOR;
import static com.example.readingbooks_final.activity.Write_tab2.CATEGORY;
import static com.example.readingbooks_final.activity.Write_tab2.COVER;
import static com.example.readingbooks_final.activity.Write_tab2.DESCRIPTION;
import static com.example.readingbooks_final.activity.Write_tab2.STATUS;
import static com.example.readingbooks_final.activity.Write_tab2.TITLE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Write_tab4 extends AppCompatActivity {
    private EditText title_chapter, content_chapter;
    public static final String TITLE_CHAPTER = "TITLE_CHAPTER";
    public static final String CONTENT_CHAPTER = "CONTENT_CHAPTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab4);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);
//        initView();
       // recieveData();
    }
    private void initView(){
        title_chapter=findViewById(R.id.title_chapter);
        content_chapter=findViewById(R.id.content_chapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId()==R.id.add_chapter){
            intentToTab5();
        }
        return super.onOptionsItemSelected(item);
    }

//    private void recieveData(){
//        Intent intent = getIntent();
//        String title_reply = intent.getStringExtra(TITLE);
//        String author_reply = intent.getStringExtra(AUTHOR);
//        String category_reply = intent.getStringExtra(CATEGORY);
//        String status_reply = intent.getStringExtra(STATUS);
//        String description_reply = intent.getStringExtra(DESCRIPTION);
//        String cover_reply = intent.getStringExtra(COVER);
//
//    }

    private void intentToTab5(){

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference bookRef = database.getReference().child("Books");
        Intent intent_recieve = getIntent();
        String title_reply = intent_recieve.getStringExtra(TITLE);
        String author_reply = intent_recieve.getStringExtra(AUTHOR);
        String category_reply = intent_recieve.getStringExtra(CATEGORY);
        String status_reply = intent_recieve.getStringExtra(STATUS);
        String description_reply = intent_recieve.getStringExtra(DESCRIPTION);
        String cover_reply = intent_recieve.getStringExtra(COVER);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Books_data books_data= snapshot.getValue(Books_data.class);
                if (books_data!=null){
                    Intent intent= new Intent(Write_tab4.this, Write_tab5.class);
                    String title_content = title_chapter.getText().toString().trim();
                    String chap_content = content_chapter.getText().toString().trim();

                    intent.putExtra(TITLE, title_reply);
                    intent.putExtra(AUTHOR, author_reply);
                    intent.putExtra(CATEGORY, category_reply);
                    intent.putExtra(STATUS, status_reply);
                    intent.putExtra(DESCRIPTION, description_reply);
                    intent.putExtra(COVER,cover_reply);
                    intent.putExtra(TITLE_CHAPTER,title_content);
                    intent.putExtra(CONTENT_CHAPTER,chap_content);
                    //startActivity(intent);
                    startActivityForResult.launch(intent);
                }


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
                        // String newData = intent.getStringExtra("data back");

//                        String title_reply = intent.getStringExtra(TITLE);
//                        String author_reply = intent.getStringExtra(AUTHOR);
//                        String category_reply = intent.getStringExtra(CATEGORY);
//                        String status_reply = intent.getStringExtra(STATUS);
//                        String description_reply = intent.getStringExtra(DESCRIPTION);
//                        String cover_reply = intent.getStringExtra(COVER);
//                        String title_content = intent.getStringExtra(TITLE_CHAPTER);
//                        String chap_content = intent.getStringExtra(CONTENT_CHAPTER);





                    }
                }
            });
}