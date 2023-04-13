package com.example.readingbooks_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.activity.login;
import com.example.readingbooks_final.database.User;
import com.example.readingbooks_final.fragment.Account;
import com.example.readingbooks_final.fragment.Books;
import com.example.readingbooks_final.fragment.Category;
import com.example.readingbooks_final.fragment.Home;
import com.example.readingbooks_final.fragment.Write;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;
import java.util.Objects;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Khai báo biến
    private DrawerLayout drawerLayout;
    private AnimatedBottomBar navigationView;
    private NavigationView nav_top;
    private ImageView image_ava;
    private TextView name_acc, email_acc;
    private Toolbar toolbar;
    private static final int Home_Frag=1;
    private static final int Books_Frag=2;
    private static final int Write_Frag=3;
    private static final int Category_Frag=4;
    private static final int Profile_Frag=5;

    private final Account account = new Account();

    private int current =Home_Frag;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Object signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();


        //Gọi hàm
        AnhXaDoiTuong();
        createIconToggleOnToolbar();
        chooseHomeScreenDefault();
        workOnToolBar();
        showUser();
        eventHandlingNavigationBottom();
    }
    //Ánh xạ các đối tượng
    private void AnhXaDoiTuong(){
        navigationView = findViewById(R.id.navbar);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_top = findViewById(R.id.nav_top);
        image_ava=nav_top.getHeaderView(0).findViewById(R.id.img_ava);
        name_acc=nav_top.getHeaderView(0).findViewById(R.id.name_acc);
        email_acc=nav_top.getHeaderView(0).findViewById(R.id.email_acc);
        gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
    }
    //Tạo icon toggle trên toolbar
    private void createIconToggleOnToolbar(){
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_top.setNavigationItemSelectedListener(MainActivity.this);

    }
    //Chọn Màn hình Home là mặc định lúc mở lên
    private void chooseHomeScreenDefault(){
        navigationView.selectTabById(R.id.action_home,true);
        nav_top.setCheckedItem(R.id.action_home);
        displayView(0);
    }

    private void workOnToolBar(){
        //set đồng bộ giữa nav top và nav bot
        navigationView.selectTabById(R.id.action_home,true);
        //không cho hiện label trên thanh bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        //getSupportActionBar().isHideOnContentScrollEnabled();


    }



    //Xử lý sự kiện của thanh navigation bottom
    //Biến navigationView là của thanh bar bottom

    private void eventHandlingNavigationBottom(){
        navigationView.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab item) {

                switch (item.getId()){
                    case (R.id.action_home):
                        Open_Home();
                        //Đồng bộ hóa
                        nav_top.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case (R.id.action_books):
                        Open_Books();
                        nav_top.getMenu().findItem(R.id.action_books).setChecked(true);


                        break;
                    case (R.id.action_write):
                        Open_Write();
                        nav_top.getMenu().findItem(R.id.action_write).setChecked(true);
                        break;
                    case (R.id.action_category):

                        Open_Category();
                        nav_top.getMenu().findItem(R.id.action_category).setChecked(true);


                        break;
                    case (R.id.action_account):
                        Open_Account();
                        nav_top.getMenu().findItem(R.id.action_account).setChecked(true);

                }

            }

            @Override
            public void onTabReselected(int lastIndex, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });

    }
    //Tạo hàm mở Fragment
    private void Open_Home(){
        //Nếu màn hình hiện tại không phải màn hình Home
        if(current!=Home_Frag){
            //Mở Fragment Home lên
            displayView(0);
            current=Home_Frag;
        }
    }

    public void Open_Books(){
        //Nếu màn hình hiện tại không phải màn hình Books
        if(current!=Books_Frag){
            //Mở Fragment Books lên
            displayView(1);
            current=Books_Frag;
        }
    }

    public void Open_Write(){
        //Nếu màn hình hiện tại không phải màn hình Write
        if(current!=Write_Frag){
            //Mở Fragment Write lên
            displayView(2);
            current=Write_Frag;
        }
    }

    public void Open_Category(){
        //Nếu màn hình hiện tại không phải màn hình Notifications
        if(current!=Category_Frag){
            //Mở Fragment Notifications lên
            displayView(3);
            current=Category_Frag;
        }
    }

    public void Open_Account(){
        if(current!=Profile_Frag){
            //Mở Fragment Account lên
            displayView(4);
            current=Profile_Frag;
        }

    }


    public void LogOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent =new Intent(MainActivity.this, login.class);
        startActivity(intent);
        finishAffinity();
    }

    // Tạo hàm hiển thị user

    public void showUser() {
        //Đăng nhập bằng tài khoản
        FirebaseAuth user = FirebaseAuth.getInstance();
        //Lấy thông tin tài khoản
        String userId = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("Users").child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user_cur = snapshot.getValue(User.class);

                if (user_cur!= null) {
                    String fullname = user_cur.getFullname();
                    String email = user_cur.getEmail();
                    //String avatarBase64= user_cur.getAvatar();
                    String avatarBase64= String.valueOf(user_cur.getAvatar());
                   // String avatarBase64 = snapshot.child("avatar").getValue(String.class);
                    name_acc.setText(fullname);
                    email_acc.setText(email);

                    if (avatarBase64.isEmpty()){
                        Glide.with(MainActivity.this).load(R.drawable.user_ava).into(image_ava);
                    }
                    if (!avatarBase64.isEmpty())
                    {
                        String avatar = snapshot.child("avatar").getValue(String.class);
                        if(avatar != null) {
                            if(!avatar.isEmpty()) {
                                byte [] byteArray = new byte[0];
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    byteArray = Base64.getDecoder().decode(avatarBase64);
//                                }
                                byteArray = Base64.getDecoder().decode(avatarBase64);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                Glide.with(MainActivity.this).load(avatarBase64).error(bitmap).into(image_ava);

                            }
                        }
                    }
                   // Glide.with(MainActivity.this).load(avatarBase64).error(R.drawable.user_ava).into(image_ava);
//                    if (avatarBase64 == null){
//                        Glide.with(MainActivity.this).load(R.drawable.user_ava).into(image_ava);
//                    }
//                    if (avatarBase64!=null){
//                        //set ava
//                        String avatar = snapshot.child("avatar").getValue(String.class);
//                        if(avatar != null) {
//                            if(!avatar.isEmpty()) {
//                                byte [] byteArray = new byte[0];
//                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                                    byteArray = Base64.getDecoder().decode(avatar);
//                                }
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                                Glide.with(MainActivity.this).load(avatarBase64).error(bitmap).into(image_ava);
//
//                            }
//                        }
//                    }

                    //set ava

//                    if (avatarBase64 != null) {
//                        if (!avatarBase64.isEmpty()) {
//                            byte[] byteArray = new byte[0];
////                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////                                byteArray = Base64.getDecoder().decode(avatarBase64);
////                            }
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                            Glide.with(MainActivity.this).load(bitmap).error(R.drawable.user_ava).into(image_ava);
//                        }
//
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });



    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id_2 = item.getItemId();
        switch (id_2) {

            case (R.id.action_home):
                Open_Home();
                //Đồng bộ navigation bottom với toolbar
                navigationView.selectTabById(R.id.action_home,true);
                //Đóng toolbar sau khi chọn trang
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case (R.id.action_books):

                Open_Books();
                navigationView.selectTabById(R.id.action_books,true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case (R.id.action_write):
                Open_Write();
                navigationView.selectTabById(R.id.action_write,true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case (R.id.action_category):
                Open_Category();
                navigationView.selectTabById(R.id.action_category,true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case (R.id.action_account):
                Open_Account();
                navigationView.selectTabById(R.id.action_account,true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case (R.id.action_logout):
                LogOut();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    //Thao tác nút back
    // Khi màn hình navigation đang mở mà click nút back thì nó sẽ đóng màn hình navigation
    //Còn nếu màn hình navigation đang đóng thì sẽ thực hiện chức năng của nút back
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            finish();
            super.onBackPressed();
            exitApp();

        }
    }
    private void replaceFragment(Fragment fragment, int position) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_tool,fragment, fragment.getClass().getName());
        transaction.commit();
    }
    public void displayView(int position){
        switch (position){
            case 0:
                replaceFragment(new Home(), position);
                break;
            case 1:
                replaceFragment(new Books(), position);
                break;
            case 2:
                replaceFragment(new Write(), position);
                break;
            case 3:
                replaceFragment(new Category(), position);
                break;
            case 4:
                replaceFragment(account, position);
                break;

        }
    }

    private void exitApp(){
        Intent exit= new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        startActivity(exit);
    }
}