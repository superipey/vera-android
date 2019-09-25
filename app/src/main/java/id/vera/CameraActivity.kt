package id.vera

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.priyankvasa.android.cameraviewex.Image
import com.priyankvasa.android.cameraviewex.Modes

import kotlinx.android.synthetic.main.activity_camera.*


class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        camera.setCameraMode(Modes.CameraMode.SINGLE_CAPTURE)
        camera.enableCameraMode(Modes.CameraMode.SINGLE_CAPTURE)
        camera.addPictureTakenListener { image: Image ->
            run {
                Log.d("iniApa", "asadad")
            }
        }

        fab_camera.setOnClickListener {
            camera.capture()
        }
    }

    override fun onStart() {
        super.onStart()
        camera.start()
    }

    override fun onPause() {
        super.onPause()
        camera.stop()
    }

    override fun onStop() {
        camera.destroy()
        super.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.visibility = View.VISIBLE
            fab_camera.visibility = View.GONE
        } else {
            loading.visibility = View.GONE
            fab_camera.visibility = View.VISIBLE
        }

    }
}
