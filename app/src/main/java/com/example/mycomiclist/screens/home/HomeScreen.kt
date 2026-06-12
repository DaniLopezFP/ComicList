package com.example.mycomiclist.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.ui.theme.naranja1
import com.example.mycomiclist.ui.theme.naranjaLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    // 🌟 Recopilamos los flujos asíncronos transformándolos en estados de Compose
    val contentIndex by homeViewModel.contentIndex.collectAsStateWithLifecycle()
    val comicList by homeViewModel.comicList.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Cómics (${homeViewModel.userName})") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeViewModel.goToAddEditScreen(Comic())
                }, containerColor = naranja1
            ){
                Icon(Icons.Default.Add, contentDescription = "Añadir Cómic")
            }
        },
        bottomBar = {
            // Tu componente HomeNavigationBar pasándole (contentIndex)
            // Cuando cambie, llamará a homeViewModel.changeIndex(nuevoIndex)
        }
    ) { innerPadding ->
        // Componente encargado de pintar la lista (ej: tu LazyVerticalGrid)
        ComicContent(
            paddingValues = innerPadding,
            comics = comicList,
            onComicClick = { selectedComic ->
                homeViewModel.goToAddEditScreen(selectedComic) // Abre el formulario para editarlo
            }
        )
    }
}

@Composable
fun ComicContent(paddingValues: PaddingValues, comics: List<Comic>, onComicClick: (Comic) -> Unit) {
    // Aquí implementas tu estructura visual de diseño (LazyColumn o LazyVerticalGrid)
    // iterando sobre la lista reactiva de 'comics' que viene de Firestore.
}