package com.example.mycomiclist.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mycomiclist.R
import com.example.mycomiclist.ui.theme.azul1
import com.example.mycomiclist.ui.theme.gris1
import com.example.mycomiclist.ui.theme.marvelFont
import com.example.mycomiclist.ui.theme.naranja1
import com.example.mycomiclist.ui.theme.rojo1
import com.example.mycomiclist.ui.theme.verde1

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    doLogin: (String) -> Unit,
    doRegister: (String) -> Unit,
) {
    // Suscripción segura a los LiveData del ViewModel
    val userName by loginViewModel.userName.observeAsState(initial = "")
    val password by loginViewModel.password.observeAsState(initial = "")
    val errorMessage by loginViewModel.errorMessage.observeAsState(initial = null)

    val isLoginError by loginViewModel.isLoginError.observeAsState(false)

    // Estado de UI local administrado correctamente por Compose
    var passVisibility by remember { mutableStateOf(false) }

    //Variable para el contexto
    val myContext = LocalContext.current

   //Código para lanzar el toast cuando falla el login
    LaunchedEffect(errorMessage) {
        errorMessage?.let { mensajeDeError ->
            Toast.makeText(myContext, mensajeDeError, Toast.LENGTH_LONG).show()
            loginViewModel.clearError() // Limpia el estado para poder repetir el aviso
        }
    }
    // 🌟 PASAMOS EL NUEVO PARÁMETRO 'isLoginError' A LAS COMPOSABLES SEPARADAS
    //UserName(userName, isLoginError) { loginViewModel.onLoginChange(it, password) }

    //Password(password, isLoginError) { loginViewModel.onLoginChange(userName, it) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row() {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "icono",
                modifier = Modifier
                    .requiredSize(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit // Esto hace que la imagen cubra todo el botón
            )
        }
        Text(
            text = "MyComicList",
            style = MaterialTheme.typography.headlineLarge,
            color = naranja1,
            fontFamily = marvelFont,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de Texto de Usuario
        OutlinedTextField(
            value = userName,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = naranja1,
                unfocusedBorderColor = azul1
            ),
            onValueChange = { loginViewModel.onLoginChange(it, password) },
            label = { Text("Usuario", color = gris1) },
            singleLine = true,
            //isError = isLoginError,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo de Texto de Contraseña
        OutlinedTextField(
            value = password,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = naranja1,
                unfocusedBorderColor = azul1
            ),
            onValueChange = { loginViewModel.onLoginChange(userName, it) },
            label = { Text("Contraseña", color = gris1) },
            singleLine = true,
            visualTransformation = if (passVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { passVisibility = !passVisibility }) {
                    Text(if (passVisibility) "Ocultar" else "Mostrar")
                }
            },
            //isError = isLoginError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        Row() {
            // Botón de Envío
            Button(
                onClick = {
                   /* if (userName.isNotBlank() && password.isNotBlank()) {
                        doLogin(userName)
                    }*/
                    if (userName.isNotBlank() && password.isNotBlank()) {
                        loginViewModel.loginUser { email ->
                            doLogin(email)
                        }
                    } else {
                        Toast.makeText(myContext, "Rellena los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = azul1
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Entrar")
            }
            // Botón de registro
            Button(
                onClick = {
                    //loginViewModel.registerUser()
                    if (userName.isNotBlank() && password.isNotBlank()) {
                        loginViewModel.registerUser { email ->
                            doRegister(email)
                        }
                    } else {
                        Toast.makeText(myContext, "Rellena los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = azul1
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Registrarse")
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Footer()
    }
}

@Composable
fun Footer() {
    Text(
        modifier = Modifier.padding(bottom = 16.dp),
        text = "Created by Daniel López (v:1.0.0)"
    )
}

