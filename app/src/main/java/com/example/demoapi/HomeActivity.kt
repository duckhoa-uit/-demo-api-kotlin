   package com.example.demoapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.demoapi.api.ApiService
import com.example.demoapi.models.DataModal
import com.example.demoapi.models.LoginResponse
import com.example.demoapi.models.User
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

   class HomeActivity : AppCompatActivity() {
       var users = ArrayList<User>()

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnGetUsersData.setOnClickListener(View.OnClickListener { view ->
            handleBtnGetUsersData()
        })
    }

    private fun handleBtnGetUsersData(){
        ApiService.apiService.getUsers("json")
            .enqueue(object: Callback<List<User>>{
                override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                    users = response?.body() as ArrayList<User>
                    var usersData = ""
                    for (u: User in users){
                        val str = "\n----------------" +
                                "\nUser has id: ${u.id}" +
                                "\nFirst name: ${u.first_name}" +
                                "\nLast name: ${u.last_name}" +
                                "\nEmail: ${u.email}" +
                                "\nIs active: ${u.isIs_active}"
                        usersData += (str)
                    }
                    Toast.makeText(applicationContext, "SENT GET REQUEST: SUCCESS", Toast.LENGTH_LONG).show()
                    tvUsersData.text = usersData
                }

                override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "SENT GET REQUEST: FAIL", Toast.LENGTH_LONG).show()
                }

            })
    }
}