package com.example.readingbooks_final.activity;

import static com.example.readingbooks_final.fragment.Account.EMAIL_REPLY;
import static com.example.readingbooks_final.fragment.Account.NAME_REPLY;
import static com.example.readingbooks_final.fragment.Account.PHONE_REPLY;
import static com.example.readingbooks_final.fragment.Account.PHOTO_REPLY;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.database.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class profile extends AppCompatActivity {

    private EditText name,email,phone;
    private ImageView avatar;
    private Button btnSave;

    private TextView fullname, email_acc;

    private ImageButton edit_ava;


    private GoogleSignInAccount user_gg;



    private String name_up, phone_up, ava_up;

    Bitmap avatarImage;

    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setDisplayShowHomeEnabled(true);


        AnhXa();
        ReceiveData();
        clickSave();
        set_ava();

    }

    private void AnhXa(){
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        avatar=findViewById(R.id.avatar);
        btnSave=findViewById(R.id.btnSave);
        edit_ava=findViewById(R.id.edit_ava);
        fullname=findViewById(R.id.fullname_pro5);
        email_acc=findViewById(R.id.acc_email_profile);
        email.setEnabled(false);


    }


    //Nhận dữ liệu từ trang Account
    private void ReceiveData(){
        Intent intent = getIntent();
        String name_reply = intent.getStringExtra(NAME_REPLY);
        String phone_reply = intent.getStringExtra(PHONE_REPLY);
        String email_reply = intent.getStringExtra(EMAIL_REPLY);
        String ava_reply = intent.getStringExtra(PHOTO_REPLY);

        name.setText(name_reply);
        fullname.setText(name_reply);
        email_acc.setText(email_reply);
        phone.setText(phone_reply);
        email.setText(email_reply);
        Glide.with(profile.this).load(ava_reply).into(avatar);

    }

    //Cập nhật dữ liệu vừa chỉnh sửa lên firebase
    public void clickSave(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                assert user != null;
                reference= database.getReference("Users").child(user.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user_cur = snapshot.getValue(User.class);
                        if (user_cur!= null){
                            String content_name= name.getText().toString().trim();
                            String content_phone= phone.getText().toString().trim();
                            String content_email= email.getText().toString().trim();
                            String content_avatar= user_cur.getAvatar();

                            //Update profile
                            String name_up= String.valueOf(reference.child("fullname").setValue(content_name));
                            String phone_up= String.valueOf(reference.child("phone").setValue(content_phone));
                            //String ava_up =String.valueOf(reference.child("avatar").setValue(content_avatar));

                            Intent intent_reply= new Intent();

                            intent_reply.putExtra(NAME_REPLY, content_name);
                            intent_reply.putExtra(PHONE_REPLY,content_phone);
                            intent_reply.putExtra(EMAIL_REPLY,content_email);
                            intent_reply.putExtra(PHOTO_REPLY,content_avatar);

                            setResult(Activity.RESULT_OK, intent_reply);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }



    //Hàm thay đổi avtar
    private void set_ava(){
        edit_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcherActivityAva.launch(intent);

            }
        });
    }

    public void uploadImage(Uri photo) {
        //Tạo tên file hình ngẫu nhiên

        String filename = UUID.randomUUID().toString();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("avatar").child(filename);

        //Upload ảnh lên firebase storage

        storageReference.putFile(photo).addOnSuccessListener(taskSnapshot -> {
            //get url
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                FirebaseUser auth= FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                String cover = uri.toString();
                DatabaseReference databaseReference = database.getReference("Users").child(auth.getUid());
                databaseReference.child("avatar").setValue(cover);




            });
        }).addOnFailureListener(e -> {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    // Sau khi chọn ảnh xong thì sẽ update ở trang profile
    final ActivityResultLauncher<Intent> launcherActivityAva = registerForActivityResult(
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


                       // uploadImage(photo, id);
                        Glide.with(profile.this).load(photo).into(avatar);



                    }
                }
            });
}