package com.example.mycomiclist.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.mycomiclist.screens.comiclist.ComicListScreen
import com.example.mycomiclist.screens.comiclist.ComicListViewModel
import com.example.mycomiclist.screens.login.LoginScreen
import com.example.mycomiclist.screens.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable

// --- DESTINOS (Heredando de NavKey de forma estricta) ---
// Asegúrate de que lleva el símbolo '@' y está justo pegado arriba del destino
@Serializable
object Login : NavKey

@Serializable
data class ComicList(val userName: String) : NavKey


@Composable
fun NavigationWrapper() {

    // Inicializamos el backstack nativo sin argumentos de tipo extras
    val backStack = rememberNavBackStack(Login)

    NavDisplay(
        backStack = backStack,
        onBack = {
            // SOLUCIÓN 1 y 2: Volver atrás usando el comportamiento de listas mutables
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryProvider = entryProvider {

            // --- PANTALLA DE LOGIN dentro del entryProvider de tu NavigationWrapper.kt ---
            entry<Login> {
                // Usamos la factoría oficial para que el ViewModel sobreviva al ciclo de vida
                val loginViewModel: LoginViewModel = viewModel {
                    LoginViewModel(FirebaseAuth.getInstance())
                }

                LoginScreen(
                    loginViewModel = loginViewModel,
                    doLogin = { userName ->
                        backStack.add(ComicList(userName))
                    },
                    doRegister = { userName ->
                        backStack.add(ComicList(userName))
                    }
                )
            }
// --- PANTALLA DE LISTA DE CÓMICS dentro del entryProvider de tu NavigationWrapper.kt ---
            // --- PANTALLA DE LISTA DE CÓMICS ---
            entry<ComicList> { destination -> // <-- Nombramos el parámetro directamente como el destino

                // Extraemos el nombre directamente del objeto recibido
                val currentUserName = destination.userName

                val comicListViewModel: ComicListViewModel = viewModel()

                ComicListScreen(
                    viewModel = comicListViewModel,
                    userName = currentUserName
                )
            }
        }
    )
}