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

    private ImageButton edit_profile;
    private TextView name_acc, email_acc,phone;


    public static final String NAME_REPLY = "name";
    public static final String EMAIL_REPLY = "email";
    public static final String PHONE_REPLY = "phone";
    public static final String PHOTO_REPLY = "photo";

    private MainActivity mainActivity;
    private Bitmap avatarImage;




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
                            return;
                        }
                        else {
                      //  Uri photo = intent.getData();



                            String name_reply = intent.getStringExtra(NAME_REPLY);
                            String phone_reply = intent.getStringExtra(PHONE_REPLY);
                            String email_reply = intent.getStringExtra(EMAIL_REPLY);
                            Uri photo= intent.getParcelableExtra(PHOTO_REPLY);

                            name_acc.setText(name_reply);
                            phone.setText(phone_reply);
                            email_acc.setText(email_reply);
                            image_ava.setImageURI(photo);
                            Glide.with(Account.this).load(photo).into(image_ava);
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

                 Intent intent= new Intent(view.getContext(), profile.class);
               // Intent intent = new Intent(getContext(), profile.class);
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
                //intent.putExtra(PHOTO_REPLY,bitmap);
                intent.putExtras(bundle_recieve);
                //startActivityForResult(intent,100);
                launcherActivityInfo.launch(intent);




            }
        });
    }


    public void showUser() {

        FirebaseAuth  user= FirebaseAuth.getInstance();
        String userId = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
       // assert userId != null;
        assert userId != null;
        DatabaseReference ref=database.getReference("Users").child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user_cur = snapshot.getValue(User.class);

                if (user_cur!= null) {
                    String fullname = user_cur.getFullname();
                    String email = user_cur.getEmail();
                    String phone_user = user_cur.getPhone();
                    String avatarBase64= String.valueOf(user_cur.getAvatar());

                    name_acc.setText(fullname);
                    email_acc.setText(email);
                    phone.setText(phone_user);
//                    Glide.with(Account.this).load(avatarBase64).into(image_ava);
//                    System.out.println(avatarBase64);
                    if (avatarBase64.isEmpty()){
                        Glide.with(Account.this).load(R.drawable.user_ava).into(image_ava);
                    }
                    if (!avatarBase64.isEmpty())
                    {
                        String avatar = snapshot.child("avatar").getValue(String.class);
                        if(avatar != null) {
                            if(!avatar.isEmpty()) {
                                byte [] byteArray = new byte[0];
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    byteArray = Base64.getDecoder().decode(avatar);
                                }
                                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                Glide.with(Account.this).load(avatarBase64).error(bitmap).into(image_ava);

                            }
                        }
                    }
//                    if (avatarBase64 == null) {
//
//                    }

//                        if (avatarBase64!=null){
//                        //set ava
//                        String avatar = snapshot.child("avatar").getValue(String.class);
//                        if(avatar != null) {
//                            if(!avatar.isEmpty()) {
//                                byte [] byteArray = new byte[0];
//                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                                    byteArray = Base64.getDecoder().decode(avatar);
//                                }
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                                Glide.with(Account.this).load(avatarBase64).error(bitmap).into(image_ava);
//
//                            }
//                        }
//                    }



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