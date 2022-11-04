package com.example.buildresume.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildresume.repository.ProfileEditDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(private val profileEditDetailsRepository: ProfileEditDetailsRepository):ViewModel() {

    fun writeToLocal(userName:String,userMobile:String,userAddress:String,userEmail:String) = viewModelScope.launch {
        profileEditDetailsRepository.writeToLocal(userName,userMobile,userAddress,userEmail)
    }

    val readToLocal =  profileEditDetailsRepository.readToLocal
}