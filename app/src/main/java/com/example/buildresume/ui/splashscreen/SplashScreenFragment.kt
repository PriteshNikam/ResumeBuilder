package com.example.buildresume.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.buildresume.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {

    private var splashScreenDelay: Long = 2000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(requireActivity() as AppCompatActivity).supportActionBar?.hide()

        lifecycleScope.launch {
            delay(splashScreenDelay)
           // view.findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginScreenFragment())
            view.findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeScreenFragment(null))
        }

    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
    }
}
