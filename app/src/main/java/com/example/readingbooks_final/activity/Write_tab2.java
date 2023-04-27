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
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.custom.CustomDialogProgress;
import com.example.readingbooks_final.database.Books_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.UUID;

public class Write_tab2 extends AppCompatActivity {

    private EditText title_book, author_book, description_book;

    private TextView category_book, status_book;
    private RoundedImageView roundImage;
    private String[] category = new String[] {"Science Fiction", "Romantic", "Non-Fiction", "Horror", "Detective", "Thriller", "History", "Story", "Action"};
    private String[] status = new String[] {"Completed", "Incomplete"};

    public static final String TITLE = "TITLE";
    public static final String AUTHOR = "AUTHOR";
    public static final String CATEGORY = "CATEGORY";
    public static final String STATUS = "STATUS";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String COVER = "COVER";
   private boolean imageLoaded = false;

    private CustomDialogProgress progressDialog;
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
        clickAddCover();
    }
    private void initView(){
        title_book= findViewById(R.id.edit_title_book);
        author_book=findViewById(R.id.tv_author_book);
        category_book=findViewById(R.id.tv_category_book);
        status_book=findViewById(R.id.tv_status_book);
        description_book=findViewById(R.id.tv_description_book);
        roundImage=findViewById(R.id.cover_details);
        progressDialog= new CustomDialogProgress(Write_tab2.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String content_title= title_book.getText().toString().trim();
        String content_author= author_book.getText().toString().trim();
        String content_category= category_book.getText().toString().trim();
        String content_status= status_book.getText().toString().trim();
        String content_description= description_book.getText().toString().trim();


        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }

        if (item.getItemId()== R.id.add_books){
            View v = findViewById(R.id.add_books);
            v.startAnimation(AnimationUtils.loadAnimation(Write_tab2.this, R.anim.btn_click_anim));
          //  putDatatoTab3();

            // Add books lên database
//            addBooks();

            // put sang màn hình 3
            if (!imageLoaded || content_title.isEmpty() || content_author.isEmpty() || content_category.isEmpty() || content_status.isEmpty() || content_description.isEmpty() ){
                Toast.makeText(Write_tab2.this, "Please Fill All Information ", Toast.LENGTH_SHORT).show();

            }
            else {
                putDatatoTab3();
            }





        }
        return super.onOptionsItemSelected(item);
    }



    private void chooseCategory(){
        category_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Write_tab2.this, R.anim.btn_click_anim));
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
                v.startAnimation(AnimationUtils.loadAnimation(Write_tab2.this, R.anim.btn_click_anim));
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
            Intent intent = new Intent();

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

                setResult(RESULT_OK,intent);
                finish();
                Toast.makeText(Write_tab2.this, "Add Books Successfully", Toast.LENGTH_SHORT).show();


        }


    private void clickAddCover(){

        roundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Write_tab2.this, R.anim.btn_click_anim));
                String content_title = title_book.getText().toString().trim();
                String content_author = author_book.getText().toString().trim();
                String content_category = category_book.getText().toString().trim();
                String content_status = status_book.getText().toString().trim();
                String content_description = description_book.getText().toString().trim();
                if (content_title.isEmpty() && content_author.isEmpty() && content_category.isEmpty() && content_status.isEmpty() && content_description.isEmpty()){
                    Toast.makeText(Write_tab2.this, "Please do not leave blank ", Toast.LENGTH_SHORT).show();

                }
                else {
                    //roundImage.setEnabled(true);
                    //Chọn ảnh
                    chooseImage();
               }

            }
        });
    }

    private void chooseImage(){

        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult.launch(intent);
        imageLoaded= true;
    }




    public void uploadImage(Uri photo) {
        //Tạo tên file hình ngẫu nhiên

        String filename = UUID.randomUUID().toString();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("cover").child(filename);

        //Upload ảnh lên firebase storage
        progressDialog.show();
        storageReference.putFile(photo).addOnSuccessListener(taskSnapshot -> {
            //get url
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String cover = uri.toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference("Books");
                String content_title = title_book.getText().toString().trim();
                String content_author = author_book.getText().toString().trim();
                String content_category = category_book.getText().toString().trim();
                String content_status = status_book.getText().toString().trim();
                String content_description = description_book.getText().toString().trim();
                String publishStatus = "private";
                long view = 0;

                //Lấy id là UID trên firebase
                String id_books= database.getReference().push().getKey();
                String  id_user=auth.getCurrentUser().getUid();
               Books_data books = new Books_data(id_books,id_user,cover,content_title,content_author,content_category,content_status,content_description,publishStatus, view);
              //  Books_data books = new Books_data(id_books,id_user,cover);

                databaseReference.child(id_books).setValue(books);
                progressDialog.dismiss();
                Toast.makeText(Write_tab2.this, "Update Successfull", Toast.LENGTH_SHORT).show();


                // FirebaseAuth auth = FirebaseAuth.getInstance();
//                FirebaseDatabase database=FirebaseDatabase.getInstance();
//                DatabaseReference bookRef = database.getReference().child("Books");
//
//                bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot books: snapshot.getChildren()){
//                            String id_books =books.getKey();
//                            DatabaseReference databaseReference = database.getReference("Books").child(id_books);
//                            databaseReference.child("imgUrl").setValue(cover);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });





            });
        }).addOnFailureListener(e -> {
            Toast.makeText(Write_tab2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

                        Uri photo = intent.getData();
                        uploadImage(photo);
                        Glide.with(Write_tab2.this).load(photo).into(roundImage);
                        imageLoaded= true;




                    }
                }
            });

}