package com.example.wahapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth



class Login : Fragment() {
    private lateinit var enterEmail: TextInputEditText
    private lateinit var enterPassword: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var googleButton: Button
    private lateinit var progress: ProgressBar
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var resultLaunch: ActivityResultLauncher<Intent>
    private val RC_SIGN_IN = 1011
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_fragment_login, container, false)
        enterEmail = view.findViewById(R.id.etLoginEmail)
        enterPassword = view.findViewById(R.id.etLoginPassword)
        loginButton = view.findViewById(R.id.btLogin)
        googleButton = view.findViewById(R.id.btLoginGoogle)
        progress = view.findViewById(R.id.LoginProgressBar)
        loginButton.setOnClickListener {
            val email = enterEmail.text.toString()
            val password = enterPassword.text.toString()
            if (TextUtils.isEmpty(email)) {
                enterEmail.error = "Es necesario introducir un correo para identificarte"
            } else if (TextUtils.isEmpty(password)) {
                enterPassword.error = "Es necesario introducir una contraseña para identificarse"
            } else {
                progress.visibility = View.VISIBLE
                signIn(email, password)
            }

        }
        googleButton.setOnClickListener {
            createRequest()
        }
        resultLaunch =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val launchData = result.data
                    val task = GoogleSignIn.getSignedInAccountFromIntent(launchData)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        Log.d("Gmail ID", "firebaseAuthWith Google : $account")
                        firebaseAuthWithGoogle(account?.idToken)
                    } catch (e: ApiException) {
                        Log.w("Error", "Google sign in failed", e)
                    }

                }
            }

        return view
    }


    private fun signIn(em: String, pass: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(em, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show()
                    onStart()
                }

            }
    }

    private fun createRequest() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("Google ID")
            .requestEmail()
            .build()
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        TODO("Not yet implemented")
    }
}

