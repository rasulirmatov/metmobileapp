package com.app.maktabielektroni.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.maktabielektroni.adapters.OlympiadFragmentCardAdapter
import com.app.maktabielektroni.models.response.OlympiadClass
import com.app.maktabielektroni.models.response.ResponseOlympicClasses
import com.app.maktabielektroni.network.ApiService
import com.app.maktabielektroni.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OlympiadFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseOlympicClasses>
    var recyclerViewAdapter: OlympiadFragmentCardAdapter

    init {
        recyclerListData = MutableLiveData()
        recyclerViewAdapter = OlympiadFragmentCardAdapter()
    }

    fun getAdapter(): OlympiadFragmentCardAdapter {
        return recyclerViewAdapter
    }

    fun setAdapterData(data: MutableList<OlympiadClass>) {
        recyclerViewAdapter.setDataList(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseOlympicClasses> {
        return recyclerListData
    }

    fun makeAPICall(input: String, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getOlympicClasses()
        call.enqueue(object : Callback<ResponseOlympicClasses> {

            override fun onResponse(
                call: Call<ResponseOlympicClasses>,
                response: Response<ResponseOlympicClasses>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseOlympicClasses>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}