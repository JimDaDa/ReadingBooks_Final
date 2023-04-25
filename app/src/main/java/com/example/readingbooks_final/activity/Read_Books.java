package com.example.readingbooks_final.activity;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.Books_data;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private String[] reason = new String[] {"Reason A", "Reason B", "Reason C"};
    private ProgressDialog progressDialog;
    private AppCompatButton rateNow, rateLater;

    private RatingBar ratingBar;
    private ImageView rateImg ;
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            onBackPressed();
        }
        if (item.getItemId()==R.id.vote_read){
            voteBooks();
        }
        if (item.getItemId()==R.id.report_read){
            reportBooks();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        pdfView = findViewById(R.id.pdfView);
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);


    }
    private void recieveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            Books_data books_data= (Books_data) bundle.get("objectBooks");
            String urlFile = books_data.getFileUrl();
            Log.d("", "link ne "+urlFile);
          //  System.out.println(urlFile);
            //pdfView.fromAsset(urlFile).load();
//pdfView.fromUri(Uri.parse(urlFile));
            new RetrivePDFfromUrl().execute(urlFile);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_book, menu);
        return true;
    }


    class  RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream= null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()==200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
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
    AlertDialog.Builder builder = new AlertDialog.Builder(Read_Books.this);
    builder.setTitle("Confirm");
    builder.setMessage("Are you sure to Report Books?");

    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            ChooseReason();


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
    private void  ChooseReason() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Read_Books.this);
        builder.setTitle("Select Reason");
        builder.setSingleChoiceItems(reason, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String reason1= reason[i];

                Bundle bundle = getIntent().getExtras();
                Books_data books_data= (Books_data) bundle.get("objectBooks");
                String id_books= books_data.getId();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                //String uid = auth.getUid();

                DatabaseReference databaseReference=database.getReference().child("Books").child(id_books).child("report");
                progressDialog.show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        int i= 0;
                        i=i+1;
                        String report= String.valueOf(i);
                        String id_rp = databaseReference.push().getKey();
                        String  id_user=auth.getCurrentUser().getUid();

                        Books_data books = new Books_data(id_rp,id_user,report,reason1);

                        databaseReference.child(id_rp).setValue(books.ReportMap());

                        progressDialog.dismiss();
                        Toast.makeText(Read_Books.this, "Report Successfull", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        databaseReference.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openDialog(int gravity){
        final Dialog rate = new Dialog(Read_Books.this);
        // final  rate_book rate = new rate_book(Read_Books.this);
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
        if (Gravity.BOTTOM == gravity){
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
                addRating();
            }
        });

        rateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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



