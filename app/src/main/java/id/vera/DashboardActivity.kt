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
import id.vera.util.Constant
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.IOException


class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    var dir: String = ""
    var TAKE_PHOTO_CODE = 0
    var newfile: File? = null
    var fileName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setPermission()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        dir = "${Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)}/picFolder/"
        val newdir = File(dir)
        newdir.mkdirs()

        btnLaporan.setOnClickListener(this)

        fab.setOnClickListener {
            fileName = "$dir/${System.currentTimeMillis()}.jpg"
            newfile = File(fileName)
            try {
                newfile?.createNewFile()
            } catch (e: IOException) {
            }


            val outputFileUri = Uri.fromFile(newfile)

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)

            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PHOTO_CODE && resultCode == Activity.RESULT_OK) {
            loading.setVisibility(View.VISIBLE)
            defaultImage.setVisibility(View.GONE)

            Log.d("CameraDemo", "Pic saved")
            val hasilImage = File(fileName) //INI HASIL CAPTURE
            val myBitmap = BitmapFactory.decodeFile(hasilImage.path)

            myBitmap.compress(Bitmap.CompressFormat.JPEG, 3, hasilImage.outputStream())

            image_test.setImageBitmap(myBitmap);

            val requestFile = hasilImage.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData(
                "file",
                hasilImage.getName(),
                requestFile
            )

            val sharedPreference =
                getSharedPreferences("VERA-SP", Context.MODE_PRIVATE)

            Constant.APISERVICE.identity(
                body,
                "Bearer " + sharedPreference.getString("token", "").toString()
            )
                .enqueue(object : retrofit2.Callback<List<IdentityModel>> {
                    override fun onFailure(call: Call<List<IdentityModel>>, t: Throwable) {
                        loading.setVisibility(View.GONE)
                        Toast.makeText(
                            this@DashboardActivity,
                            "Gagal melakukan identifikasi",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.wtf("ERROR", t.message)
                    }

                    override fun onResponse(
                        call: Call<List<IdentityModel>>,
                        response: Response<List<IdentityModel>>
                    ) {
                        loading.setVisibility(View.GONE)

                        if (response.body()?.size!! > 0) {
                            val identity: IdentityModel? = response.body()?.get(0)
                            Log.wtf("DEBUG", identity?.nis)
                            Glide.with(this@DashboardActivity).load(identity?.foto).transition(
                                DrawableTransitionOptions.withCrossFade()
                            )
                                .into(image_test)

                            txtNamaLengkap.setText(identity?.data?.biodata?.nama_lengkap)
                            txtNis.setText(identity?.data?.nis)
                            txtNisn.setText(identity?.data?.nisn)
                            txtKelas.setText(identity?.data?.detailKelas?.kelas?.nama_kelas)
                            val jenisKelamin =
                                if (identity?.data?.biodata?.jenis_kelamin.equals("L")) "Laki-Laki" else "Perempuan"

                            txtJenisKelamin.setText(jenisKelamin)


                            txtAlamat.setText(identity?.data?.biodata?.alamat_jalan + " RT" + identity?.data?.biodata?.alamat_rt + "/" + identity?.data?.biodata?.alamat_rw + " Kel/Desa" + identity?.data?.biodata?.alamat_desa + " Kec. " + identity?.data?.biodata?.alamat_kecamatan + " " + identity?.data?.biodata?.alamat_kota + " Kodepos " + identity?.data?.biodata?.alamat_pos)

                            txtAgama.setText(identity?.data?.biodata?.agama)
                            txtTelpMobile.setText(identity?.data?.biodata?.telp_mobile)
                            txtEmail.setText(identity?.data?.biodata?.email)

                            biodata.setVisibility(View.VISIBLE)
                        } else {
                            Toast.makeText(
                                this@DashboardActivity,
                                "Wajah tidak ditemukan, silakan foto ulang.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLaporan -> {
                val intent = Intent(this, LaporActivity::class.java)
                intent.putExtra("nis", txtNis.text)
                startActivity(intent)
            }
        }
    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000, true)
        val path =
            Images.Media.insertImage(inContext.contentResolver, OutImage, "Title", null)
        return Uri.parse(path)
    }

    private fun setPermission() {
        askPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) {
            onDenied {
                setPermission()
            }
        }
    }
}
