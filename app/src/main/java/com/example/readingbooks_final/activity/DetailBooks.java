package com.example.readingbooks_final.activity;

import static android.content.ContentValues.TAG;

import static com.example.readingbooks_final.database.Constants.MAX_BYTE_PDF;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;

public class DetailBooks extends AppCompatActivity {

    private ImageView cover_detail;
    private TextView title_details, author_details, description_details, tv_view_details, tv_status, vote_tv;
    private Button read_books, fav_book, download;
    private boolean isLiked ;
    private float avg = 0f;

    private long mView ;
    private AppCompatButton  yes, cancel, yes_unlike, cancel_unlike;

    private CustomDialogProgress dialogProgress ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_book);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setIcon(R.drawable.logo);
        initView();
        //setVisible();
        addLikeChangeListener();
        recieveData();
      //  clickButtonRead();
        clickButtonToSaveLibrary();
        clickButtonDownload();
      // total_rating();
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
        download=findViewById(R.id.download);
        dialogProgress= new CustomDialogProgress(DetailBooks.this);

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
            float getVote = books_data.getTotal_rating();
            String getVoteString = String.valueOf(getVote);
            if (getVoteString == null){
                vote_tv.setText("0.0/5.0");
            }else {
                vote_tv.setText(getVoteString + " /5.0");
            }

//            category_book.setText(books_data.getCategory());
//            status_book.setText(books_data.getStatus());
            description_details.setText(books_data.getDescription());
            description_details.setMovementMethod(new ScrollingMovementMethod());
            Glide.with(DetailBooks.this).load(books_data.getImgUrl()).into(cover_detail);
        }
    }

    //Click để đọc sách
    private void clickButtonRead(){
        read_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(DetailBooks.this, R.anim.btn_click_anim));
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
                       curr_view++;
                       ref.setValue(curr_view);
                       mView=curr_view;
                       String getViewString = String.valueOf(Long.valueOf(mView));
                       tv_view_details.setText(getViewString + " View");

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


    //Click để thêm vào thư viện

    void setVisible(){
        Drawable like = getResources().getDrawable(R.drawable.like);
        Drawable unlike = getResources().getDrawable(R.drawable.unlike);
        if (!isLiked){
            //fav_book.setText("LIKE");
            fav_book.setCompoundDrawablesWithIntrinsicBounds(unlike,null,null,null);

        }else {
            //fav_book.setText("UNLIKE");
            fav_book.setCompoundDrawablesWithIntrinsicBounds(like,null,null,null);

        }
    }
    private void clickButtonToSaveLibrary(){
        fav_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(DetailBooks.this, R.anim.btn_click_anim));

                if(!isLiked){
                    //Open Dialog
                    openConfirm(Gravity.CENTER);

                }else {

                    //OpenDialog
                    openConfirmUnlike(Gravity.CENTER);

                }

            }
        });

    }

    private void openConfirmUnlike(int gravity) {
        final Dialog confirm_unlike = new Dialog(DetailBooks.this);
        confirm_unlike.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm_unlike.setContentView(R.layout.custom_unlikebook);
        Window window = confirm_unlike.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams winAtr = window.getAttributes();
        winAtr.gravity = gravity;
        window.setAttributes(winAtr);
        if (Gravity.CENTER == gravity){
            confirm_unlike.setCancelable(true);
        }else {
            confirm_unlike.setCancelable(false);
        }
        yes_unlike = confirm_unlike.findViewById(R.id.yes_unlike);
        cancel_unlike = confirm_unlike.findViewById(R.id.cancel_unlike);

        confirm_unlike.show();
        yes_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(DetailBooks.this, R.anim.btn_click_anim));
                RemoveBooks();
                confirm_unlike.dismiss();
            }
        });
        cancel_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(DetailBooks.this, R.anim.btn_click_anim));
                confirm_unlike.dismiss();
            }
        });
    }

    private void openConfirm(int gravity) {
        final Dialog confirm = new Dialog(DetailBooks.this);
        confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirm.setContentView(R.layout.custom_likebook);
        Window window = confirm.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams winAtr = window.getAttributes();
        winAtr.gravity = gravity;
        window.setAttributes(winAtr);
        if (Gravity.CENTER == gravity){
            confirm.setCancelable(true);
        }else {
            confirm.setCancelable(false);
        }
        yes = confirm.findViewById(R.id.yes);
        cancel = confirm.findViewById(R.id.cancel);

        confirm.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(DetailBooks.this, R.anim.btn_click_anim));
                SaveBooksConfirm();
                confirm.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(DetailBooks.this, R.anim.btn_click_anim));
                confirm.dismiss();
            }
        });

    }

    private void addLikeChangeListener(){
        Bundle bundle = getIntent().getExtras();
        Books_data books_data= (Books_data) bundle.get("objectBooks");
        String id_books= books_data.getId();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id_user = auth.getCurrentUser().getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference favBookRef = database.getReference("Users").child(id_user).child("Fav_Books");
        Query query =database.getReference("Users").child(id_user).child("Fav_Books").orderByChild("id_book").equalTo(id_books);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    //user.setLikedStatus(user.getLikedStatus());
                    String like_id = user.getId_like();
                    String key = snapshot1.getKey();
                    String like_status = user.getLikedStatus();
                    String book_id = user.getId_book();

                    if (book_id.equals(id_books) && like_status.contains("liked")){
                        isLiked =true;
                    }
                    else {
                        isLiked =false;
                    }
                    setVisible();
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        favBookRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1: snapshot.getChildren()){
//                    User user = snapshot1.getValue(User.class);
//                    //user.setLikedStatus(user.getLikedStatus());
//                    String like_id = user.getId_like();
//                    String key = snapshot1.getKey();
//                    String like_status = user.getLikedStatus();
//                    String book_id = user.getId_book();
//
//                    if (book_id.equals(id_books) && like_status.contains("liked")){
//                        isLiked =true;
//                    }
//                    else {
//                        isLiked =false;
//                    }
//                    setVisible();
//                    break;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


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
                String likedStatus = "liked";
                String id_like = databaseReference.child("Fav_Books").push().getKey();
                User user_like = new User(id_like,books_id,likedStatus);

                databaseReference.child("Fav_Books").child(id_like).setValue(user_like.BookLike());
                Toast.makeText(DetailBooks.this, "Save Successfull", Toast.LENGTH_SHORT).show();
                databaseReference.removeEventListener(this);


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
    //id user hiện tại
    String id_user = auth.getCurrentUser().getUid();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    //tham chiếu đến Fav_Books
    DatabaseReference databaseReference=database.getReference("Users").child(id_user).child("Fav_Books");
    //Nhận dữ liệu khi click vào item
    Bundle bundle = getIntent().getExtras();
    Books_data books_data= (Books_data) bundle.get("objectBooks");
    //id book khi click vào
    String books_id = books_data.getId();
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                User user = snapshot1.getValue(User.class);

             //   String id_user = user.getId();
                String id_like = user.getId_like();
                String id_book_user = user.getId_book();

                if (id_book_user.equals(books_id)){
                    databaseReference.child(id_like).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Intent intent = new Intent();
                            intent.putExtra("removelike",books_data.getId());
                            Toast.makeText(DetailBooks.this, "Remove from Library Success", Toast.LENGTH_SHORT).show();

                            // fav_book.setText("LIKE");
                            setResult(RESULT_OK,intent);
                            finish();

                        }
                    });
                }

            }






        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "Error", error.toException());
        }
    };
    databaseReference.addListenerForSingleValueEvent(valueEventListener);
}

