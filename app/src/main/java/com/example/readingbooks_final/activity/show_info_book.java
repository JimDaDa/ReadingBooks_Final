package com.example.readingbooks_final.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.custom.CustomDialogProgress;
import com.example.readingbooks_final.database.Books_data;
import com.example.readingbooks_final.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Date;
import java.util.UUID;

public class show_info_book extends AppCompatActivity {

    private CustomDialogProgress progressDialog;
    private TextView title_book, author_book, description_book,category_book, status_book;
    private RoundedImageView cover_details;
    public static final String ATTACH_FILE = "ATTACH_FILE";
    private Uri fileUrl;
    private boolean isPublish;

    private MenuItem publish, unPublish;
    private AppCompatButton yes_delete, cancel_delete, yes_publish, cancel_publish, yes_unpublis, cancel_unpublish;

    Books_data books_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info_book);
        ActionBar actionBar=getSupportActionBar();
        // assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        RecieveData();
        addPublishChangeListener();
//        checkPublish();
    }

    @Override
    public void onBackPressed() {
        Intent intent_reply=new Intent();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("objectBooks", books_data);
        intent_reply.putExtras(bundle2);
        setResult(200, intent_reply);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView(){
        title_book= findViewById(R.id.tv_title_book);
        author_book= findViewById(R.id.tv_author_book);
        category_book=findViewById(R.id.tv_category_book);
        status_book=findViewById(R.id.tv_status_book);
        description_book=findViewById(R.id.tv_description_book);
        cover_details=findViewById(R.id.cover_details);
        progressDialog= new CustomDialogProgress(show_info_book.this);
    }
    private void RecieveData(){
        Bundle bundle = getIntent().getExtras();
         if (bundle!= null){
            books_data= (Books_data) bundle.get("objectBooks");
            title_book.setText(books_data.getTitle());
            author_book.setText(books_data.getAuthors());
            category_book.setText(books_data.getCategory());
            status_book.setText(books_data.getStatus());
            description_book.setText(books_data.getDescription());
            description_book.setMovementMethod(new ScrollingMovementMethod());
            Glide.with(show_info_book.this).load(books_data.getImgUrl()).into(cover_details);
         } else {
             finish();
         }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_info_book, menu);
        publish = menu.findItem(R.id.publishBooks);
        setVisible();
        return true;
    }

    void setVisible(){
        if (!isPublish){
            publish.setIcon(R.drawable.check);

        }else {
           publish.setIcon(R.drawable.cancel);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
            return true;
        }
        if (item.getItemId()== R.id.add_file_books){
            View v = findViewById(R.id.add_file_books);
            v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
            addFile();
        }
        if (item.getItemId()== R.id.edit_chapter){
            View v = findViewById(R.id.edit_chapter);
            v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
            openEdit();
        }
        if (item.getItemId()== R.id.deleteBook){
            View v = findViewById(R.id.deleteBook);
            v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
            deleteBooks();
        }
        if (item.getItemId()== R.id.publishBooks){
            View v = findViewById(R.id.publishBooks);
            v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
            if(!isPublish){
                publishBooks();
            }else {
                unPublishBooks();
            }

//            if (!isPublish){
//                publishBooks();
//                publish.setVisible(true);
//                unPublish.setVisible(false);
//            }


            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void addFile(){
        Intent intent= new Intent();
       // intent.setType("application/pdf");
        intent.setType("application/pdf");
      //  String [] mimetypes = {"application/pdf", "application/epub+zip"};
        intent.setAction(Intent.ACTION_GET_CONTENT);
       // intent.putExtra(Intent.EXTRA_MIME_TYPES,mimetypes);

        startAttach.launch(Intent.createChooser(intent, "Select File"));



    }

    public void uploadFile(Uri fileUrl) {

        //Tạo tên file hình ngẫu nhiên

        String filename = UUID.randomUUID().toString();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("File").child(filename);

        //Upload file lên firebase storage
        progressDialog.show();
        storageReference.putFile(fileUrl).addOnSuccessListener(taskSnapshot -> {
            //get url
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String file = uri.toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String id_books= books_data.getId();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference("Books").child(id_books);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("fileUrl").setValue(file);
                        progressDialog.dismiss();
                        Toast.makeText(show_info_book.this, "Upload file Successfull", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            });
        }).addOnFailureListener(e -> {
            Toast.makeText(show_info_book.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    final ActivityResultLauncher<Intent> startAttach = registerForActivityResult(
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
                        Uri file = intent.getData();
                        uploadFile(file);




                    }
                }
            });

    private void publishBooks(){
        showConfirmPublic(Gravity.CENTER);

    }

    private void showConfirmPublic(int gravity) {
        final Dialog confirm_public = new Dialog(show_info_book.this);
        confirm_public.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm_public.setContentView(R.layout.custom_pulicbook);
        Window window = confirm_public.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams winAtr = window.getAttributes();
        winAtr.gravity = gravity;
        window.setAttributes(winAtr);
        if (Gravity.CENTER == gravity){
            confirm_public.setCancelable(true);
        }else {
            confirm_public.setCancelable(false);
        }
        yes_publish = confirm_public.findViewById(R.id.yes_public);
        cancel_publish = confirm_public.findViewById(R.id.cancel_public);

        confirm_public.show();
        yes_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));

                String id_books= books_data.getId();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference().child("Books").child(id_books);
                progressDialog.show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Date date = new Date();
                        Long timestamp = date.getTime();
                        String publishStatus = "public";
                        databaseReference.child("timestamp").setValue(timestamp);
                        databaseReference.child("publishStatus").setValue(publishStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(show_info_book.this, "Update Successfull", Toast.LENGTH_SHORT).show();
                            }
                        });

                        databaseReference.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        cancel_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
                confirm_public.dismiss();
            }
        });
    }

    private void unPublishBooks(){
        showConfirmUnpublic(Gravity.CENTER);
    }

    private void showConfirmUnpublic(int gravity) {
        final Dialog confirm_private = new Dialog(show_info_book.this);
        confirm_private.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm_private.setContentView(R.layout.custom_privatebook);
        Window window = confirm_private.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams winAtr = window.getAttributes();
        winAtr.gravity = gravity;
        window.setAttributes(winAtr);
        if (Gravity.CENTER == gravity){
            confirm_private.setCancelable(true);
        }else {
            confirm_private.setCancelable(false);
        }
        yes_unpublis = confirm_private.findViewById(R.id.yes_private);
        cancel_unpublish = confirm_private.findViewById(R.id.cancel_private);

        confirm_private.show();
        yes_unpublis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
                String id_books= books_data.getId();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference().child("Books").child(id_books);
                progressDialog.show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String publishStatus = "private";
                        databaseReference.child("timestamp").removeValue();
                        databaseReference.child("publishStatus").setValue(publishStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(show_info_book.this, "Unpublish Successfull", Toast.LENGTH_SHORT).show();
                            }
                        });


                        databaseReference.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        cancel_unpublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
                confirm_private.dismiss();
            }
        });
    }

    private void addPublishChangeListener(){
        String id_books= books_data.getId();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference().child("Books").child(id_books);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Books_data BookDataUpdate = snapshot.getValue(Books_data.class);
                if (BookDataUpdate!= null){
                    books_data.setPublishStatus(BookDataUpdate.getPublishStatus());
                    String publishStatus = books_data.getPublishStatus();
                    if (publishStatus.contains("private")){
                        isPublish= false;

                    }else if (publishStatus.contains("public")){
                        isPublish=true;
                    }
                    invalidateOptionsMenu();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void openEdit(){
        String id_books= books_data.getId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("Books").child(id_books);
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user_cur = snapshot.getValue(User.class);
                if (user_cur!=null){
                    Intent intent=new Intent(show_info_book.this, edit_book.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("objectBooks", books_data);
                    intent.putExtras(bundle2);

                    startActivityForResult.launch(intent);
                    reference.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(valueEventListener);
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
                        else {
                            // Uri photo = intent.getData();
                            Bundle bundle_reply = result.getData().getExtras();
                            if (bundle_reply!= null){
                                Books_data bookAfterEdited= (Books_data) bundle_reply.getSerializable("objectBooks");
                                books_data.setTitle(bookAfterEdited.getTitle());
                                books_data.setDescription(bookAfterEdited.getDescription());
                                books_data.setStatus(bookAfterEdited.getStatus());
                                books_data.setAuthors(bookAfterEdited.getAuthors());
                                books_data.setCategory(bookAfterEdited.getCategory());
                                books_data.setImgUrl(bookAfterEdited.getImgUrl());
                                title_book.setText(books_data.getTitle());
                                author_book.setText(books_data.getAuthors());
                                category_book.setText(books_data.getCategory());
                                status_book.setText(books_data.getStatus());
                                description_book.setText(books_data.getDescription());
                                Glide.with(show_info_book.this).load(books_data.getImgUrl()).into(cover_details);

                            }
                        }




                    }
                }
            });

    private void deleteBooks(){
        openConfirm(Gravity.CENTER);
    }

    private void openConfirm(int gravity) {
        final Dialog confirm_delete = new Dialog(show_info_book.this);
        confirm_delete.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm_delete.setContentView(R.layout.custom_alertdialog_books);
        Window window = confirm_delete.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams winAtr = window.getAttributes();
        winAtr.gravity = gravity;
        window.setAttributes(winAtr);
        if (Gravity.CENTER == gravity){
            confirm_delete.setCancelable(true);
        }else {
            confirm_delete.setCancelable(false);
        }
        yes_delete = confirm_delete.findViewById(R.id.deleteBooks);
        cancel_delete = confirm_delete.findViewById(R.id.cancel_delete);

        confirm_delete.show();

        yes_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
                progressDialog.show();
                String id_books= books_data.getId();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference().child("Books").child(id_books);
                databaseReference.setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(show_info_book.this, ListBook.class);
                        intent.putExtra("removeId", books_data.getId());
                        Toast.makeText(show_info_book.this, "Delete Success", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }
        });

        cancel_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(show_info_book.this, R.anim.btn_click_anim));
                confirm_delete.dismiss();
            }
        });

    }

}