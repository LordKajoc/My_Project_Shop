package com.lordkajoc.myprojectshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lordkajoc.myprojectshop.data.network.ApiService
import com.lordkajoc.myprojectshop.model.DataDetailNewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelDetailNews @Inject constructor(
    private val Client: ApiService
) : ViewModel() {
    private val liveDetailNews: MutableLiveData<DataDetailNewsItem?> = MutableLiveData()
    val detailNews: LiveData<DataDetailNewsItem?> get() = liveDetailNews

    fun getNewsById(id: Int) {
        Client.getDetailNews(id).enqueue(object : Callback<DataDetailNewsItem> {
            override fun onResponse(
                call: Call<DataDetailNewsItem>,
                response: Response<DataDetailNewsItem>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        liveDetailNews.postValue(responseBody)
                    }
                }
            }
            override fun onFailure(call: Call<DataDetailNewsItem>, t: Throwable) {

            }

        })
    }
}