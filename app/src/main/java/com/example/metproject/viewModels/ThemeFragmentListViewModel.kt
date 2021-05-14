package com.example.metproject.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.metproject.adapters.ClassesFragmentCardAdapter
import com.example.metproject.adapters.ThemeFragmentListAdapter
import com.example.metproject.models.response.ClassesItem
import com.example.metproject.models.response.ResponseClassesModel
import com.example.metproject.models.response.ResponseThemesModel
import com.example.metproject.models.response.Themes
import com.example.metproject.network.ApiService
import com.example.metproject.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class ThemeFragmentListViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseThemesModel>
    var recyclerViewAdapter: ThemeFragmentListAdapter
    var subjectName = ""

    init {
        recyclerListData = MutableLiveData()
        recyclerViewAdapter = ThemeFragmentListAdapter()
    }

    fun getAdapter(): ThemeFragmentListAdapter {
        return recyclerViewAdapter
    }

    fun setAdapterData(data: MutableList<Themes>) {
        recyclerViewAdapter.setDataList(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseThemesModel> {
        return recyclerListData
    }

    fun makeAPICall(input: String,input2: String, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getThemes(input, input2)
        call.enqueue(object : Callback<ResponseThemesModel> {

            override fun onResponse(
                call: Call<ResponseThemesModel>,
                response: Response<ResponseThemesModel>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseThemesModel>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }






}