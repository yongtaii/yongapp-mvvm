package com.rnd.yongapp_mvvm.viewmodel;

import com.rnd.yongapp_mvvm.enums.Enums;
import com.rnd.yongapp_mvvm.eventbus.OnClickBus;
import com.rnd.yongapp_mvvm.model.Blog;

import org.greenrobot.eventbus.EventBus;

public class MainItemViewModel {

    private Blog blog;

    public MainItemViewModel(Blog blog) {
        this.blog = blog;
    }

    public String getTitle() {
        return blog.getTitle();
    }
    public String getBlogname() {
        return blog.getBlogname();
    }
    public Blog getBlog() {
        return blog;
    }

    public void onItemClick(Blog blog) {
        EventBus.getDefault().post(new OnClickBus(Enums.ONCLICK,blog));
    }

}
