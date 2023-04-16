package com.example.readingbooks_final.activity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static com.example.readingbooks_final.activity.Write_tab2.AUTHOR;
import static com.example.readingbooks_final.activity.Write_tab2.CATEGORY;
import static com.example.readingbooks_final.activity.Write_tab2.COVER;
import static com.example.readingbooks_final.activity.Write_tab2.DESCRIPTION;
import static com.example.readingbooks_final.activity.Write_tab2.STATUS;
import static com.example.readingbooks_final.activity.Write_tab2.TITLE;
import static com.example.readingbooks_final.activity.Write_tab4.CONTENT_CHAPTER;
import static com.example.readingbooks_final.activity.Write_tab4.TITLE_CHAPTER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.fragment.Write;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

public class Write_tab5 extends AppCompatActivity {

    private RoundedImageView cover5;
    private TextView title5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab5);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);
        initView();
        recieveData();
    }

    private void initView(){
        cover5=findViewById(R.id.cover5);
        title5=findViewById(R.id.title5);
    }

    private void recieveData(){
        Intent intent = getIntent();
        String title_reply = intent.getStringExtra(TITLE);
        String author_reply = intent.getStringExtra(AUTHOR);
        String category_reply = intent.getStringExtra(CATEGORY);
        String status_reply = intent.getStringExtra(STATUS);
        String description_reply = intent.getStringExtra(DESCRIPTION);
        String cover_reply = intent.getStringExtra(COVER);
        title5.setText(title_reply);
        Glide.with(Write_tab5.this).load(cover_reply).into(cover5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId()==R.id.back_write){
            backToWriteFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void backToWriteFragment(){

        Intent intent2 = getIntent();
        String title_reply = intent2.getStringExtra(TITLE);
        String author_reply = intent2.getStringExtra(AUTHOR);
        String category_reply = intent2.getStringExtra(CATEGORY);
        String status_reply = intent2.getStringExtra(STATUS);
        String description_reply = intent2.getStringExtra(DESCRIPTION);
        String cover_reply = intent2.getStringExtra(COVER);
        String title_content = intent2.getStringExtra(TITLE_CHAPTER);
        String chap_content = intent2.getStringExtra(CONTENT_CHAPTER);

        Intent intent= new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TASK);

//
        intent.putExtra(TITLE, title_reply);
        intent.putExtra(AUTHOR, author_reply);
        intent.putExtra(CATEGORY, category_reply);
        intent.putExtra(STATUS, status_reply);
        intent.putExtra(DESCRIPTION, description_reply);
        intent.putExtra(COVER,cover_reply);
        intent.putExtra(TITLE_CHAPTER,title_content);
        intent.putExtra(CONTENT_CHAPTER,chap_content);
        //startActivity(intent);
        setResult(RESULT_OK,intent);
        finish();

//        Write write = new Write();
//        FragmentManager fragmentManager=
//        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.content_tool,write);
//        transaction.addToBackStack("write");
//        transaction.commit();
    }
}