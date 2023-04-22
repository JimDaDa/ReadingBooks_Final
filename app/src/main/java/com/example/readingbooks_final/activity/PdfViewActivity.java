package com.example.readingbooks_final.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.readingbooks_final.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfView = findViewById(R.id.pdfView);

        int pos = getIntent().getIntExtra("position", 0);
        if(pos == 0){
            pdfView.fromAsset("set1.pdf").load();
        }
        else if(pos == 1){
            pdfView.fromAsset("set2.pdf").load();
        }
        else if(pos == 2){
            Toast.makeText(this, "chương 3", Toast.LENGTH_SHORT).show();
        }
        else if(pos == 3){
            Toast.makeText(this, "chương 4", Toast.LENGTH_SHORT).show();
        }
    }
}