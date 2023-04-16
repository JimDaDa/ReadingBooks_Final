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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.UUID;

public class Write_tab3 extends AppCompatActivity {

    private TextView title_book, author_book, category_book, status_book, description_book;
    private  Button addChapter;

    private RoundedImageView roundImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tab3);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

       actionBar.setDisplayShowHomeEnabled(true);
        initView();
        initView();
        RecieveData();
        addChapter();
        clickAddCover();
    }

    private void initView(){
        title_book=findViewById(R.id.tv_title_book);
        author_book=findViewById(R.id.tv_author_book);
        category_book=findViewById(R.id.tv_category_book);
        status_book=findViewById(R.id.tv_status_book);
        description_book=findViewById(R.id.tv_description_book);
        addChapter=findViewById(R.id.addChapter);
        roundImage=findViewById(R.id.cover3);

    }

    private void RecieveData(){
        Intent intent = getIntent();
        String title_reply = intent.getStringExtra(TITLE);
        String author_reply = intent.getStringExtra(AUTHOR);
        String category_reply = intent.getStringExtra(CATEGORY);
        String status_reply = intent.getStringExtra(STATUS);
        String description_reply = intent.getStringExtra(DESCRIPTION);
      //  String cover_reply = intent.getStringExtra(COVER);
        title_book.setText(title_reply);
        author_book.setText(author_reply);
        category_book.setText(category_reply);
        status_book.setText(status_reply);
        description_book.setText(description_reply);


        //Glide.with(Write_tab3.this).load(cover_reply).into(roundImage);
    }

    private void clickAddCover(){
        roundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chọn ảnh
                chooseImage();
            }
        });
    }

    private void chooseImage(){
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult.launch(intent);
    }

    public void uploadImage(Uri photo) {
        //Tạo tên file hình ngẫu nhiên

        String filename = UUID.randomUUID().toString();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("cover").child(filename);

        //Upload ảnh lên firebase storage

        storageReference.putFile(photo).addOnSuccessListener(taskSnapshot -> {
            //get url
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String cover = uri.toString();
               // FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference bookRef = database.getReference().child("Books");

                bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot books: snapshot.getChildren()){
                            String id_books =books.getKey();
                            DatabaseReference databaseReference = database.getReference("Books").child(id_books);
                            databaseReference.child("imgUrl").setValue(cover);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            });
        }).addOnFailureListener(e -> {
            Toast.makeText(Write_tab3.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

                        Uri photo = intent.getData();
                        uploadImage(photo);
                        Glide.with(Write_tab3.this).load(photo).into(roundImage);




                    }
                }
            });

    private void addChapter(){
        addChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference bookRef = database.getReference().child("Books");

                bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Books_data books_data= snapshot.getValue(Books_data.class);
                        if (books_data!=null){
                            Intent intent = new Intent(Write_tab3.this, Write_tab4.class);

                            String content_title= title_book.getText().toString().trim();
                            String content_author= author_book.getText().toString().trim();
                            String content_category= category_book.getText().toString().trim();
                            String content_status= status_book.getText().toString().trim();
                            String content_description= description_book.getText().toString().trim();
                            String content_cover = books_data.getImgUrl();

                            intent.putExtra(TITLE, content_title);
                            intent.putExtra(AUTHOR, content_author);
                            intent.putExtra(CATEGORY, content_category);
                            intent.putExtra(STATUS, content_status);
                            intent.putExtra(DESCRIPTION, content_description);
                            intent.putExtra(COVER,content_cover);
                            startActivity(intent);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }
}