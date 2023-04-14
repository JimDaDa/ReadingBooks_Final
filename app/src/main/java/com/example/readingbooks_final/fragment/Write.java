package com.example.readingbooks_final.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.readingbooks_final.R;
import com.example.readingbooks_final.activity.Write_tab2;


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
                startActivity(intent);

            }
        });
    }

    private void editStory(){
        edit_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}