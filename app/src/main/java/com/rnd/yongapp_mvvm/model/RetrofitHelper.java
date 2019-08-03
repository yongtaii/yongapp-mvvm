package com.rnd.yongapp_mvvm.model;

import com.rnd.yongapp_mvvm.BaseApplication;
import com.rnd.yongapp_mvvm.R;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitHelper {

    private static Retrofit retrofit;
    private RestBlogService RestBlogService;

    private RetrofitHelper() {
        String baseUrl = BaseApplication.getResourceString(R.string.kakao_base_url);
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RestBlogService = retrofit.create(RestBlogService.class);
    }
    private static class Singleton {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return Singleton.INSTANCE;
    }

    public void getBooks(Subscriber<Blog> subscriber, String keyword, int page) {
        String kakaoKey = "KakaoAK " + BaseApplication.getResourceString(R.string.kakao_native_key);
        RestBlogService.getBooks(kakaoKey,keyword,page)
                .map(new Func1<BlogList, List<Blog>>() {
                    @Override
                    public List<Blog> call(BlogList BlogList) {
                        return BlogList.getBlogList();
                    }
                })
                .flatMap(new Func1<List<Blog>, Observable<Blog>>() {
                    @Override
                    public Observable<Blog> call(List<Blog> books) {
                        return Observable.from(books);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    
}
