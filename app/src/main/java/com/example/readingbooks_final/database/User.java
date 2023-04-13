package com.example.readingbooks_final.database;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String fullname;
    private String email;
    private String password;
    private String phone;
    
    private String avatar;


    public User(){}

    public User(String id, String fullname, String email, String password, String phone, String avatar) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.avatar=avatar;
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
}
