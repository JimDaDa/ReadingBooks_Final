package com.example.readingbooks_final.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.readingbooks_final.R;

public class rate_book extends Dialog {

    private float userRate = 0;

    public rate_book(@NonNull Context context)
    {
        super(context);
    }

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.rate_book);

        final AppCompatButton rateNow = findViewById(R.id.rateNow);
        final AppCompatButton rateLater = findViewById(R.id.rateLater);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final ImageView rateImg = findViewById(R.id.rateImg);

        rateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if(rating <= 1)
                {
                    rateImg.setImageResource(R.drawable.rate1);
                }
                else if(rating <= 2)
                {
                    rateImg.setImageResource(R.drawable.rate2);
                }
                else if(rating <= 3)
                {
                    rateImg.setImageResource(R.drawable.rate3);
                }
                else if(rating <= 4)
                {
                    rateImg.setImageResource(R.drawable.rate4);
                }
                else if(rating <= 5)
                {
                    rateImg.setImageResource(R.drawable.rate5);
                }

                animateImg(rateImg);
                userRate = rating;
            }
        });

    }

    private void animateImg(ImageView rateImg)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        rateImg.startAnimation(scaleAnimation);

    }
}
