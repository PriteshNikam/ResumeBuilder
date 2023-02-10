package com.example.buildresume.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.buildresume.R
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(splashScreenDelay)
           // view.findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginScreenFragment())
            view.findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeScreenFragment())
        }
    }
    companion object{
        private var splashScreenDelay: Long = 2000
    }

}
