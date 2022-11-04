package com.example.buildresume.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildresume.ProfileEditDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(private val profileEditDetails: ProfileEditDetails):ViewModel() {

    fun writeToLocal(userName:String,userMobile:String,userAddress:String,userEmail:String) = viewModelScope.launch {
        profileEditDetails.writeToLocal(userName,userMobile,userAddress,userEmail)
    }

    val readToLocal = profileEditDetails.readToLocal
}