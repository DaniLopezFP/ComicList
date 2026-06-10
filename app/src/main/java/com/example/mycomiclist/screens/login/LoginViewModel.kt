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

    // Para guardar el fallo de Firebase y que la UI muestre el aviso
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    //Para guardar el error en el login
    private val _isLoginError = MutableLiveData<Boolean>(false)
    val isLoginError: LiveData<Boolean> = _isLoginError

    fun onLoginChange(userName: String, password: String) {
        _userName.value = userName
        _password.value = password
        _errorMessage.value = null // Limpiamos el error al escribir
    }

    fun loginUser(navigateToHome: (String) -> Unit) {
        _errorMessage.value = null // Reseteamos antes de intentar

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
                //_isLoginError.postValue(true)
            }
        }
    }

    fun registerUser(navigateToHome: (String) -> Unit) {
        _errorMessage.value = null // Limpiamos advertencias previas

        auth.createUserWithEmailAndPassword(
            _userName.value.toString(),
            _password.value.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i("Register button", "User registered with ID: ${auth.currentUser?.uid}")

                // CRUCIAL: Si el registro es correcto, navegamos directos pasando el email
                navigateToHome(_userName.value.toString())
            } else {
                Log.i("Register button", "Registry failed ${task.exception.toString()}")

                // Si falla, guardamos el mensaje de Firebase para que salte la alerta en la pantalla
                _errorMessage.value = task.exception?.localizedMessage ?: "Error en el registro"
                //_isLoginError.postValue(true)
            }
        }
    }

    // Para que el botón de aceptar del AlertDialog pueda limpiar la alerta
    fun clearError() {
        _errorMessage.value = null
    }
}



