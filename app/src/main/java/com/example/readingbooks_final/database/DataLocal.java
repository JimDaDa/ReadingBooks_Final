package com.example.readingbooks_final.database;

import android.content.Context;

public class DataLocal {
    private static DataLocal instance;
    private SaveBookCurrent myBookMark;

    private static final String PAGE_CURR = "PAGE_CURR";

    public static void init(Context context) {
        instance = new DataLocal();
        instance.myBookMark = new SaveBookCurrent(context);
    }

    public static DataLocal getInstance() {
        if (instance == null){
            instance= new DataLocal();
        }
        return  instance;
    }
    public static void setMyBookMark(int pageCurr){
        DataLocal.getInstance().myBookMark.putIntValue(PAGE_CURR,pageCurr);

    }

    public static int getMyBookMark(){
       return DataLocal.getInstance().myBookMark.getIntValue(PAGE_CURR);

    }



}
