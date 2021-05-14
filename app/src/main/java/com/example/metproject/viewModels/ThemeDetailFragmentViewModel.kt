package com.example.metproject.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.metproject.adapters.ClassesFragmentCardAdapter
import com.example.metproject.adapters.ThemeFragmentListAdapter
import com.example.metproject.models.response.*
import com.example.metproject.network.ApiService
import com.example.metproject.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class ThemeDetailFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseThemeDetail>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseThemeDetail> {
        return recyclerListData
    }

    fun makeAPICall(input: String, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getThemeDetail(input)
        call.enqueue(object : Callback<ResponseThemeDetail> {

            override fun onResponse(
                call: Call<ResponseThemeDetail>,
                response: Response<ResponseThemeDetail>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseThemeDetail>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }
        })
    }






}