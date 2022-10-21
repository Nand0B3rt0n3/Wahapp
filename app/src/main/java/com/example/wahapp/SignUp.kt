package com.example.wahapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : Fragment() {
    private lateinit var enterEmail           : TextInputEditText
    private lateinit var enterPassword        : TextInputEditText
    private lateinit var enterConfirmPassword : TextInputEditText
    private lateinit var signUpButton         : Button
    private lateinit var progressBar          : ProgressBar
    private lateinit var fauth                : FirebaseAuth
    private lateinit var fstore               : FirebaseFirestore
    private lateinit var db                   : DocumentReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view             = inflater.inflate(R.layout.activity_fragment_sign_up, container, false)
        enterEmail           = view.findViewById(R.id.etSign_Up_Email)
        enterPassword        = view.findViewById(R.id.etSign_Up_Password)
        enterConfirmPassword = view.findViewById(R.id.etSignUp_Confirm_Password)
        signUpButton         = view.findViewById(R.id.btSignUp)
        progressBar          = view.findViewById(R.id.signUpProgressBar)
        fauth                = FirebaseAuth.getInstance()
        fstore               = FirebaseFirestore.getInstance()

        //Listener de los botones
        signUpButton.setOnClickListener {
            val email = enterEmail.text.toString()
            val password = enterPassword.text.toString()
            val confirmPassword = enterConfirmPassword.text.toString()
            if(TextUtils.isEmpty(email))
                {
                 enterEmail.error = "Es necesario introducir un correo para la creación de una cuenta"
                }
            else if(TextUtils.isEmpty(password))
                {
                enterPassword.error = "Es necesario introducir una contraseña para registrarte"
                }
            else if (password.length<6)
                {
                enterPassword.error = "La contraseña debe contener mas de 6 caracteres, te recomendamos una combinacion de letras, numero y simbolos ;-)"
                }
            else if (password!=confirmPassword)
            {
                enterConfirmPassword.error = "Error al confirmar la contraseña"
            }
            progressBar.visibility = View.VISIBLE
            createAccount(email,password)
        }


        return view
    }

    private fun createAccount(em : String,pass : String) {
        fauth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener{task->
            if (task.isSuccessful)
            {
                val userInfo = fauth.currentUser?.uid
                db = fstore.collection("users").document(userInfo.toString())
                val obj = mutableMapOf<String,String>()
                obj["userEmail"] = em
                obj["userPassword"] = pass
                obj["userStatus"]=""
                obj["userName"]=""
                db.set(obj).addOnSuccessListener {
                    Log.d("onSuccess","User Created Sucessfully")
                    progressBar.visibility = View.GONE
                }
            }
        }


    }
}
