package id.vera.service

import id.vera.model.ResponseLoginModel
import retrofit2.Call
import retrofit2.http.GET

public interface VeraService {


    @GET("/login")
    fun doLogin(): Call<ResponseLoginModel>

}