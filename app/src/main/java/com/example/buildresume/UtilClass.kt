package com.example.buildresume

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

object UtilClass {
    fun showToast(context: Context, message:Int){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun onClick(view:View?,navDirections:NavDirections){
         view?.findNavController()?.navigate(navDirections)
    }

    fun showLog(key:String,message:String){
        Log.d(key,message)
    }
}