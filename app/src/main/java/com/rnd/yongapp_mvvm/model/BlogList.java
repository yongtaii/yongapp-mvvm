package com.rnd.yongapp_mvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogList {
    @SerializedName("documents")
    private List<Blog> mBlogList;
    public List<Blog> getBlogList() {
        return mBlogList;
    }
}
