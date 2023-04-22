package com.example.readingbooks_final.database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Books_data implements Serializable {

    private String id, id_user, id_chapter, id_report;
    private String title, authors,category, description, imgUrl, status, publishStatus, publishDate, fileUrl, reason_rp, reason_num;
    private Long timestamp;

    private float rating;

    private long view ;



    public Books_data(){}


    public Books_data(String id, String id_user, String id_chapter, String id_report, String title, String authors, String category, String description, String imgUrl, String status, String publishStatus, String publishDate, String fileUrl, String reason_rp, String reason_num, Long timestamp, float rating, long view) {
        this.id = id;
        this.id_user = id_user;
        this.id_chapter = id_chapter;
        this.id_report = id_report;
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.description = description;
        this.imgUrl = imgUrl;
        this.status = status;
        this.publishStatus = publishStatus;
        this.publishDate = publishDate;
        this.fileUrl = fileUrl;
        this.reason_rp = reason_rp;
        this.reason_num = reason_num;
        this.timestamp = timestamp;
        this.rating = rating;
        this.view = view;
    }

    public Books_data(Long timestamp, String publishStatus) {
        this.timestamp = timestamp;
        this.publishStatus = publishStatus;
    }



    public Books_data(String id_rp, String id_user, String report, String reason1) {
        this.id_report = id_rp;
        this.id_user = id_user;
        this.reason_rp=reason1;
        this.reason_num=report;

    }


    public String getId_chapter() {
        return id_chapter;
    }

    public void setId_chapter(String id_chapter) {
        this.id_chapter = id_chapter;
    }

    public String getReason_num() {
        return reason_num;
    }

    public void setReason_num(String reason_num) {
        this.reason_num = reason_num;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public Books_data(String id_books, String id_user, String cover, String title, String author, String categoryy, String statuss, String description , String publishStatus, long view) {
        this.id = id_books;
        this.id_user = id_user;
        this.imgUrl=cover;
        this.title = title;
        this.authors = author;
        this.category = categoryy;
        this.status = statuss;
        this.description = description;
        this.publishStatus= publishStatus;
        this.view = view;

    }

    public Books_data(String id_books, String id_user, String cover) {
        this.id = id_books;
        this.id_user = id_user;
        this.imgUrl=cover;



    }
//    public Books_data(String id_report, String id_user, String reason_rp) {
//        this.id_report = id_report;
//        this.id_user = id_user;
//        this.reason_rp=reason_rp;
//
//
//
//    }

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

    public String getId_report() {
        return id_report;
    }

    public void setId_report(String id_report) {
        this.id_report = id_report;
    }

    public String getReason_rp() {
        return reason_rp;
    }

    public void setReason_rp(String reason_rp) {
        this.reason_rp = reason_rp;
    }

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    public Map<String, Object> ReportMap(){
        HashMap<String,Object> res = new HashMap<>();
        res.put("id_report",  id_report);
        res.put("id_user",id_user);
        res.put("reason",  reason_rp);
        res.put("reason_num",  reason_num);
        return res;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap();
        result.put("id",id);
        result.put("id_user",id_user);
        result.put("id_chapter",  id_chapter);
        result.put("id_report",  id_report);
        result.put("title",title);
        result.put("authors",authors);
        result.put("category",category);
        result.put("imgUrl",imgUrl);
        result.put("rating", rating);
        result.put("timestamp", timestamp);
        result.put("status", status);
        result.put("publishStatus", publishStatus);
        result.put("publishDate", publishDate);
        result.put("fileUrl", fileUrl);
        result.put("reason",  reason_rp);
        result.put("reason_num",  reason_num);
        return result ;
    }
}
