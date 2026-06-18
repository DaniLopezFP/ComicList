package com.example.mycomiclist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.mycomiclist.data.firestore.FirebaseComicsRepository
import com.example.mycomiclist.data.openlibrary.search.OLSearchApi
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.openlibrary.OLSearchRepository
import com.example.mycomiclist.screens.addedit.AddEditScreen
import com.example.mycomiclist.screens.addedit.AddEditViewModel
import com.example.mycomiclist.screens.comiclist.ComicListScreen
import com.example.mycomiclist.screens.comiclist.ComicListViewModel
import com.example.mycomiclist.screens.home.HomeScreen
import com.example.mycomiclist.screens.home.HomeViewModel
import com.example.mycomiclist.screens.login.LoginScreen
import com.example.mycomiclist.screens.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.serialization.Serializable
import java.util.Map.entry

// --- DESTINOS (Heredando de NavKey de forma estricta) ---
@Serializable
object Login : NavKey

@Serializable
data class ComicList(val userName: String) : NavKey


@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    // 1. Instancias únicas (Singletons)
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val repository = FirebaseComicsRepository(firestore)

    // 3. Definimos el contenedor de navegación (Empezamos en la pantalla de Login)
    NavHost(navController = navController, startDestination = "login") {

        // --- PANTALLA 1: LOGIN ---
        composable("login") {
            val loginViewModel = remember { LoginViewModel(auth) }
            LoginScreen(
                loginViewModel = loginViewModel,
                doLogin = { email ->
                    navController.navigate("home/$email") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                doRegister = { /* ... */ }
            )
        }

        // --- PANTALLA 2: HOME (LISTADO) ---
        // Definimos que recibe un argumento obligatorio: el username (email)
        composable(
            route = "home/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recuperamos el nombre de la ruta de forma segura
            val userName = backStackEntry.arguments?.getString("userName") ?: "Usuario"

            HomeScreen(
                homeViewModel = remember {
                    HomeViewModel(
                        repository = repository,
                        userName = userName,
                        userId = auth.currentUser?.uid ?: "",
                        goToAddEditScreen = { comic ->
                            // Viajamos a la pantalla de añadir/editar pasando el ID del cómic (si no tiene, pasamos "new")
                            val comicId = if (comic.id.isEmpty()) "new" else comic.id
                            navController.navigate("addEdit/$comicId")
                        },
                        goBack = { navController.popBackStack() }
                    )
                }
            )
        }

        // --- PANTALLA 3: ADD / EDIT ---
        composable(
            route = "addEdit/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getString("comicId") ?: "new"
            val placeholderComic = Comic(id = if (comicId == "new") "" else comicId)

            //Instancia del repositorio
            val searchRepository = OLSearchRepository(OLSearchApi.searchService)

            val addEditViewModel = remember {
                AddEditViewModel(
                    inComic = placeholderComic,
                    repository = repository,
                    searchRepository = searchRepository,
                    userId = auth.currentUser?.uid ?: "",
                    navigateBack = { navController.popBackStack() }
                )
            }

            // Llamar a la pantalla pasándole el ViewModel que acabamos de crear
            AddEditScreen(addEditViewModel = addEditViewModel)
        }
    }

    /* Antiguas funciones
    ----------------------------------------------

    // Inicializamos el backstack nativo sin argumentos de tipo extras
    val backStack = rememberNavBackStack(Login)

    NavDisplay(
        backStack = backStack,
        onBack = {
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
    */

}