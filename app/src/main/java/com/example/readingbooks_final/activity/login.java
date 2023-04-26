package com.example.readingbooks_final.activity;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingbooks_final.MainActivity;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.admin.DashboardAdmin;
import com.example.readingbooks_final.database.User;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login extends AppCompatActivity {

    // Khai báo biến
    private EditText email;
    private  EditText password;
    private Button btnLogin;
    private TextView layout_signUp;

    private TextView forgot;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finishAffinity();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Gọi các hàm
        AnhXaDoiTuong();
        getLayout_signUp();
        setBtnLogin();
        Forgot_pass();
    }

    private void AnhXaDoiTuong(){
        // Ánh xạ các đối tượng

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        layout_signUp=findViewById(R.id.layout_signup);
        forgot=findViewById(R.id.forgot_pass);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

    }



    //Hàm mở trang Quên mật khẩu
    private void Forgot_pass(){
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(login.this, forget_pass.class);
                startActivity(intent);
            }
        });
    }
    //Hàm mở trang đăng ký
    private void getLayout_signUp() {
        layout_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(login.this,sign_up.class);
                startActivity(intent);
            }
        });
    }

    //Login bằng tài khoản ứng dụng
    //Xử lý sự kiện khi click vào nút button Login
    private void setBtnLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    //Hàm kiểm tra thông tin đăng nhập
    private void Login(){

        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();
        FirebaseAuth auth=FirebaseAuth.getInstance();
       // String id_user = auth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
       // DatabaseReference reference = database.getReference().child("Users").child(id_user);



        if(user_email.isEmpty() || user_password.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Các thông tin không được bỏ trống", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.show();
            auth.signInWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful())
                    {
                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get Post object and use the values to update the UI
                                    User current_user = dataSnapshot.getValue(User.class);

                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(login.this, "Login success", Toast.LENGTH_SHORT).show();
                                    finishAffinity();





                            }
                            //
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                            }
                        };
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(postListener);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



}