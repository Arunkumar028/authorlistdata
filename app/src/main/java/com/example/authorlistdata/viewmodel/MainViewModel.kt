package com.example.authorlistdata.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorlistdata.Repo.MessageRepository
import com.example.authorlistdata.utils.NetworkUtil
import com.example.authorlistdata.utils.Resource
import com.example.mvvmretrofitcoroutines.data.Authorentity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private var postRepository: MessageRepository,
    private var networkUtil: NetworkUtil
) : ViewModel() {

//    val getdata = postRepository.getData().catch { e ->
//    //    retrivedata= data.
//        Log.e("main1111111111111", "${e.message}")
//    }.asLiveData()

    val data: MutableLiveData<Resource<MutableList<Authorentity>>> = MutableLiveData()

    init {
        fetchdata()
    }

    private fun fetchdata() {
        viewModelScope.launch {
            data.postValue(Resource.loading(null))
            try {
                var dplist = postRepository.getAllPost
                if (dplist.isEmpty() && networkUtil.isNetworkConnected()) {
                    val insertDp = mutableListOf<Authorentity>()
                    for (i in postRepository.getAuthorDetails().body()?.message!!) {
                        val addItems = Authorentity(
                            i.id,
                            i.auther.name,
                            i.updated,
                            i.auther.photos,
                            i.content,
                            "0"
                        )
                        insertDp.add(addItems)
                        postRepository.insert(insertDp)
                    }
                    data.postValue(Resource.success(insertDp))
                }
                else {
                    data.postValue(Resource.success(dplist) as Resource<MutableList<Authorentity>>?)
                }
            } catch (ex: Exception) {
                data.postValue(Resource.error(ex.toString(), null))
            }
        }
    }

    fun getMessage(): MutableLiveData<Resource<MutableList<Authorentity>>> {
        return data
    }

    fun deletedata(authorentity: Authorentity) = viewModelScope.launch(Dispatchers.IO) {
        postRepository.deleted(authorentity)
    }

    fun updatedata(authorentity: Authorentity) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("0000000000000","1")
        postRepository.update(authorentity)

    }
}





