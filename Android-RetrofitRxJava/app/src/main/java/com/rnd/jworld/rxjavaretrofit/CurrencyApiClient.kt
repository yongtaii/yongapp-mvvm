package com.rnd.jworld.rxjavaretrofit

import com.rnd.jworld.rxjavaretrofit.pojo.Currency
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object CurrencyApiClient {
    // base url은 마지막이 "/"로 끝냄
    private val BASE_URL = "https://www.koreaexim.go.kr/"

    private val client = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY)).build()

    // BASE_RUL 설정, Converter 설정 ( Json Response형태를 Convert해줄 것이므로 Gson 이용)
    // addCallAdapterFactory : RxJava 사용 하기위해 추가 , 응답을 Observable형태로 반환
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Retrofit은 @GET,@POST,@PUT,@DELTE 등 HTTP METHOD에대한 Annotation 지원
    // ApiService에 대한 interface 생성.
    interface ApiService{
        // @Query 를 통해 GET 메서드의 Query도 입력 가능.
        @GET("site/program/financial/exchangeJSON")
        fun getAllCurrencyInfo(@Query("authkey") authkey: String, @Query("data") data: String) : Observable<ArrayList<Currency>>
    }

    fun getCurrencyApiClient() = retrofit


}