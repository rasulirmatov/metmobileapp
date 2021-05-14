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

class OlympiadDetailFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseOlympicDetail>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseOlympicDetail> {
        return recyclerListData
    }

    fun makeAPICall(input: String,input2: String, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getOlympicDetail(input, input2)
        call.enqueue(object : Callback<ResponseOlympicDetail> {

            override fun onResponse(
                call: Call<ResponseOlympicDetail>,
                response: Response<ResponseOlympicDetail>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseOlympicDetail>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}