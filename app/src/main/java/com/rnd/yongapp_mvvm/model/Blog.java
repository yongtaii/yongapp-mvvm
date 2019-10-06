package com.rnd.yongapp_mvvm.model;

import com.google.gson.annotations.SerializedName;

public class Blog {

    @SerializedName("title")
    private String title;
    @SerializedName("blogname")
    private String blogname;

    public Blog(String title, String blogname) {
        this.title = title;
        this.blogname = blogname;
    }

    public String getTitle() {
        return title;
    }

    public String getBlogname() {
        return blogname;
    }


}
