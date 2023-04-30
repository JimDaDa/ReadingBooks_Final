package com.example.readingbooks_final.database;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.PhantomReference;

public class SaveBookCurrent {
    public static final  String BOOK_MARK = "BOOK_MARK";
    private Context context;

    public SaveBookCurrent(Context context) {
        this.context = context;
    }
    public void putIntValue (String key, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(BOOK_MARK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();

        editor.putInt(key, value);

        editor.apply();
    }
    public int getIntValue (String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(BOOK_MARK, Context.MODE_PRIVATE);
      return sharedPreferences.getInt(key,0);
    }

}
