package com.example.readingbooks_final.database;

import android.net.Uri;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String id, id_like,id_vote, id_book;
    private String fullname,email,password, phone, avatar, likedStatus;


    





    public User(){}

    public User(String id, String fullname, String email, String password, String phone, String avatar) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.avatar=avatar;
    }

    public User(String id, String id_like, String id_vote, String id_book, String fullname, String email, String password, String phone, String avatar) {
        this.id = id;
        this.id_like = id_like;
        this.id_vote = id_vote;
        this.id_book = id_book;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
    }

    public User(String id, String fullname, String user_email, String user_ava) {
        this.id = id;
        this.fullname = fullname;
        this.email = user_email;
        this.avatar=user_ava;
    }

    public User(String id, String fullname, String user_email, String password, String user_phone) {
        this.id = id;
        this.fullname = fullname;
        this.email = user_email;
        this.password = password;
        this.phone = user_phone;

    }

    public User(String id_like, String books_id, String likedStatus) {
        this.id_like =id_like;
        this.id_book = books_id;
        this.likedStatus = likedStatus;
    }

    public String getLikedStatus() {
        return likedStatus;
    }

    public void setLikedStatus(String likedStatus) {
        this.likedStatus = likedStatus;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public String getId_like() {
        return id_like;
    }

    public void setId_like(String id_like) {
        this.id_like = id_like;
    }

    public String getId_vote() {
        return id_vote;
    }

    public void setId_vote(String id_vote) {
        this.id_vote = id_vote;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap();
        result.put("id",id);
        result.put("fullname",fullname);
        result.put("email",email);
        result.put("password",password);
        result.put("phone",phone);
        result.put("avatar_base64", avatar);


        return result ;
    }

    public Map<String, Object> BookLike(){
        HashMap<String,Object> result=new HashMap();
        result.put("id_like",id_like);
        result.put("id_book",id_book);
        result.put("likedStatus",likedStatus);

        return result ;
    }
}
