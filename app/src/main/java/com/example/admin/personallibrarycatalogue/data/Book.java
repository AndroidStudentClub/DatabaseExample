package com.example.admin.personallibrarycatalogue.data;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Mikhail Valuyskiy on 25.05.2015.
 */
public class Book {

    //region Private fields
    @Nullable private Integer id_ = null;
    private String title_;
    private String author_;
    private String description_;
    private byte[] cover_;
    //endregion

    //region Constructors
    public Book(@Nullable Integer id, String title, String author, String description, byte[] cover) {
        this.id_ = id;
        this.title_ = title;
        this.author_ = author;
        this.description_ = description;
        this.cover_ = cover;
    }


    public Book(@Nullable Integer id, String title, String author) {
        this.id_ = id;
        this.title_ = title;
        this.author_ = author;
    }

    public Book(){}
    //endregion

    //region Accessor Methods
    @Nullable
    public Integer getId() {
        return id_;
    }

    public String getTitle(){
        return title_;
    }

    public void setTitle(String title){
        this.title_ = title;
    }

    public String getAuthor(){
        return author_;
    }

    public void setAuthor(String author){
        this.author_ = author;
    }

    public String getDescription(){
        return description_;
    }

    public void setDescription(String description){
        this.description_ = description;
    }

    public byte[] getCover(){
        return this.cover_;
    }

    public void setCover(byte[] cover){
        this.cover_ = cover;
    }
    //endregion

}
