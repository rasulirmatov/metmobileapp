package com.app.maktabielektroni.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.maktabielektroni.models.response.ResponseClassesBySubject
import com.app.maktabielektroni.network.ApiService
import com.app.maktabielektroni.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassesFragmentBottomSheetDialogViewModel : ViewModel(){

    var recyclerListData: MutableLiveData<ResponseClassesBySubject>
//    var recyclerViewAdapter: ClassesFragmentBottomSheetDialogCardAdapter

    init {
        recyclerListData = MutableLiveData()
//        recyclerViewAdapter = ClassesFragmentBottomSheetDialogCardAdapter()
    }

//    fun getAdapter(): ClassesFragmentBottomSheetDialogCardAdapter {
//        return recyclerViewAdapter
//    }
//
//    fun setAdapterData(data: MutableList<Classes>?) {
//        recyclerViewAdapter.setDataList(data!!)
//        recyclerViewAdapter.notifyDataSetChanged()
//    }

    fun getRecyclerListDataObserver(): MutableLiveData<ResponseClassesBySubject> {
        return recyclerListData
    }

    fun makeAPICall(input: String?, context: Context) {
        val service = RetrofitClient.getClient(context)!!.create(ApiService::class.java)
        val call = service.getClassesBySubject(input!!)
        call.enqueue(object : Callback<ResponseClassesBySubject> {

            override fun onResponse(
                call: Call<ResponseClassesBySubject>,
                response: Response<ResponseClassesBySubject>
            ) {
                if (response.code() == 200) {
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseClassesBySubject>, t: Throwable) {
                Log.e("ApiError", t.message!!)
                recyclerListData.postValue(null)
            }
        })
    }



}