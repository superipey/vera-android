package id.vera.service

import id.vera.util.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VeraRetrofit {
    val veraRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constant.APIURL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}