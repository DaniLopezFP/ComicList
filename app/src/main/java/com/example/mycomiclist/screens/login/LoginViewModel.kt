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

    // ESTADO DE ADVERTENCIA: Para avisar al usuario si el login falla (Requisito)
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

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

    // 1. FUNCIÓN DE LOGIN
    fun loginUser(navigateToHome: (String) -> Unit) {
        auth.signInWithEmailAndPassword(
            _userName.value.toString(),
            _password.value.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("Login button", "User logged: ${auth.currentUser?.email}")
                navigateToHome(_userName.value.toString())
            } else {
                Log.i("Login button", "User login failed: ${it.exception.toString()}")
                _errorMessage.value = it.exception?.localizedMessage ?: "Error de autenticación"
            }
        }
    }

    // 2. FUNCIÓN DE REGISTRO (Asegúrate de añadirle el parámetro 'navigateToHome')
    fun registerUser(navigateToHome: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(
            _userName.value.toString(),
            _password.value.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("Register button", "User registered: ${auth.currentUser?.email}")
                navigateToHome(_userName.value.toString())
            } else {
                Log.i("Register button", "User registration failed: ${it.exception.toString()}")
                _errorMessage.value = it.exception?.localizedMessage ?: "Error en el registro"
            }
        }
    }

    // Función para limpiar la advertencia desde la UI (por ejemplo, al cerrar un diálogo)
    fun clearError() {
        _errorMessage.value = null
    }
}




