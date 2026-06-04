package com.example.mycomiclist.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {

    // Estados con valor inicial vacío para evitar NullPointerException
    private val _userName = MutableLiveData<String>("")
    val userName: LiveData<String> = _userName

    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    fun onLoginChange(userName: String, password: String) {
        _userName.value = userName
        _password.value = password
    }

    // NUEVA FUNCIÓN: Lógica preparatoria para el registro con Firebase
    fun onRegisterClick(onSuccess: (String) -> Unit, onError: () -> Unit) {
        val currentEmail = _userName.value ?: ""
        val currentPassword = _password.value ?: ""

        if (currentEmail.isNotBlank() && currentPassword.isNotBlank()) {
            // Más adelante, aquí irá: auth.createUserWithEmailAndPassword(email, password)...
            onSuccess(currentEmail)
        } else {
            onError()
        }
    }

    fun registerUser() {
        auth.createUserWithEmailAndPassword(
            _userName.value.toString(),
            _password.value.toString()
        ).addOnCompleteListener {
            Log.i(
                "Register button",
                if (it.isSuccessful)
                    "User registered with ID: ${auth.currentUser?.uid}"
                else
                    "Registry failed ${it.exception.toString()}"
            )
        }
    }
}



