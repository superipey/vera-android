package id.vera.service

import id.vera.model.IdentityModel
import id.vera.model.ResponseDefault
import id.vera.model.ResponseLoginModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

public interface VeraService {
    @POST("api/login")
    @FormUrlEncoded
    fun doLogin(@Field("username") username: String?, @Field("password") password: String?): Call<ResponseLoginModel>

    @POST("api/identify")
    @Multipart
    fun identity(@Part file: MultipartBody.Part, @Header("Authorization") auth: String): Call<List<IdentityModel>>

    @POST("api/laporan")
    @FormUrlEncoded
    fun laporan(@Field("nis") nis: String?, @Field("jenis_pelanggaran") pelanggaran: Int?, @Field("catatan") catatan: String?, @Header("Authorization") auth: String): Call<ResponseDefault>
}