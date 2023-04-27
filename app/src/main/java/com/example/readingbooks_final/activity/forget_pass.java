package com.example.readingbooks_final.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.custom.CustomDialogProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_pass extends AppCompatActivity {

    private EditText type_email;
    private Button btnReset;
    private FirebaseAuth auth;
    private CustomDialogProgress customDialogProgress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        AnhXa();
        resetPass();

    }
    private void AnhXa(){
        type_email=findViewById(R.id.type_email);
        btnReset=findViewById(R.id.btnReset);
        auth = FirebaseAuth.getInstance();
       customDialogProgress = new CustomDialogProgress(forget_pass.this);
    }

    private void resetPass(){
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(forget_pass.this, R.anim.btn_click_anim));
                String email_forgot= type_email.getText().toString().trim();
                if (TextUtils.isEmpty(email_forgot)){
                    type_email.setError("Please enter your email");

                }else {
                    customDialogProgress.show();
                    auth.sendPasswordResetEmail(email_forgot).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            customDialogProgress.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(forget_pass.this,"Password reset email sent!",Toast.LENGTH_LONG).show();

                                startActivity(new Intent(forget_pass.this, login.class));
                            }else{
                                Toast.makeText(forget_pass.this,"Email not registered!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}