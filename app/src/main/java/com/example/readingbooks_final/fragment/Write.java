package com.example.readingbooks_final.fragment;

import static com.example.readingbooks_final.activity.Write_tab2.AUTHOR;
import static com.example.readingbooks_final.activity.Write_tab2.CATEGORY;
import static com.example.readingbooks_final.activity.Write_tab2.COVER;
import static com.example.readingbooks_final.activity.Write_tab2.DESCRIPTION;
import static com.example.readingbooks_final.activity.Write_tab2.STATUS;
import static com.example.readingbooks_final.activity.Write_tab2.TITLE;
import static com.example.readingbooks_final.activity.Write_tab4.CONTENT_CHAPTER;
import static com.example.readingbooks_final.activity.Write_tab4.TITLE_CHAPTER;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.Write_tab2;
import com.example.readingbooks_final.activity.Write_tab3;


public class Write extends Fragment {

    private TextView create_story, edit_story;

    private View view;


    public Write() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_write, container, false);
        initView();
        createStory();
        editStory();
    return view;
    }

    private void initView(){
        create_story= view.findViewById(R.id.create_story);
        edit_story=view.findViewById(R.id.edit_story);

    }

    private void createStory(){
        create_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Write_tab2.class);
               // startActivity(intent);
                startActivityForResult.launch(intent);



            }
        });
    }

    private void editStory(){
        edit_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Hiển thị trang chứa truyện vừa tạo

            }
        });
    }

    final ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//
                    if(result.getResultCode() ==Activity.RESULT_OK) {

                        Intent intent = result.getData();
                        if (intent==null){
                            return;
                        }
                       // String newData = intent.getStringExtra("data back");

                        String title_reply = intent.getStringExtra(TITLE);
                        String author_reply = intent.getStringExtra(AUTHOR);
                        String category_reply = intent.getStringExtra(CATEGORY);
                        String status_reply = intent.getStringExtra(STATUS);
                        String description_reply = intent.getStringExtra(DESCRIPTION);
                        String cover_reply = intent.getStringExtra(COVER);
//                        String title_content = intent.getStringExtra(TITLE_CHAPTER);
//                        String chap_content = intent.getStringExtra(CONTENT_CHAPTER);





                    }
                }
            });

//    private void replaceFrag(){
//        Write write1= new Write();
//
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.content_tool,write);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}