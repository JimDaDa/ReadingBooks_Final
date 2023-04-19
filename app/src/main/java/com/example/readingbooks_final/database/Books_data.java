package com.example.readingbooks_final.database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Books_data implements Serializable {

    private String id, id_user, id_chapter;
    private String title, authors,category, description, imgUrl, status, publishStatus, publishDate, titleChapter, contentChapter;
    private int pageCount;
    private float rating;



    public Books_data(){}

    public Books_data(String id, String id_user, String id_chapter, String title, String authors, String category, String description, String imgUrl, String status, String publishStatus, String publishDate, String titleChapter, String contentChapter, int pageCount, float rating) {
        this.id = id;
        this.id_user = id_user;
        this.id_chapter = id_chapter;
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.description = description;
        this.imgUrl = imgUrl;
        this.status = status;
        this.publishStatus = publishStatus;
        this.publishDate = publishDate;
        this.titleChapter = titleChapter;
        this.contentChapter = contentChapter;
        this.pageCount = pageCount;
        this.rating = rating;
    }


    public String getId_chapter() {
        return id_chapter;
    }

    public void setId_chapter(String id_chapter) {
        this.id_chapter = id_chapter;
    }

    public String getTitleChapter() {
        return titleChapter;
    }

    public void setTitleChapter(String titleChapter) {
        this.titleChapter = titleChapter;
    }

    public String getContentChapter() {
        return contentChapter;
    }

    public void setContentChapter(String contentChapter) {
        this.contentChapter = contentChapter;
    }

    public Books_data(int book1) {

    }

    public String getStatus() {
        return status;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Books_data(String id_books, String id_user, String cover, String title, String author, String categoryy, String statuss, String description) {
        this.id = id_books;
        this.id_user = id_user;
        this.imgUrl=cover;
        this.title = title;
        this.authors = author;
        this.category = categoryy;
        this.status = statuss;
        this.description = description;

    }

    public Books_data(String id_books, String id_user, String cover) {
        this.id = id_books;
        this.id_user = id_user;
        this.imgUrl=cover;



    }

    public Books_data(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public Books_data(String cover) {
        this.imgUrl = cover;
    }

    public Books_data(String title, String author, String categoryy, String statuss, String description) {

        this.title = title;
        this.authors = author;
        this.category = categoryy;
        this.status = statuss;
        this.description = description;

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


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap();
        result.put("id",id);
        result.put("id_user",id_user);
        result.put("id_chapter",  id_chapter);
        result.put("title",title);
        result.put("authors",authors);
        result.put("category",category);
        result.put("imgUrl",imgUrl);
        result.put("rating", rating);
        result.put("pageCount", pageCount);
        result.put("status", status);
        result.put("publishStatus", publishStatus);
        result.put("publishDate", publishDate);
        result.put("title_Chapter", titleChapter);
        result.put("content_chapter",  contentChapter);
        return result ;
    }
}
