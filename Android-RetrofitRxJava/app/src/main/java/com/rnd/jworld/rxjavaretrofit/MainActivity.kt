package com.rnd.jworld.rxjavaretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


// REST API 인증키 : 3sSOPmu3LW1H5Ce6sbzWfrJPHSuD6oTZ

class MainActivity : AppCompatActivity() {

    lateinit var retrofit : Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Layoutmanager : 리사이클러뷰가 아이템을 화면에 표시할 때, 아이템 뷰들이 리아시클러뷰 내부에서 배치되는 형태를 관리하는 요소.
        // 리스트뷰 vs 리사이클러뷰 : 리스트뷰는 수직 스크롤만 가능하지만, 리사이클러뷰는 데이터목록을 수직,수평,Grid 등의 형태로 배치할 수 있다
        // recyclerview는 데이터목록을 수직,수평,Grid 등의 형태로 배치할 수 있는데, 이를 관리하는 요소가 Layout manager이다.
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gsonBuilder = GsonBuilder()
        with(gsonBuilder){
            setLenient()
        }
        val gson = gsonBuilder.create()

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(CurrencyService.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    }
}
