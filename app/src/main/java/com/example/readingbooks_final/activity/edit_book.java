package com.example.readingbooks_final.activity;

import static com.example.readingbooks_final.activity.Write_tab2.AUTHOR;
import static com.example.readingbooks_final.activity.Write_tab2.CATEGORY;
import static com.example.readingbooks_final.activity.Write_tab2.TITLE;

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
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class edit_book extends AppCompatActivity {

    private EditText title_book, author_book, description_book;

    private TextView category_book, status_book;
    private String[] category = new String[] {"Science Fiction", "Romantic", "Non-Fiction", "Horror", "Detective", "Thriller", "History", "Story", "Action"};
    private String[] status = new String[] {"Completed", "Incomplete"};
    private RoundedImageView cover_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book);
        ActionBar actionBar=getSupportActionBar();
        // assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        recieveData();
        chooseCategory();
        chooseStatus();
        clickAddCover();
    }

    private void initView(){
        title_book=findViewById(R.id.edit_title_book);
        author_book=findViewById(R.id.edit_author_book);
        category_book=findViewById(R.id.edit_category_book);
        status_book=findViewById(R.id.edit_status_book);
        description_book=findViewById(R.id.edit_description_book);
        cover_edit=findViewById(R.id.cover_edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        if(item.getItemId()== R.id.update_book){
            saveEdit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void recieveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            title_book.setText(books_data.getTitle());
            author_book.setText(books_data.getAuthors());
            category_book.setText(books_data.getCategory());
            status_book.setText(books_data.isStatus());
            description_book.setText(books_data.getDescription());
            Glide.with(edit_book.this).load(books_data.getImgUrl()).into(cover_edit);


        }
    }

    private void chooseCategory(){
        category_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_book.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_book.this);
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

    private void saveEdit(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            Books_data books_edit= (Books_data) bundle.get("objectBooks");
            String id_books= books_edit.getId();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=database.getReference().child("Books").child(id_books);
            String content_title= title_book.getText().toString().trim();
            String content_author= author_book.getText().toString().trim();
            String content_category= category_book.getText().toString().trim();
            String content_status= status_book.getText().toString().trim();
            String content_description= description_book.getText().toString().trim();
           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   Books_data books_data= snapshot.getValue(Books_data.class);
                   if (books_data != null){
                       String title_update = String.valueOf(databaseReference.child("title").setValue(content_title));
                       String authors_update = String.valueOf(databaseReference.child("authors").setValue(content_author));
                       String category_update = String.valueOf(databaseReference.child("category").setValue(content_category));
                       String description_update = String.valueOf(databaseReference.child("description").setValue(content_description));
                       String status_update = String.valueOf(databaseReference.child("status").setValue(content_status));

                       Intent intent_reply=new Intent();
                       Bundle bundle2 = new Bundle();
                       bundle2.putSerializable("objectBooks", books_data);
                       intent_reply.putExtras(bundle2);
                       setResult(Activity.RESULT_OK, intent_reply);
                       finish();

                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
        }










    }


    private void clickAddCover(){

        cover_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content_title = title_book.getText().toString().trim();
                String content_author = author_book.getText().toString().trim();
                String content_category = category_book.getText().toString().trim();
                String content_status = status_book.getText().toString().trim();
                String content_description = description_book.getText().toString().trim();
                if (content_title.isEmpty() && content_author.isEmpty() && content_category.isEmpty() && content_status.isEmpty() && content_description.isEmpty()){
                    Toast.makeText(edit_book.this, "Please do not leave blank ", Toast.LENGTH_SHORT).show();

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
                FirebaseAuth auth = FirebaseAuth.getInstance();

                Bundle bundle = getIntent().getExtras();
                if (bundle!= null){
                    Books_data books_edit= (Books_data) bundle.get("objectBooks");
                    String id_books= books_edit.getId();
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference=database.getReference("Books").child(id_books);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            databaseReference.child("imgUrl").setValue(cover);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }





//                String id_books = database.getReference().child("Books").getKey();
//                DatabaseReference reference=databaseReference.child(id_books);
//
//                System.out.println(reference);
//                System.out.println(id_books);
//                String content_title = title_book.getText().toString().trim();
//                String content_author = author_book.getText().toString().trim();
//                String content_category = category_book.getText().toString().trim();
//                String content_status = status_book.getText().toString().trim();
//                String content_description = description_book.getText().toString().trim();

//                String  id_user=auth.getCurrentUser().getUid();
//
//                String title= String.valueOf(reference.child("title").setValue(content_title));
//                String authors_up= String.valueOf(reference.child("authors").setValue(content_author));
//                String category_up= String.valueOf(reference.child("category").setValue(content_category));
//                String status_up= String.valueOf(reference.child("status").setValue(content_status));
//                String description_up= String.valueOf(reference.child("description").setValue(content_description));




            });
        }).addOnFailureListener(e -> {
            Toast.makeText(edit_book.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Glide.with(edit_book.this).load(photo).into(cover_edit);





                    }
                }
            });

}