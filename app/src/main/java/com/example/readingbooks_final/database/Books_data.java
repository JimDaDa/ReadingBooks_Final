package com.example.readingbooks_final.database;

public class Books_data {

    private String id;
    private String title, authors,category, description, imgUrl;
    private int pageCount, drawableRes;
    private float rating;
    private boolean status;

    public Books_data(){}
    public Books_data(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public Books_data(String id, String title, String authors, String category, String description, String imgUrl, int pageCount, int drawableRes, float rating, boolean status) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
