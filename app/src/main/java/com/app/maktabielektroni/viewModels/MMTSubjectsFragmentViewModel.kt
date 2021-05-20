package com.app.maktabielektroni.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.maktabielektroni.models.response.*
import com.app.maktabielektroni.network.ApiService
import com.app.maktabielektroni.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MMTSubjectsFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseSubjectsByClusterId>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseSubjectsByClusterId> {
        return recyclerListData
    }

    fun makeAPICall(input: String?, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getSubjectsByClusterId(input!!)
        call.enqueue(object : Callback<ResponseSubjectsByClusterId> {

            override fun onResponse(
                call: Call<ResponseSubjectsByClusterId>,
                response: Response<ResponseSubjectsByClusterId>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseSubjectsByClusterId>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}