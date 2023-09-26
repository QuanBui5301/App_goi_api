package com.example.goiapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.goiapi.data.User

class DataModelView : ViewModel() {
    val currentCall = MutableLiveData<User>()
}