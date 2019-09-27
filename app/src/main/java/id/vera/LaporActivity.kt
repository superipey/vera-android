package id.vera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.StrictMode
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sembozdemir.permissionskt.askPermissions
import id.vera.model.IdentityModel
import id.vera.model.ResponseDefault
import id.vera.model.ResponseLoginModel
import id.vera.util.Constant
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_laporan.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_login
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.IOException


class LaporActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var nis: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)
        btnLaporkan.setOnClickListener(this)
        nis = intent.getStringExtra("nis")

        etNIS.setText(nis)
        etNIS.setEnabled(false)
    }

    override fun onClick(mView: View?) {
        when (mView?.id) {
            R.id.btnLaporkan -> {
                val sharedPreference =
                    getSharedPreferences("VERA-SP", Context.MODE_PRIVATE)

                btnLaporkan.setText("Loading")
                btnLaporkan.setEnabled(false)

                Constant.APISERVICE.laporan(
                    nis,
                    spinnerPelanggaran.selectedItemPosition,
                    et_catatan.text.toString(),
                    "Bearer " + sharedPreference.getString("token", "").toString()
                ).enqueue(object : retrofit2.Callback<ResponseDefault> {
                    override fun onFailure(call: Call<ResponseDefault>, t: Throwable) {
                        Toast.makeText(
                            this@LaporActivity,
                            "Terjadi kesalahan pada server",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.wtf("ERROR", t.message)

                        btnLaporkan.setEnabled(true)
                        btnLaporkan.setText("LAPORKAN")
                    }

                    override fun onResponse(
                        call: Call<ResponseDefault>, response: Response<ResponseDefault>
                    ) {
                        Toast.makeText(
                            this@LaporActivity,
                            "Laporan berhasil disimpan",
                            Toast.LENGTH_LONG
                        ).show()
                        btnLaporkan.setEnabled(true)
                        btnLaporkan.setText("LAPORKAN")
                    }
                })
            }
        }
    }
}