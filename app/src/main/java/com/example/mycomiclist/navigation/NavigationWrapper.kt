package com.example.mycomiclist.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mycomiclist.screens.comiclist.ComicListScreen
import com.example.mycomiclist.screens.comiclist.ComicListViewModel
import com.example.mycomiclist.screens.login.LoginScreen
import com.example.mycomiclist.screens.login.LoginViewModel
import kotlinx.serialization.Serializable

// 1. DEFINIMOS LOS DESTINOS (Tienen que llevar @Serializable obligatoriamente)
@Serializable
object Login

@Serializable
data class ComicList(val userName: String)

@Composable
fun NavigationWrapper() {
    // 2. Creamos el controlador oficial de navegación de Android
    val navController = rememberNavController()

    // 3. NavHost es el nuevo contenedor (el sustituto definitivo de NavDisplay)
    NavHost(
        navController = navController,
        startDestination = Login
    ) {

        // PANTALLA DE LOGIN
        composable<Login> {
            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(loginViewModel = loginViewModel) { txtUser ->
                // Así se navega oficialmente a la siguiente pantalla pasando el parámetro
                navController.navigate(ComicList(userName = txtUser))
            }
        }

        // PANTALLA DE LISTA DE CÓMICS
        composable<ComicList> { backStackEntry ->
            // Recuperamos los datos del destino de forma segura y tipada
            val destination = backStackEntry.toRoute<ComicList>()
            val currentUserName = destination.userName

            val comicListViewModel: ComicListViewModel = viewModel()

            ComicListScreen(
                viewModel = comicListViewModel,
                userName = currentUserName
            )
        }
    }
}