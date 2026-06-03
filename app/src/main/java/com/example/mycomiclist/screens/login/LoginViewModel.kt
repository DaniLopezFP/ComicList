package com.example.mycomiclist.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

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
}