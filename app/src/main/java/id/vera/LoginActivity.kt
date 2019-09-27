package id.vera

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        val sharedPreference = getSharedPreferences("VERA-SP", Context.MODE_PRIVATE)

        setContentView(R.layout.activity_login2)
        btn_login.setOnClickListener(this)

        val token: String = sharedPreference.getString("token", "").toString()
        if (token.isNotEmpty()) {
            dashboard()
        }
    }

    override fun onClick(mView: View?) {
        when (mView?.id) {
            R.id.btn_login -> {
                if (et_username.text.toString().isNotEmpty() && et_pass.text.toString().isNotEmpty()) {
                    btn_login.setEnabled(false)
                    btn_login.setText("Loading")

                    Constant.APISERVICE.doLogin(
                        et_username.text.toString(),
                        et_pass.text.toString()
                    )
                        .enqueue(object : retrofit2.Callback<ResponseLoginModel> {
                            override fun onFailure(call: Call<ResponseLoginModel>, t: Throwable) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Username atau Password Salah",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.wtf("ERROR", t.message)

                                btn_login.setEnabled(true)
                                btn_login.setText("SIGN IN")
                            }

                            override fun onResponse(
                                call: Call<ResponseLoginModel>,
                                response: Response<ResponseLoginModel>
                            ) {
                                val sharedPreference =
                                    getSharedPreferences("VERA-SP", Context.MODE_PRIVATE)

                                var editor = sharedPreference.edit()
                                editor.putString("token", response.body()?.token)
                                editor.commit()

                                Log.wtf("DEBUG", sharedPreference.getString("token", ""))

                                dashboard()
                            }

                        })
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

    fun dashboard() {
        startActivity(
            Intent(
                this@LoginActivity,
                DashboardActivity::class.java
            )
        )
        Animatoo.animateSlideUp(this@LoginActivity)
        finish()
    }
}
