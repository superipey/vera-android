package id.vera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import id.vera.model.ResponseLoginModel
import id.vera.util.Constant
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener(this)
    }

    override fun onClick(mView: View?) {
        when (mView?.id) {
            R.id.btn_login -> {
                if (et_username.text.toString().isNotEmpty() && et_pass.text.toString().isNotEmpty()) {

                    //CONTOH API RETROFIT

//                    Constant.APISERVICE.doLogin().enqueue(object :retrofit2.Callback<ResponseLoginModel> {
//                        override fun onFailure(call: Call<ResponseLoginModel>, t: Throwable) {
//                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                        }
//
//                        override fun onResponse(
//                            call: Call<ResponseLoginModel>,
//                            response: Response<ResponseLoginModel>
//                        ) {
//                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                        }
//
//                    })


                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    Animatoo.animateSlideUp(this@LoginActivity)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Username & Password Tidak Boleh Kosong",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else -> {
            }
        }
    }

}
