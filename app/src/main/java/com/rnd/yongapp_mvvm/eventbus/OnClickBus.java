package com.rnd.yongapp_mvvm.eventbus;

import com.rnd.yongapp_mvvm.enums.Enums;
import com.rnd.yongapp_mvvm.model.Blog;

public class OnClickBus {
    private Enums event;
    private Blog blog;

    public OnClickBus(Enums event, Blog blog) {
        this.event = event;
        this.blog = blog;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Enums getEvent() {
        return event;
    }

    public void setEvent(Enums event) {
        this.event = event;
    }
}