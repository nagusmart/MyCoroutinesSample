package com.example.mycoroutinessample.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mycoroutinessample.util.Resource
import com.example.recyclerviewex.ApiService
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.HashMap

class MainViewModel(private val apiService: ApiService) : ViewModel() {

  fun getUsers()= liveData(Dispatchers.IO) {
         emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.getUsers()))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
  }

    fun createUser(data:HashMap<String, String>)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.createUser(data)))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }

    fun updateUser(data:HashMap<String, String>)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.updateUser(data)))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }
    fun deleteUser(data:HashMap<String, String>)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=apiService.deleteUser(data)))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error occured"))
        }
    }
}