//    private void total_rating(){
//
//    }


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

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id_user = auth.getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        //Nhận dữ liệu
        Bundle bundle = getIntent().getExtras();
        Books_data books_data= (Books_data) bundle.get("objectBooks");
        //Lấy id quyển sách đó
        String id_books= books_data.getId();
        DatabaseReference databaseReference=database.getReference().child("Books").child(id_books).child("total_rating");
        Query ratingRef = database.getReference().child("Books").child(id_books).child("rating");
        ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float totalScore = 0;
                int count=0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Books_data books_data1 = snapshot1.getValue(Books_data.class);

                    Float score = books_data1.getScore();
                    if (score!= null){
                        totalScore+=score;
                        count++;

                    }
                }
                if (count>0){
                    float average = (float) totalScore / count ;
                    databaseReference.setValue(average);
                    avg=average;

                }
              //  float getVote = books_data.getTotal_rating();
                String getVoteString = String.format("%.1f / 5.0", avg);
              //  String getVoteString = String.valueOf(getVote);
                vote_tv.setText(getVoteString);
                //setResult(Activity.RESULT_OK);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        clickButtonRead();


//        database.getReference().child("Books").child(id_books).child("view").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    Books_data view_data = dataSnapshot.getValue(Books_data.class);
//                    String view = String.valueOf(view_data.getView());
//
//                    Log.d("", "view: "+view);
//                    System.out.println(view);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    private void clickButtonDownload(){
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String id_user = auth.getUid();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                //Nhận dữ liệu
                Bundle bundle = getIntent().getExtras();
                Books_data books_data= (Books_data) bundle.get("objectBooks");
                //Lấy id quyển sách đó
                String id_books= books_data.getId();
                DatabaseReference databaseReference=database.getReference().child("Books").child(id_books);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            //link pdf trong realtime
                            String fileUrl = snapshot.child("fileUrl").getValue(String.class);
                            if (fileUrl!= null){
                                //file trong storage
                                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileUrl);

                                dialogProgress.show();
                                storageReference.getBytes(MAX_BYTE_PDF).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {

                                        String nameExtension = books_data.getTitle() + ".pdf";
                                        try {
                                            File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                            downloadFolder.mkdir();
                                            String filePath = downloadFolder.getPath() + "/" + nameExtension;
                                            FileOutputStream out = new FileOutputStream(filePath);
                                            out.write(bytes);
                                            out.close();
                                            Toast.makeText(DetailBooks.this, "Download Success at "+filePath, Toast.LENGTH_SHORT).show();
                                            dialogProgress.dismiss();


                                        }catch (Exception e){
                                            dialogProgress.dismiss();
                                            Toast.makeText(DetailBooks.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogProgress.dismiss();
                                        Toast.makeText(DetailBooks.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }
                        databaseReference.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailBooks.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                };
                databaseReference.addListenerForSingleValueEvent(valueEventListener);
            }
        });

    }

}