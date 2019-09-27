package id.vera.service

import id.vera.BuildConfig
import id.vera.util.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object VeraRetrofit {
    val logging = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logging)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)


    val veraRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constant.APIURL)
        .client(okHttpClientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}