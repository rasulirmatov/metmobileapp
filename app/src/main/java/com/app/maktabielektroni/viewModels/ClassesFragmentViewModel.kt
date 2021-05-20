package com.app.maktabielektroni.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.maktabielektroni.adapters.ClassesFragmentCardAdapter
import com.app.maktabielektroni.models.response.ClassesItem
import com.app.maktabielektroni.models.response.ResponseClassesModel
import com.app.maktabielektroni.network.ApiService
import com.app.maktabielektroni.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassesFragmentViewModel : ViewModel() {
    var recyclerListData: MutableLiveData<ResponseClassesModel>
    var recyclerViewAdapter: ClassesFragmentCardAdapter

    init {
        recyclerListData = MutableLiveData()
        recyclerViewAdapter = ClassesFragmentCardAdapter()
    }

    fun getAdapter(): ClassesFragmentCardAdapter {
        return recyclerViewAdapter
    }

    fun setAdapterData(data: MutableList<ClassesItem>) {
        recyclerViewAdapter.setDataList(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseClassesModel> {
        return recyclerListData
    }

    fun makeAPICall(input: String, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getClasses()
        call.enqueue(object : Callback<ResponseClassesModel> {

            override fun onResponse(
                call: Call<ResponseClassesModel>,
                response: Response<ResponseClassesModel>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseClassesModel>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}