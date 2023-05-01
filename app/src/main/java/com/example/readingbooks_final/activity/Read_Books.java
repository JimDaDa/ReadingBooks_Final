package com.example.readingbooks_final.activity;


import static com.example.readingbooks_final.database.SaveBookCurrent.BOOK_MARK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.custom.CustomDialogProgress;
import com.example.readingbooks_final.database.Books_data;
import com.example.readingbooks_final.database.Constants;
import com.example.readingbooks_final.database.DataLocal;
import com.example.readingbooks_final.database.SaveBookCurrent;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.StringValue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;


public class Read_Books extends AppCompatActivity {
    private float userRate;
    private PDFView pdfView;
    private CustomDialogProgress progressDialog;
    private AppCompatButton rateNow, rateLater, yes, cancel;

    private RatingBar ratingBar;
    private ImageView rateImg ;
    private String bookId;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_books);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();
        recieveData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        SaveBookCurrent saveBookCurrent= new SaveBookCurrent(getApplicationContext());

        int bookmark= saveBookCurrent.getIntValue(BOOK_MARK + bookId);
        saveBookCurrent.putIntValue(BOOK_MARK + bookId,bookmark);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
        }
        if (item.getItemId()==R.id.vote_read){
            View v = findViewById(R.id.vote_read);
            v.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
            voteBooks();
        }
        if (item.getItemId()==R.id.report_read){
            View v = findViewById(R.id.report_read);
            v.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
            reportBooks();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        pdfView = findViewById(R.id.pdfView);
        progressDialog= new CustomDialogProgress(this);

    }
    private void recieveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            bookId= books_data.getId();
            loadPdf();


        }
    }
    private void loadPdf(){
    DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Books").child(bookId);
    reference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {


                    String urlFile = ""+snapshot.child("fileUrl").getValue();
                    StorageReference reference1 = FirebaseStorage.getInstance().getReferenceFromUrl(urlFile);
                    progressDialog.show();
                    reference1.getBytes(Constants.MAX_BYTE_PDF).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            progressDialog.dismiss();
                          SaveBookCurrent saveBookCurrent= new SaveBookCurrent(getApplicationContext());

                             int bookmark= saveBookCurrent.getIntValue(BOOK_MARK + bookId);
//                            Paint paint = new Paint();
//                            paint.setColor(Color.BLUE);
//                            paint.setAlpha(100);
//                            paint.setStyle(Paint.Style.FILL);
                            int hightlight= Color.BLUE;

                          //   DataLocal.setMyBookMark(currentPage);
                            pdfView.fromBytes(bytes).swipeHorizontal(false)

                                    .onPageChange(new OnPageChangeListener() {
                                @Override
                                public void onPageChanged(int page, int pageCount) {
                                  //  currentPage =page;

                                   // DataLocal.setMyBookMark(currentPage);
                                    saveBookCurrent.putIntValue(BOOK_MARK + bookId,page);
                                }
                            })
                                    .onLoad(new OnLoadCompleteListener() {
                                @Override
                                public void loadComplete(int nbPages) {
                                  //  int bookmark = DataLocal.getMyBookMark();
                                    SaveBookCurrent saveBookCurrent = new SaveBookCurrent(getApplicationContext());
                                    int cur= saveBookCurrent.getIntValue(BOOK_MARK + bookId);
                                    pdfView.jumpTo(cur);
                                }
                            })
                                    .load();




                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Read_Books.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_book, menu);
        return true;
    }


    private void voteBooks(){
        openDialog(Gravity.CENTER);


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

private void reportBooks(){
    openConfirm(Gravity.CENTER);
}

private void openConfirm(int gravity){
    final Dialog confirm = new Dialog(Read_Books.this);
    confirm.requestWindowFeature(Window.FEATURE_NO_TITLE);
    confirm.setContentView(R.layout.custom_alertdialog);
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
            v.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
           // ChooseReason();
            ChooseReason(Gravity.CENTER);
            confirm.dismiss();
        }
    });
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
            confirm.dismiss();
        }
    });
}

