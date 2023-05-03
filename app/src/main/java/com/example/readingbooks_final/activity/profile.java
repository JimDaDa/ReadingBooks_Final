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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.custom.CustomDialogProgress;
import com.example.readingbooks_final.database.Books_data;
import com.example.readingbooks_final.database.User;
import com.example.readingbooks_final.fragment.Account;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.UploadTask;

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

    private CustomDialogProgress dialogProgress;


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
        dialogProgress = new CustomDialogProgress(profile.this);


    }


    //Nhận dữ liệu từ trang Account
    private void ReceiveData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            User user= (User) bundle.get("objectUser");

                name.setText(user.getFullname());
                fullname.setText(user.getFullname());
                email_acc.setText(user.getEmail());
                phone.setText(user.getPhone());
                email.setText(user.getEmail());
                Glide.with(profile.this).load(user.getAvatar()).into(avatar);






        }

    }
    //Cập nhật dữ liệu vừa chỉnh sửa lên firebase






public void clickSave(){
    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(AnimationUtils.loadAnimation(profile.this, R.anim.btn_click_anim));
            Bundle bundle = getIntent().getExtras();
            if (bundle!= null){
                User user_edit= (User) bundle.get("objectUser");
                String id_user= user_edit.getId();
               // FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference().child("Users").child(id_user);
                String content_name= name.getText().toString().trim();
                String content_phone= phone.getText().toString().trim();
                user_edit.setFullname(content_name);
                user_edit.setPhone(content_phone);
                dialogProgress.show();
               databaseReference.updateChildren(user_edit.updateUser());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user= snapshot.getValue(User.class);
                        if (user!= null){
                            //Update profile
//                            databaseReference.child("fullname").setValue(content_name);
//                            databaseReference.child("phone").setValue(content_phone);
                            dialogProgress.dismiss();
                            Toast.makeText(profile.this, "Update Success", Toast.LENGTH_SHORT).show();
                            //String name_up= String.valueOf(databaseReference.child("fullname").setValue(content_name));
                            //  String phone_up= String.valueOf(databaseReference.child("phone").setValue(content_phone));
                            Intent intent_reply=new Intent();
                            Bundle bundle2 = new Bundle();
                            bundle2.putSerializable("objectUser", user);
                            intent_reply.putExtras(bundle2);
                            databaseReference.removeEventListener(this);
                            setResult(Activity.RESULT_OK, intent_reply);
                            finish();
                             databaseReference.removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
               // dialogProgress.show();
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        User user= snapshot.getValue(User.class);
//                        if (user!= null){
//                            //Update profile
////                            databaseReference.child("fullname").setValue(content_name);
////                            databaseReference.child("phone").setValue(content_phone);
//                        //    dialogProgress.dismiss();
//                            Toast.makeText(profile.this, "Update Success", Toast.LENGTH_SHORT).show();
//                            //String name_up= String.valueOf(databaseReference.child("fullname").setValue(content_name));
//                          //  String phone_up= String.valueOf(databaseReference.child("phone").setValue(content_phone));
//                            Intent intent_reply=new Intent();
//                            Bundle bundle2 = new Bundle();
//                            bundle2.putSerializable("objectUser", user);
//                            intent_reply.putExtras(bundle2);
//                            databaseReference.removeEventListener(this);
//                            setResult(Activity.RESULT_OK, intent_reply);
//                           finish();
//                           // databaseReference.removeEventListener(this);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }
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

    //Hàm thay đổi avtar
    private void set_ava(){
        edit_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(profile.this, R.anim.btn_click_anim));
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               launcherActivityAva.launch(intent);
               // startActivityForResult(intent,300);



            }
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
                      //  Uri photo = result.getData();
                        uploadImage(photo);
                       //  Glide.with(profile.this).load(photo).into(avatar);





                    }
                }
            });
    public void uploadImage(Uri photo) {
        FirebaseUser auth= FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        User user= (User) bundle.get("objectUser");
        String id_user= user.getId();
        //Tạo tên file hình ngẫu nhiên
        String filename = UUID.randomUUID().toString();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("avatar").child(filename);

        //Upload ảnh lên firebase storage
        dialogProgress.show();
        storageReference.putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialogProgress.dismiss();
                        String cover = uri.toString();
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference=database.getReference("Users").child(id_user);
                        databaseReference.child("avatar").setValue(cover);
                        Glide.with(profile.this).load(uri).into(avatar);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void uploadImage(Uri photo) {
//        //Tạo tên file hình ngẫu nhiên
//        String filename = UUID.randomUUID().toString();
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("avatar").child(filename);
//
//        //Upload ảnh lên firebase storage
//
//        storageReference.putFile(photo).addOnSuccessListener(taskSnapshot -> {
//            //get url
//            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                FirebaseUser auth= FirebaseAuth.getInstance().getCurrentUser();
//                String cover = uri.toString();
//                Bundle bundle = getIntent().getExtras();
//                if (bundle!= null){
//                    User user= (User) bundle.get("objectUser");
//                    String id_user= user.getId();
//                    FirebaseDatabase database=FirebaseDatabase.getInstance();
//                    DatabaseReference databaseReference=database.getReference("Users").child(id_user);
////                    databaseReference.child("avatar").setValue(cover);
////                    Toast.makeText(profile.this, "Change Ava Successful", Toast.LENGTH_SHORT).show();
//                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            databaseReference.child("avatar").setValue(cover);
//                            Toast.makeText(profile.this, "Change Ava Successful", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//
//
//            });
//        }).addOnFailureListener(e -> {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        });
//
//    }

}
