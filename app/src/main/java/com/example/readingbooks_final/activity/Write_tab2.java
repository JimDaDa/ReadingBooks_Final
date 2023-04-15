package com.example.readingbooks_final.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.UUID;

public class Write_tab2 extends AppCompatActivity {

    private EditText title_book, author_book, description_book;

    private TextView category_book, status_book;
    private RoundedImageView roundedImageView;
    private String[] category = new String[] {"Fiction", "Romantic", "Non-Fiction", "Horror", "Detective"};
    private String[] status = new String[] {"Completed", "Continues"};

    public static final String TITLE = "TITLE";
    public static final String AUTHOR = "AUTHOR";
    public static final String CATEGORY = "CATEGORY";
    public static final String STATUS = "STATUS";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String COVER = "COVER";
    private TextView add_cover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab2);
        ActionBar actionBar=getSupportActionBar();
       // assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

       // actionBar.setDisplayShowHomeEnabled(true);
        initView();
        chooseCategory();
        chooseStatus();
        //clickAddCover();
    }
    private void initView(){
        title_book= findViewById(R.id.tv_title_book);
        author_book=findViewById(R.id.tv_author_book);
        category_book=findViewById(R.id.tv_category_book);
        status_book=findViewById(R.id.tv_status_book);
        description_book=findViewById(R.id.tv_description_book);

        roundedImageView=findViewById(R.id.cover3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== R.id.add_books){
            // Add books lên database
            addBooks();
            // put sang màn hình 3
            putDatatoTab3();

        }
        return super.onOptionsItemSelected(item);
    }


    private void addBooks(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Books");

        //Lấy id là UID trên firebase
        String id_books= database.getReference().push().getKey();
        String  id_user=auth.getCurrentUser().getUid();

        String title = title_book.getText().toString().trim();
        String author = author_book.getText().toString().trim();
        String categoryy = category_book.getText().toString().trim();
        String statuss = status_book.getText().toString().trim();
        String description = description_book.getText().toString().trim();
        String photo1 ="";


        Books_data books = new Books_data(id_books,id_user, title, author, categoryy,statuss,description,photo1);
        databaseReference.child(id_books).setValue(books);
    }

    private void chooseCategory(){
        category_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Write_tab2.this);
                builder.setTitle("Select category");
                builder.setSingleChoiceItems(category, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        category_book.setText(category[i]);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void chooseStatus(){
        status_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Write_tab2.this);
                builder.setTitle("Select status");
                builder.setSingleChoiceItems(status, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        status_book.setText(status[i]);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }



        private void putDatatoTab3(){
            Intent intent = new Intent(Write_tab2.this, Write_tab3.class);

            String content_title= title_book.getText().toString().trim();
            String content_author= author_book.getText().toString().trim();
            String content_category= category_book.getText().toString().trim();
            String content_status= status_book.getText().toString().trim();
            String content_description= description_book.getText().toString().trim();
            intent.putExtra(TITLE, content_title);
            intent.putExtra(AUTHOR, content_author);
            intent.putExtra(CATEGORY, content_category);
            intent.putExtra(STATUS, content_status);
            intent.putExtra(DESCRIPTION, content_description);
            startActivity(intent);

        }


}