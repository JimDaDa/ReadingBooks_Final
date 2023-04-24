package com.example.readingbooks_final.activity;

import static android.content.ContentValues.TAG;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.MainActivity;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.example.readingbooks_final.database.User;
import com.example.readingbooks_final.fragment.Books;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailBooks extends AppCompatActivity {

    private ImageView cover_detail;
    private TextView title_details, author_details, description_details, tv_view_details, tv_status, vote_tv;
    private Button read_books, fav_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_book);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        recieveData();
        clickButtonRead();
        clickButtonToSaveLibrary();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void initView(){
        cover_detail=findViewById(R.id.cover_detail);
        title_details=findViewById(R.id.title_details);
        author_details=findViewById(R.id.author_details);
        description_details=findViewById(R.id.description_details);
        read_books=findViewById(R.id.read_books);
        tv_view_details= findViewById(R.id.tv_view_details);
        tv_status= findViewById(R.id.tv_status);
        vote_tv= findViewById(R.id.vote_tv);
        fav_book= findViewById(R.id.fav_book);

    }
    private void recieveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            title_details.setText(books_data.getTitle());
            author_details.setText(books_data.getAuthors());
            long getView= books_data.getView();
            String getViewString = String.valueOf(Long.valueOf(getView));
            tv_view_details.setText(getViewString + " View");
            tv_status.setText(books_data.getStatus());
            float getVote = books_data.getRating();
            String getVoteString = String.valueOf(getVote);
            vote_tv.setText(getVoteString + " /5.0");
//            category_book.setText(books_data.getCategory());
//            status_book.setText(books_data.getStatus());
            description_details.setText(books_data.getDescription());
            description_details.setMovementMethod(new ScrollingMovementMethod());
            Glide.with(DetailBooks.this).load(books_data.getImgUrl()).into(cover_detail);
        }
    }
    private void clickButtonRead(){
        read_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                Books_data books_data= (Books_data) bundle.get("objectBooks");
                String books_id = books_data.getId();
                Intent intent=new Intent(DetailBooks.this, Read_Books.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("objectBooks", books_data);
                intent.putExtras(bundle2);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books").child(books_id).child("view");
                //Lấy giá trị hiện tại view
               ref.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       long curr_view =snapshot.getValue(Long.class);
                       //Tăng giá trị lên 1 và up lên database
                       ref.setValue(curr_view+1);

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Log.e("Error",error.getMessage());

                   }
               });

               // startActivity(intent);
                 startActivityForResult.launch(intent);
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


                        }



                    }

            });

    private void clickButtonToSaveLibrary(){
        fav_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav_book.getText().toString().equals("LIKE")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailBooks.this);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure to Save Books to Library?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SaveBooksConfirm();


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

                } else if (fav_book.getText().toString().equals("UNLIKE")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailBooks.this);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure to Remove Books to Library?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           RemoveBooks();


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
        });

    }



    private void SaveBooksConfirm(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id_user = auth.getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("Users").child(id_user);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Bundle bundle = getIntent().getExtras();
                Books_data books_data= (Books_data) bundle.get("objectBooks");
                String books_id = books_data.getId();
                String id_user = user.getId();
                String id_like = databaseReference.child("Fav_Books").push().getKey();
                User user_like = new User(id_like,books_id);

                databaseReference.child("Fav_Books").child(id_like).setValue(user_like.BookLike());
                Toast.makeText(DetailBooks.this, "Save Successfull", Toast.LENGTH_SHORT).show();
                fav_book.setText("UNLIKE");

//                Intent intent=new Intent(DetailBooks.this, Books.class);
//                Bundle bundle2 = new Bundle();
//                bundle2.putSerializable("objectBooks", books_data);
//                intent.putExtras(bundle2);
//                startBooksFragment.launch(intent);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error", error.toException());
            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);



    }

private void RemoveBooks(){
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String id_user = auth.getUid();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=database.getReference("Users").child(id_user);
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            User user = snapshot.getValue(User.class);
            Bundle bundle = getIntent().getExtras();
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            String books_id = books_data.getId();
            String id_user = user.getId();
            String id_like = user.getId_like();


            databaseReference.child("Fav_Books").child(id_like).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Intent intent = new Intent();
                    intent.putExtra("removelike",books_data.getId());
                    Toast.makeText(DetailBooks.this, "Remove from Library Success", Toast.LENGTH_SHORT).show();
                    fav_book.setText("LIKE");
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });





        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "Error", error.toException());
        }
    };
    databaseReference.addListenerForSingleValueEvent(valueEventListener);
}


    final ActivityResultLauncher<Intent> startBooksFragment = registerForActivityResult(
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






                    }
                }
            });
}