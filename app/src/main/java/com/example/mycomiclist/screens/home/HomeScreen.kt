package com.example.mycomiclist.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.ui.theme.azul1
import com.example.mycomiclist.ui.theme.azulLight
import com.example.mycomiclist.ui.theme.grisLight
import com.example.mycomiclist.ui.theme.naranja1
import com.example.mycomiclist.ui.theme.naranjaLight
import com.example.mycomiclist.ui.theme.rojo1
import com.example.mycomiclist.ui.theme.verde1
import com.example.mycomiclist.ui.theme.verdeLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val contentIndex by homeViewModel.contentIndex.collectAsStateWithLifecycle()
    val comicList by homeViewModel.comicList.collectAsStateWithLifecycle()

    //Valores del usuario
    val stats by homeViewModel.userStats.collectAsStateWithLifecycle()

    //Colores texto
    val rainbowColors: List<Color> =
        listOf(rojo1, azul1, verde1, naranja1)

    val brush = remember {
        Brush.linearGradient(
            colors = rainbowColors
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                //colors = TopAppBarDefaults.topAppBarColors(containerColor = grisLight),
                title = {
                    val shortName = homeViewModel.userName.substringBefore("@")
                    //Text("Mis Cómics (${homeViewModel.userName})")
                    Row(){
                        Text(
                            "Mis Cómics: ",
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Text("$shortName",
                            style = TextStyle(brush = brush, fontSize = 20.sp)
                        )
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeViewModel.goToAddEditScreen(Comic())
                }, containerColor = naranja1
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Cómic")
            }
        },
        bottomBar = {
            Row(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = "Último acceso: ${stats.lastConnection}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 15.sp,
                    color = Color.White
                )
                // Tu componente HomeNavigationBar pasándole (contentIndex)
                // Cuando cambie, llamará a homeViewModel.changeIndex(nuevoIndex)
            }
        },
        containerColor = azul1
    ) { innerPadding ->
        // Componente encargado de pintar la lista (ej: tu LazyVerticalGrid)
        ComicContent(
            paddingValues = innerPadding,
            comics = comicList,
            onComicClick = { selectedComic ->
                homeViewModel.goToAddEditScreen(selectedComic) // Abre el formulario para editarlo
            },
            onToggleRead = { comicSeleccionado ->
                // 🌟 Llamamos a la función del ViewModel que actualiza Firestore
                // Le pasamos el ID del usuario actual y el objeto modificado
                homeViewModel.toggleComicReadStatus(homeViewModel.userId, comicSeleccionado)
            }
        )
    }
}

@Composable
fun ComicContent(
    paddingValues: PaddingValues,
    comics: List<Comic>,
    onComicClick: (Comic) -> Unit,
    onToggleRead: (Comic) -> Unit, //Para cambiar a leído/pendiente
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues) // 🌟 IMPORTANTE: Evita que el contenido se solape con barras o botones
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(comics) { comic ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (comic.isRead) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 🌟 IMAGEN DE PORTADA CON COIL (Usa la URL nueva del formulario)
                    AsyncImage(
                        model = comic.imageUrl,
                        contentDescription = comic.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clickable {
                                // 🌟 Al pulsar la tarjeta, disparamos el evento hacia arriba para editar
                                onComicClick(comic)
                            },
                        placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                        error = painterResource(id = android.R.drawable.ic_delete)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // --- Txtos de los comics
                    Text(
                        text = "${comic.title} #${comic.volumeNumber}",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        minLines = 2
                    )

                    Text(
                        text = comic.author,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            onToggleRead(comic)
                        },
                        shape = MaterialTheme.shapes.small,
                        // 🌟 Configuramos los colores del contenedor del botón de forma dinámica
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (comic.isRead) azulLight else verdeLight
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 6.dp)
                    ) {
                        Text(
                            text = if (comic.isRead) "Leído" else "Pendiente",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = if (comic.isRead) Color.Black else azul1
                        )
                    }
                }
            }
        }
    }
}