private void ChooseReason(int gravity){
    final Dialog chooseReason = new Dialog(Read_Books.this);
    chooseReason.requestWindowFeature(Window.FEATURE_NO_TITLE);
    chooseReason.setContentView(R.layout.custom_choose_reason);
    Window window = chooseReason.getWindow();
    if (window == null){
        return;
    }
    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    WindowManager.LayoutParams winAtr = window.getAttributes();
    winAtr.gravity = gravity;
    window.setAttributes(winAtr);
    if (Gravity.CENTER == gravity){
        chooseReason.setCancelable(true);
    }else {
        chooseReason.setCancelable(false);
    }
    yes = chooseReason.findViewById(R.id.yes_report);
    cancel = chooseReason.findViewById(R.id.cancel_report);

    chooseReason.show();

    yes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
            RadioGroup radioGroup = chooseReason.findViewById(R.id.radiogroup);
            int selectedId= radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = chooseReason.findViewById(selectedId);
            CharSequence selectedText = radioButton.getText().toString().trim();
            Bundle bundle = getIntent().getExtras();
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            String id_books= books_data.getId();
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String uid = auth.getUid();

            DatabaseReference databaseReference=database.getReference().child("Books").child(id_books).child("report");
            progressDialog.show();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean checkUser = false;
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        Books_data books_report = snapshot1.getValue(Books_data.class);
                        String id_rp = books_report.getId_report();
                                String id_user = auth.getCurrentUser().getUid();
                        //Nếu tồn tại id_user
                        if (books_report.getId_user().equals(id_user)) {
                            checkUser = true;
                            databaseReference.child(id_rp).child("reason").setValue(selectedText);
                            progressDialog.dismiss();
                            Toast.makeText(Read_Books.this, "Report Successfull", Toast.LENGTH_SHORT).show();
                            chooseReason.dismiss();
                            break;
                        }
                    }
                        if (!checkUser){
                            //Nếu người dùng chưa report lần nào thì rating
                            int i = 0;
                            i = i + 1;
                            String report = String.valueOf(i);
                            String id_rp = databaseReference.push().getKey();
                            String id_user = auth.getCurrentUser().getUid();
                        Books_data books = new Books_data(id_rp,id_user,selectedText,report);

                        databaseReference.child(id_rp).setValue(books.ReportMap());

                        progressDialog.dismiss();
                        Toast.makeText(Read_Books.this, "Report Successfull", Toast.LENGTH_SHORT).show();
                        chooseReason.dismiss();
                        databaseReference.removeEventListener(this);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    });
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
            chooseReason.dismiss();
        }
    });

}

    private void openDialog(int gravity){
        final Dialog rate = new Dialog(Read_Books.this);
        rate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rate.setContentView(R.layout.rate_book);
        Window window = rate.getWindow();

        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams winAtr = window.getAttributes();
        winAtr.gravity = gravity;
        window.setAttributes(winAtr);
        if (Gravity.CENTER == gravity){
            rate.setCancelable(true);
        }else {
            rate.setCancelable(false);
        }

        rateNow = rate.findViewById(R.id.rateNow);
        rateLater = rate.findViewById(R.id.rateLater);
        ratingBar = rate.findViewById(R.id.ratingBar);
        rateImg = rate.findViewById(R.id.rateImg);
        rate.show();
        rateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
                addRating();
                rate.dismiss();
            }
        });

        rateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(Read_Books.this, R.anim.btn_click_anim));
                rate.dismiss();

            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if(rating <= 1)
                {
                    rateImg.setImageResource(R.drawable.rate1);
                }
                else if(rating <= 2)
                {
                    rateImg.setImageResource(R.drawable.rate2);
                }
                else if(rating <= 3)
                {
                    rateImg.setImageResource(R.drawable.rate3);
                }
                else if(rating <= 4)
                {
                    rateImg.setImageResource(R.drawable.rate4);
                }
                else if(rating <= 5)
                {
                    rateImg.setImageResource(R.drawable.rate5);
                }

                animateImg(rateImg);
                userRate = rating;
            }
        });


    }

    private void addRating(){

        //Nhận dữ liệu
        Bundle bundle = getIntent().getExtras();
        Books_data books_data= (Books_data) bundle.get("objectBooks");
        //Lấy id quyển sách đó
        String id_books= books_data.getId();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference=database.getReference().child("Books").child(id_books).child("rating");
        progressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExist =false;
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Books_data books_data1= snapshot1.getValue(Books_data.class);
                    String id_rating = books_data1.getId_rating();
                    String  id_user=auth.getCurrentUser().getUid();
                    //Nếu tồn tại id_user
                    if (books_data1.getId_user().equals(id_user)){
                        userExist= true;
                        databaseReference.child(id_rating).child("score").setValue(userRate);
                        progressDialog.dismiss();
                        Toast.makeText(Read_Books.this, "Rating Successfull", Toast.LENGTH_SHORT).show();

                        break;
                    }

                }

                if (!userExist){
                    //Nếu user chưa rating thì rating
                    // Books_data books_data1= snapshot.getValue(Books_data.class);
                    String id_rating = databaseReference.push().getKey();
                    String  id_user=auth.getCurrentUser().getUid();
                    Books_data books = new Books_data(id_rating,id_user,userRate);

                    databaseReference.child(id_rating).setValue(books.RatingMap());

                    progressDialog.dismiss();
                    Toast.makeText(Read_Books.this, "Rating Successfull", Toast.LENGTH_SHORT).show();

                    databaseReference.removeEventListener(this);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void animateImg(ImageView rateImg)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        rateImg.startAnimation(scaleAnimation);

    }




}



