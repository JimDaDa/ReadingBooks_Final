package com.example.readingbooks_final.database;

import java.util.HashMap;
import java.util.Map;

public class Books_data {

    private String id, id_user;
    private String title, authors,category, description, imgUrl;
    private int pageCount, drawableRes;
    private float rating;
    private String status;


    public Books_data(){}
    public Books_data(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public Books_data(String id, String id_user, String title, String authors, String category, String description, String imgUrl, int pageCount, int drawableRes, float rating, String status) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.description = description;
        this.imgUrl = imgUrl;
        this.pageCount = pageCount;
        this.drawableRes = drawableRes;
        this.rating = rating;
        this.status = status;
    }

//    public Books_data(String id, String title, String authors, String category, String description, String imgUrl, int pageCount, int drawableRes, float rating, boolean status) {
//        this.id = id;
//        this.title = title;
//        this.authors = authors;
//        this.category = category;
//        this.description = description;
//        this.imgUrl = imgUrl;
//        this.pageCount = pageCount;
//        this.drawableRes = drawableRes;
//        this.rating = rating;
//        this.status = status;
//    }
//Constructor add books
//    public Books_data(String id_books, String id_user, String title, String author, String categoryy, String statuss, String description) {
//    }

    public Books_data(String id_books, String id_user, String title, String author, String categoryy, String statuss, String description, String photo1) {
        this.id = id_books;
        this.id_user = id_user;
        this.title = title;
        this.authors = author;
        this.category = categoryy;
        this.description = description;
        this.imgUrl = photo1;
        this.status = statuss;
    }

    public Books_data(String cover) {
        this.imgUrl = cover;
    }

//    public Books_data(String id_books, String id_user, String title, String author, String categoryy, String statuss, String description) {
//        this.id = id_books;
//        this.id_user = id_user;
//        this.title = title;
//        this.authors = author;
//        this.category = categoryy;
//        this.description = description;
//        this.status = statuss;
//    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap();
        result.put("id",id);
        result.put("id_user",id_user);
        result.put("title",title);
        result.put("authors",authors);
        result.put("category",category);
        result.put("imgUrl",imgUrl);
        result.put("drawableRes", drawableRes);
        result.put("rating", rating);
        result.put("pageCount", pageCount);
        result.put("status", status);
        return result ;
    }
}
