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
}