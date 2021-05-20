package com.app.maktabielektroni.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.maktabielektroni.models.response.ResponseSubjectsModel
import com.app.maktabielektroni.network.ApiService
import com.app.maktabielektroni.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectsFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseSubjectsModel>


    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseSubjectsModel> {
        return recyclerListData
    }

    fun makeAPICall(input: String, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getSubjects()
        call.enqueue(object : Callback<ResponseSubjectsModel> {

            override fun onResponse(
                call: Call<ResponseSubjectsModel>,
                response: Response<ResponseSubjectsModel>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseSubjectsModel>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}