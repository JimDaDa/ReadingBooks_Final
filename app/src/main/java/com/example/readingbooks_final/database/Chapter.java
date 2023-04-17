package com.example.readingbooks_final.database;

import java.util.HashMap;
import java.util.Map;

public class Chapter {
    private String id_book, id_user, id_chapter, title_chap, content_chap;

    public Chapter(){

    }

    public Chapter(String id_book, String id_user, String id_chapter, String title_chap, String content_chap) {
        this.id_book = id_book;
        this.id_user = id_user;
        this.id_chapter = id_chapter;
        this.title_chap = title_chap;
        this.content_chap = content_chap;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_chapter() {
        return id_chapter;
    }

    public void setId_chapter(String id_chapter) {
        this.id_chapter = id_chapter;
    }

    public String getTitle_chap() {
        return title_chap;
    }

    public void setTitle_chap(String title_chap) {
        this.title_chap = title_chap;
    }

    public String getContent_chap() {
        return content_chap;
    }

    public void setContent_chap(String content_chap) {
        this.content_chap = content_chap;
    }
    public Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap();
        result.put("id_book",id_book);
        result.put("id_user",id_user);
        result.put("id_chapter",id_chapter);
        result.put("title_chap",title_chap);
        result.put("content_chapter",content_chap);

        return result ;
    }
}
