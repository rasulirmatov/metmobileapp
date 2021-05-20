package com.app.maktabielektroni.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.maktabielektroni.adapters.MMTFragmentCardAdapter
import com.app.maktabielektroni.adapters.OlympiadFragmentCardAdapter
import com.app.maktabielektroni.models.response.ClusterItem
import com.app.maktabielektroni.models.response.OlympiadClass
import com.app.maktabielektroni.models.response.ResponseClusters
import com.app.maktabielektroni.models.response.ResponseOlympicClasses
import com.app.maktabielektroni.network.ApiService
import com.app.maktabielektroni.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MMTFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseClusters>
    var recyclerViewAdapter: MMTFragmentCardAdapter

    init {
        recyclerListData = MutableLiveData()
        recyclerViewAdapter = MMTFragmentCardAdapter()
    }

    fun getAdapter(): MMTFragmentCardAdapter {
        return recyclerViewAdapter
    }

    fun setAdapterData(data: MutableList<ClusterItem>) {
        recyclerViewAdapter.setDataList(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseClusters> {
        return recyclerListData
    }

    fun makeAPICall(input: String, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getClasters()
        call.enqueue(object : Callback<ResponseClusters> {

            override fun onResponse(
                call: Call<ResponseClusters>,
                response: Response<ResponseClusters>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseClusters>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}