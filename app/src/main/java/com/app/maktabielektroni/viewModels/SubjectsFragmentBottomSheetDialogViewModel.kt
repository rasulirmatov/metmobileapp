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

class SubjectsFragmentBottomSheetDialogViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<ResponseSubjectsByClass>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseSubjectsByClass> {
        return recyclerListData
    }

    fun makeAPICall(input: String?, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getSubjectsByClass(input!!)
        call.enqueue(object : Callback<ResponseSubjectsByClass> {

            override fun onResponse(
                call: Call<ResponseSubjectsByClass>,
                response: Response<ResponseSubjectsByClass>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseSubjectsByClass>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }

        })
    }

}