package com.example.readingbooks_final.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

public class show_info_book extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView title_book, author_book, description_book,category_book, status_book;
    private RoundedImageView cover_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info_book);
        ActionBar actionBar=getSupportActionBar();
        // assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        RecieveData();
    }

    private void initView(){
        title_book= findViewById(R.id.tv_title_book);
        author_book= findViewById(R.id.tv_author_book);
        category_book=findViewById(R.id.tv_category_book);
        status_book=findViewById(R.id.tv_status_book);
        description_book=findViewById(R.id.tv_description_book);
        cover_details=findViewById(R.id.cover_details);



    }
    private void RecieveData(){
    Bundle bundle = getIntent().getExtras();
    if (bundle!= null){
        Books_data books_data= (Books_data) bundle.get("objectBooks");
        title_book.setText(books_data.getTitle());
        author_book.setText(books_data.getAuthors());
        category_book.setText(books_data.getCategory());
        status_book.setText(books_data.isStatus());
        description_book.setText(books_data.getDescription());
        description_book.setMovementMethod(new ScrollingMovementMethod());
        Glide.with(show_info_book.this).load(books_data.getImgUrl()).into(cover_details);
     }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_info_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId()== R.id.add_chapter_show_info_book){
            openChater();
        }
        if (item.getItemId()== R.id.edit_chapter){
            openEdit();
        }
        if (item.getItemId()== R.id.deleteBook){
            deleteBooks();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openChater(){

    }
    private void openEdit(){
        Bundle bundle = getIntent().getExtras();
        Books_data books_data= (Books_data) bundle.get("objectBooks");

        Intent intent=new Intent(this, edit_book.class);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("objectBooks", books_data);
        intent.putExtras(bundle2);

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

                       // Uri photo = intent.getData();
                        Bundle bundle_reply = getIntent().getExtras();
                        if (bundle_reply!= null){
                            Books_data books_data= (Books_data) bundle_reply.get("objectBooks");
                            title_book.setText(books_data.getTitle());
                            author_book.setText(books_data.getAuthors());
                            category_book.setText(books_data.getCategory());
                            status_book.setText(books_data.isStatus());
                            description_book.setText(books_data.getDescription());
                            Glide.with(show_info_book.this).load(books_data.getImgUrl()).into(cover_details);

                        }

                       // Glide.with(show_info_book.this).load(photo).into(cover_details);


                    }
                }
            });

    private void deleteBooks(){
        AlertDialog.Builder builder = new AlertDialog.Builder(show_info_book.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to delete Books?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = getIntent().getExtras();
                Books_data books_data= (Books_data) bundle.get("objectBooks");
                String id_books= books_data.getId();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference().child("Books").child(id_books);
                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Intent intent = new Intent(show_info_book.this, ListBook.class);
                        Toast.makeText(show_info_book.this, "Delete Success", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK,intent);
                        finish();
                        //startActivity(intent);

                    }
                });

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });

        AlertDialog confirmDialog = builder.create();
        confirmDialog.show();



    }

}