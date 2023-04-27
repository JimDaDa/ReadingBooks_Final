package com.example.readingbooks_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingbooks_final.MainActivity;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.custom.CustomDialogProgress;
import com.example.readingbooks_final.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {
    //Khai báo

    private EditText name, email, phone, pass, confirm_pass;

    private Button btnSignUp;
    private TextView layout_login;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private CustomDialogProgress dialogProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhxaDoiTuong();
        clickSignUp();
        click_Login();
    }

    private void AnhxaDoiTuong() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        confirm_pass = findViewById(R.id.confirm_pass);
        btnSignUp=findViewById(R.id.btnSignUp);
        layout_login=findViewById(R.id.layout_login);
        progressDialog=new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dialogProgress = new CustomDialogProgress(sign_up.this);
    }
    private void click_Login(){
        layout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(sign_up.this, R.anim.btn_click_anim));
                Intent intent =new Intent(sign_up.this,login.class);
                startActivity(intent);
            }
        });
    }
    private void clickSignUp() {

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(sign_up.this, R.anim.btn_click_anim));
                dangKyTaiKhoan();
            }


        });

    }

    private void dangKyTaiKhoan() {
        String fullname = name.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String user_phone = phone.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String confirm = confirm_pass.getText().toString().trim();

        // Đặt điều kiện không được bỏ trống các ô
        if (user_email.isEmpty() || password.isEmpty() || fullname.isEmpty() || user_phone.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Các thông tin không được bỏ trống", Toast.LENGTH_SHORT).show();
        }
        //Mật khẩu phải nhiều hơn 7 kí tự
        else if (password.length() < 7)  {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải nhiều hơn 7 kí tự", Toast.LENGTH_SHORT).show();
        }
        //Mật khẩu nhập lại không trùng khớp
        else if (!confirm.equals(password)) {
            Toast.makeText(getApplicationContext(), "Nhập lại mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }
        else {
         //   progressDialog.show();
            dialogProgress.show();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.createUserWithEmailAndPassword(user_email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  //  progressDialog.dismiss();
                    dialogProgress.dismiss();
                    if (task.isSuccessful()) {
                        // Đăng ký thành công, ứng dụng sẽ gửi email xác thực, sau đó chuyển về trang đăng nhập
                        sendEmailVertification();

                    } else {
                        // Nếu các thông tin đã tồn tại, ứng dụng sẽ báo lỗi
                        Toast.makeText(sign_up.this, "Tạo tài khoản thất bại.", Toast.LENGTH_SHORT).show();

                    }
                }
                // Hàm gửi email xác thực
                private void sendEmailVertification() {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {



                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Xác thực qua Email đã gửi, vui lòng xác thực và đăng nhập", Toast.LENGTH_SHORT).show();

                                    addUser();

                                    auth.signOut();
                                    finish();
                                    startActivity(new Intent(sign_up.this, login.class));
                                }

                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Không thể gửi xác thực tài khoản qua Email", Toast.LENGTH_SHORT).show();
                    }
                }

                private void addUser(){
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference=database.getReference("Users");

                    //Lấy id là UID trên firebase
                    String  id=auth.getCurrentUser().getUid();

                    String fullname = name.getText().toString().trim();
                    String user_email = email.getText().toString().trim();
                    String user_phone = phone.getText().toString().trim();
                    String password = pass.getText().toString().trim();


                    User user = new User(id,fullname, user_email, password, user_phone);
                    databaseReference.child(id).setValue(user);
                }
            });
        }

    }
}