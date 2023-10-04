package com.example.navermaprestaurant

import android.content.res.Resources
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SearchRepository {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AppIntercepter())
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())//코틀린과 json 사이에 매핑을 해주는 어댑터 연결완료
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    private val service = retrofit.create(SearchService::class.java)

    fun getRestaurant(query: String) : Call<SearchResult> {
        return service.getRestaurant(query ="$query 맛집",display = 5)
    }

    class AppIntercepter: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val clientId = MyApplication.applicationContext.getString(R.string.naver_search_client_id)
            val clientSecret = MyApplication.applicationContext.getString(R.string.naver_search_client_secret)
            val newRequest = chain.request().newBuilder() //체인에서 request 를 가져와서 새로운정보를 넣어줌
                .addHeader("X-Naver-Client-Id",clientId)
                .addHeader("X-Naver-Client-Secret",clientSecret)
                .build()
            return chain.proceed(newRequest) //통신할때 헤더를 추가해주기위해 인터셉터 이용해서 okhttpClient 재정의후 사용
        }

    }
}