package com.rnd.yongapp_mvvm.viewmodel;

import com.rnd.yongapp_mvvm.model.Blog;
import com.rnd.yongapp_mvvm.model.RetrofitHelper;
import com.rnd.yongapp_mvvm.view.MainAdapter;

import rx.Subscriber;

public class MainViewModel {

    private MainAdapter mainAdapter;
    private Subscriber<Blog> subscriber;

    public MainViewModel(MainAdapter mainAdapter,String keyword,int page) {
        this.mainAdapter = mainAdapter;
        getBooks(keyword,page);
    }

    public void getBooks(String keyword, int page) {
        subscriber = new Subscriber<Blog>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Blog blog) {
                // View에 해당하는 Adapter를 통해 Obserable 데이터를 소비
                mainAdapter.addItem(blog);
            }
        };
        RetrofitHelper.getInstance().getBooks(subscriber,keyword,page);
    }

}
