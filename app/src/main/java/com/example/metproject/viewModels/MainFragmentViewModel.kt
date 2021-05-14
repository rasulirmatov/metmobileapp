package com.example.metproject.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.metproject.adapters.ClassesFragmentCardAdapter
import com.example.metproject.models.response.*
import com.example.metproject.network.ApiService
import com.example.metproject.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseNews>

    var recyclerListDataMainSlider: MutableLiveData<ResponseMainSlider>

    var recyclerListDataSecondSlider: MutableLiveData<ResponseSecondSlider>

    init {
        recyclerListData = MutableLiveData()
        recyclerListDataMainSlider = MutableLiveData()
        recyclerListDataSecondSlider = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseNews> {
        return recyclerListData
    }

    fun getMainSliderListDataObserver(): MutableLiveData<ResponseMainSlider> {
        return recyclerListDataMainSlider
    }

    fun getSecondSliderListDataObserver(): MutableLiveData<ResponseSecondSlider> {
        return recyclerListDataSecondSlider
    }

    fun makeAPICall(context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getNews()
        call.enqueue(object : Callback<ResponseNews> {

            override fun onResponse(
                call: Call<ResponseNews>,
                response: Response<ResponseNews>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

    fun makeAPICallMainslider(context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getMainSlider()
        call.enqueue(object : Callback<ResponseMainSlider> {
            override fun onResponse(
                call: Call<ResponseMainSlider>,
                response: Response<ResponseMainSlider>
            ) {
                if (response.code() == 200) {
                    recyclerListDataMainSlider.postValue(response.body())
                } else {
                    recyclerListDataMainSlider.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseMainSlider>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListDataMainSlider.postValue(null)
            }

        })
    }

    fun makeAPICallSecondslider(context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getSecondSlider()
        call.enqueue(object : Callback<ResponseSecondSlider> {
            override fun onResponse(
                call: Call<ResponseSecondSlider>,
                response: Response<ResponseSecondSlider>
            ) {
                if (response.code() == 200) {
                    recyclerListDataSecondSlider.postValue(response.body())
                } else {
                    recyclerListDataSecondSlider.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseSecondSlider>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListDataSecondSlider.postValue(null)
            }
        })
    }

}