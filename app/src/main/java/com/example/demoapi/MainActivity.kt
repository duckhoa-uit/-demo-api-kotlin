package com.example.demoapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demoapi.api.ApiService
import com.example.demoapi.model.DataModal
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showHome()

        btnShowLogin.setOnClickListener( View.OnClickListener { view ->
            showLogin()
        })
        btnShowRegister.setOnClickListener(View.OnClickListener { view ->
            showRegistration()
        })

        btnLogin.setOnClickListener(View.OnClickListener { view ->
            val email = edtLoginEmail.text.toString().trim()
            val password = edtLoginPassword.text.toString().trim()

            if (email.isEmpty()){
                edtLoginEmail.error = "Email required"
                edtLoginEmail.requestFocus()
                return@OnClickListener
            }
            if (password.isEmpty()){
                edtLoginPassword.error = "Password required"
                edtLoginPassword.requestFocus()
                return@OnClickListener
            }

            login(email, password)
        })

        btnRegister.setOnClickListener(View.OnClickListener { view ->
            val email = edtRegisterEmail.text.toString().trim()
            val password = edtRegisterPassword.text.toString().trim()
            val comfirmedPassword = edtRegisterConfirmedPassWord.text.toString().trim()

            if (email.isEmpty()){
                edtRegisterEmail.error = "Email required."
                edtRegisterEmail.requestFocus()
                return@OnClickListener
            }
            if (password.isEmpty()){
                edtRegisterPassword.error = "Password required."
                edtRegisterPassword.requestFocus()
                return@OnClickListener
            }
            if (comfirmedPassword.isEmpty()){
                edtRegisterConfirmedPassWord.error = "Confirmed password required."
                edtRegisterConfirmedPassWord.requestFocus()
                return@OnClickListener
            }
            if (comfirmedPassword != password){
                edtRegisterConfirmedPassWord.error = "Confirmed password does not match password."
                edtRegisterConfirmedPassWord.requestFocus()
                return@OnClickListener
            }
            register(email, password)
        })
    }

    private fun login(email: String, password: String) {
        val data = DataModal(email, password)
        ApiService.apiService.loginUser(data)
            .enqueue(object: Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>?,
                    response: Response<LoginResponse>?
                ) {
                    if (response?.code() == 200){
                        Toast.makeText(applicationContext, "LOGIN SUCCESSFUL\naccess_token: " + response?.body()?.access_token, Toast.LENGTH_LONG).show()
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    }else if ((response?.code() == 400)){
                        val jsonResponse = JSONObject(response?.errorBody()?.string())
                        val errorMessage = jsonResponse["error_message"]
                        Toast.makeText(applicationContext,
                            "LOGIN FAILED\n$errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "LOGIN FAILED" + t?.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun register(email: String, password: String) {
        val data = DataModal(email, password)
        ApiService.apiService.registerUser(data)
            .enqueue(object: Callback<RegisterResponse>{
                override fun onResponse(
                    call: Call<RegisterResponse>?,
                    response: Response<RegisterResponse>?
                ) {
                    if (response?.code() == 201){
                        Toast.makeText(
                            applicationContext,
                            "REGISTER SUCCESSFUL" +
                                    "\nid: " + response.body()?.getId() +
                                    "\nemail: " + response.body()?.getEmail()
                            , Toast.LENGTH_LONG).show()
                        showLogin()
                    }else if (response?.code() == 400){
                        val jsonResponse = JSONObject(response.errorBody().string())
                        var error_message = ""
                        if (jsonResponse["email"] != null){
                            error_message += jsonResponse["email"]
                        }
                        Toast.makeText(applicationContext,
                            "REGISTER FAILED\n$jsonResponse", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "REGISTER FAILED" + t?.message, Toast.LENGTH_LONG).show()

                }

            })
    }

    private fun showRegistration() {
        registration_layout.visibility = View.VISIBLE
        login_layout.visibility = View.GONE
//        main_layout.visibility = View.GONE
    }
    private fun showLogin() {
        registration_layout.visibility = View.GONE
        login_layout.visibility = View.VISIBLE
//        main_layout.visibility = View.GONE
    }

    private  fun showHome(){
        registration_layout.visibility = View.GONE
        login_layout.visibility = View.GONE
        main_layout.visibility = View.VISIBLE
    }

}