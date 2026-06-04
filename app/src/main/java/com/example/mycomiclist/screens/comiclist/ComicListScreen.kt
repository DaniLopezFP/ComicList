package com.example.mycomiclist.screens.comiclist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.mycomiclist.ui.theme.azul1
import com.example.mycomiclist.ui.theme.naranja1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicListScreen(viewModel: ComicListViewModel, userName: String) {
    // Nos suscribimos a la lista de cómics del ViewModel
    val comicList by viewModel.comics.observeAsState(initial = emptyList())

    /* Scaffold(
         topBar = {
             TopAppBar(
                 title = {*/
    Column(modifier = Modifier.padding(top = 0.dp)) {
        Text(text = "¡Hola, $userName!", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Mis Cómics", style = MaterialTheme.typography.headlineSmall)
        }

        /*               }
                   )
               }
           ) { paddingValues ->*/
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                //.padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(comicList) { comic ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (comic.isRead) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1. IMAGEN DE PORTADA (A la izquierda, con tamaño fijo y controlado)
                        AsyncImage(
                            model = comic.imageUrl,
                            contentDescription = comic.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(width = 80.dp, height = 120.dp)
                                .padding(end = 12.dp)
                        )

                        // 2. TEXTOS (En el centro, controlando el peso para evitar colapsar el 'measure')
                        Column(
                            modifier = Modifier.weight(1f) // <-- SOLUCIÓN: Absorbe el espacio sobrante de forma segura
                        ) {
                            Text(
                                text = "${comic.title} #${comic.volumeNumber}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = comic.author,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // 3. BOTÓN DE ESTADO (A la derecha)
                        Button(
                            onClick = { viewModel.toggleReadStatus(comic.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (comic.isRead) azul1 else naranja1
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = if (comic.isRead) "Leído" else "Pendiente",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}