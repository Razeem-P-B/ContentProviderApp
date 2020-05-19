package com.example.contentProviderApp

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppApplication : Application() {
    companion object{
        var retrofit:Retrofit?=null
    }

    fun initRetrofitData() {
        val gson = GsonBuilder().setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
        val httpClient = OkHttpClient.Builder()


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)


       // httpClient.addInterceptor(HeaderInterceptor())
        //httpClient.addInterceptor(ReceivedCookiesInterceptor())
        httpClient.connectTimeout(5, TimeUnit.MINUTES)
        httpClient.callTimeout(5, TimeUnit.MINUTES)
        httpClient.writeTimeout(5, TimeUnit.MINUTES)
        httpClient.readTimeout(5, TimeUnit.MINUTES)

        retrofit = Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initRetrofitData()
        Stetho.initializeWithDefaults(this)
    }

}