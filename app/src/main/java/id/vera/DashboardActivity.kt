package id.vera

import android.Manifest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.StrictMode
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sembozdemir.permissionskt.askPermissions
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.io.File
import java.io.IOException


class DashboardActivity : AppCompatActivity() {
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
            Log.d("CameraDemo", "Pic saved")
            val hasilImage = File(fileName) //INI HASIL CAPTURE
            val myBitmap = BitmapFactory.decodeFile(hasilImage.path)
            image_test.setImageBitmap(myBitmap)
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
