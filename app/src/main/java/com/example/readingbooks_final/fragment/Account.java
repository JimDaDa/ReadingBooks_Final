package com.example.readingbooks_final.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.MainActivity;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.profile;
import com.example.readingbooks_final.database.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;


public class Account extends Fragment {
    private View view;
    private ImageView image_ava;
    private TextView name_acc, email_acc,phone;

    private ImageButton edit_profile;
    public static final String NAME_REPLY = "name";
    public static final String EMAIL_REPLY = "email";
    public static final String PHONE_REPLY = "phone";
    public static final String PHOTO_REPLY = "photo";
    public static final String PHOTOLOG_REPLY = "photolog";
    private Uri photo;
    private MainActivity mainActivity;
    private Bitmap avatarImage;


    private GoogleSignInAccount user_gg;

    private FirebaseAuth user;

    private DatabaseReference ref;

    public Account() {
        // Required empty public constructor
    }


    // Nhận dữ liệu sau khi chỉnh sửa ở trang profile về để hiển thị
    ActivityResultLauncher<Intent> launcherActivityInfo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();

                        if (intent==null){
                        }
                        else {
                            photo = intent.getData();


                            image_ava.setImageURI(photo);


                            String name_reply = intent.getStringExtra(NAME_REPLY);
                            String phone_reply = intent.getStringExtra(PHONE_REPLY);
                            String email_reply = intent.getStringExtra(EMAIL_REPLY);

                            name_acc.setText(name_reply);
                            phone.setText(phone_reply);
                            email_acc.setText(email_reply);
                        }



                    }
                }


            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_account, container, false);

        AnhXa();
        edit_pro5();
        showUser();
        Update();
        return view;
    }

    private void AnhXa(){
        image_ava= view.findViewById(R.id.avatar);
        name_acc=view.findViewById(R.id.fullname);
        email_acc=view.findViewById(R.id.acc_email);
        edit_profile=view.findViewById(R.id.edit_profile);
        phone=view.findViewById(R.id.phone);
        mainActivity=(MainActivity) getActivity();

    }
    private void edit_pro5(){
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi click vào edit pro5 thì sẽ lấy dữ liệu đang hiển thị đóng gói lại chuyển sang trang profile

                // Intent intent= new Intent(view.getContext(), profile.class);
                Intent intent = new Intent(getContext(), profile.class);
                //Lấy các thông tin từ các textview
                String content_name= name_acc.getText().toString().trim();
                String content_email= email_acc.getText().toString().trim();
                String content_phone= phone.getText().toString().trim();

                Bundle bundle_recieve= new Bundle();

                bundle_recieve.putString(NAME_REPLY, content_name);
                bundle_recieve.putString(EMAIL_REPLY,content_email);
                bundle_recieve.putString(PHONE_REPLY,content_phone);
                // Lấy avatar
                image_ava.setDrawingCacheEnabled(true);
                Bitmap bitmap = image_ava.getDrawingCache();
                //Đóng gói lại
                intent.putExtra(PHOTO_REPLY,bitmap);
                intent.putExtras(bundle_recieve);
                //startActivityForResult(intent,100);
                if (launcherActivityInfo != null){
                    launcherActivityInfo.launch(intent);
                }
                else {
                    return;
                }




            }
        });
    }


    public void showUser() {
        user= FirebaseAuth.getInstance();
        String userId = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        assert userId != null;
        DatabaseReference ref=database.getReference("Users").child(userId);
        user_gg = GoogleSignIn.getLastSignedInAccount(requireContext());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Lấy thông tin tài khoản đăng nhập bằng gg

                Uri photoUrl = user_gg.getPhotoUrl();
                String name_gg=user_gg.getDisplayName();
                String email_gg=user_gg.getEmail();


                //Lấy thông tin tài khoản khi đăng kí trong ứng dụng
                User user_data = snapshot.getValue(User.class);
                String logfullname = snapshot.child("fullname").getValue(String.class);
                String logemail = snapshot.child("email").getValue(String.class);

                if (logfullname!= null){
                    name_acc.setVisibility(View.VISIBLE);
                    name_acc.setText(logfullname);
                }

                else if (name_gg!= null){
                    name_acc.setText(name_gg);
                    Glide.with(Account.this).load(photoUrl).error(R.drawable.user_ava).into(image_ava);
                }
                else {
                    name_acc.setVisibility(View.GONE);
                }
                //set ava
                String avatarBase64 = snapshot.child("avatar_base64").getValue(String.class);
                if(avatarBase64 != null) {
                    if(!avatarBase64.isEmpty()) {
                        byte [] byteArray = new byte[0];
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            byteArray = Base64.getDecoder().decode(avatarBase64);
                        }
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        Glide.with(Account.this).load(bitmap).error(R.drawable.user_ava).into(image_ava);

                    }
                }

                if (logemail != null){
                    email_acc.setVisibility(View.VISIBLE);
                    email_acc.setText(logemail);
//
                }

                else if (email_gg!= null){
                    email_acc.setVisibility(View.VISIBLE);
                    email_acc.setText(email_gg);
                }
                else {
                    email_acc.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void Update(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        // Set tên và email lên thanh menu
        mainActivity.showUser();


    }

}