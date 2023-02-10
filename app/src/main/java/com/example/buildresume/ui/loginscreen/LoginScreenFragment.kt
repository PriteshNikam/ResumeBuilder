package com.example.buildresume.ui.loginscreen

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.buildresume.R
import com.example.buildresume.UtilClass.showLog
import com.example.buildresume.UtilClass.showToast
import com.example.buildresume.databinding.FragmentLoginScreenBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginScreenFragment : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var firebaseAuth: FirebaseAuth

    private val oneTapResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                when {
                    idToken != null -> {
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        firebaseAuth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    val currentUser = firebaseAuth.currentUser
                                    findNavController().navigate(
                                        LoginScreenFragmentDirections.actionLoginScreenFragmentToHomeScreen(
                                           // currentUser
                                        )
                                    )
                                } else {
                                    Log.w(
                                        "login",
                                        "signInWithCredential:failure",
                                        task.exception
                                    )
                                }
                            }
                    }
                    else -> {
                        // Shouldn't happen.
                        showLog(TAG, "No ID token!")
                    }
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        showLog(TAG, "One-tap dialog was closed.")
                        // Don't re-prompt the user.
                        Snackbar.make( // error
                            binding.root,
                            "One-tap dialog was closed.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    CommonStatusCodes.NETWORK_ERROR -> {
                        showLog(TAG, "One-tap encountered a network error.")
                        // Try again or just ignore.
                        Snackbar.make(
                            binding.root,
                            "One-tap encountered a network error.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        showLog(
                            TAG, "Couldn't get credential from result." +
                                    " (${e.localizedMessage})"
                        )
                        Snackbar.make(
                            binding.root, "Couldn't get credential from result.\" +\n" +
                                    " (${e.localizedMessage})", Snackbar.LENGTH_INDEFINITE
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth // entry point for firebase authentication
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            view?.findNavController()
                ?.navigate(
                    LoginScreenFragmentDirections.actionLoginScreenFragmentToHomeScreen(
                    //    firebaseAuth.currentUser
                    )
                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOneTapSignIn()
    }

    private fun initOneTapSignIn() {
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.web_client_id)) // web id
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()

        binding.linearLayoutLoginScreen.setOnClickListener {
            showToast(requireContext(),R.string.click)
            displaySignIn()
        }
    }

    private fun displaySignIn() {
        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(requireActivity()) { result ->
            try {
                val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                oneTapResult.launch(ib)
            } catch (e: IntentSender.SendIntentException) {
                Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
            }
        }.addOnFailureListener(requireActivity()) { e ->
            // No Google Accounts found. Just continue presenting the signed-out UI.
            showLog(TAG,"error:" +e.localizedMessage!!)
        }
    }

    companion object{
        private var TAG = "google_signIn"
    }
}