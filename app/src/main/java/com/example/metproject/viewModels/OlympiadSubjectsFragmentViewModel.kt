package com.example.metproject.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.metproject.adapters.ClassesFragmentBottomSheetDialogCardAdapter
import com.example.metproject.adapters.ClassesFragmentCardAdapter
import com.example.metproject.adapters.SubjectsFragmentBottomSheetDialogCardAdapter
import com.example.metproject.models.response.*
import com.example.metproject.network.ApiService
import com.example.metproject.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class OlympiadSubjectsFragmentViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseOlympicSubjects>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseOlympicSubjects> {
        return recyclerListData
    }

    fun makeAPICall(input: String?, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getOlympicSubjects(input!!)
        call.enqueue(object : Callback<ResponseOlympicSubjects> {

            override fun onResponse(
                call: Call<ResponseOlympicSubjects>,
                response: Response<ResponseOlympicSubjects>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseOlympicSubjects>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}