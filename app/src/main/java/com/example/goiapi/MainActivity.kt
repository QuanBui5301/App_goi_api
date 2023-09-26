package com.example.goiapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goiapi.API.APIClient
import com.example.goiapi.API.APIservice
import com.example.goiapi.DataAdapter.Companion.DataList
import com.example.goiapi.data.Result
import com.example.goiapi.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val api: APIservice by lazy {
        APIClient().getClient().create(APIservice::class.java)
    }
    @Inject lateinit var network: Network
    lateinit var dataModelView: DataModelView
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private var dataAdapter : DataAdapter = DataAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        dataModelView = ViewModelProvider(this).get(DataModelView::class.java)
        CallData()
        dataModelView.currentCall.observe(this, Observer {
            DataList = mutableListOf()
            DataList = it.results as MutableList<Result>
            Log.e("Data", it.results.size.toString())
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = dataAdapter
        })
    }
    private fun CallData() {
        val callApi = api.getData(true)
        callApi.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.e("onFailure", "Err : ${response.code()}")
                when (response.code()) {
                    in 200..299 -> {
                        dataModelView.currentCall.value = response.body()
                        progressBar.visibility = View.GONE
                    }
                    in 300..399 -> {
                        Log.d("Response Code", " Redirection messages : ${response.code()}")
                    }
                    in 400..499 -> {
                        Log.d("Response Code", " Client error responses : ${response.code()}")
                    }
                    in 500..599 -> {
                        Log.d("Response Code", " Server error responses : ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("onFailure", "Err : ${t.message}")
            }
        })
    }
}