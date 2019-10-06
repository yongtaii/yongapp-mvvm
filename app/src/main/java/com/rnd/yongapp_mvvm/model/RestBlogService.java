package com.rnd.yongapp_mvvm.model;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface RestBlogService {
    @GET("v3/search/book?sort=accuracy&target=title&size=10")
    Observable<BlogList> getBooks(@Header("Authorization") String restAPIKey, @Query("query") String query, @Query("page") int page);
}
