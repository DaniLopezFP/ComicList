package com.example.mycomiclist.screens.addedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.ui.theme.azul1
import com.example.mycomiclist.ui.theme.azulLight
import com.example.mycomiclist.ui.theme.grisLight
import com.example.mycomiclist.ui.theme.naranja1
import com.example.mycomiclist.ui.theme.naranjaLight



@Composable
fun AddEditScreen(addEditViewModel: AddEditViewModel) {
    val comic by addEditViewModel.comic.observeAsState(Comic())
    val isError by addEditViewModel.isError.observeAsState(false)
    val isNew = addEditViewModel.isNewComic

    // Recuperamos el estado de carga y error de la API
    val apiUIState = addEditViewModel.apiUIState
    Scaffold(topBar = {
    },
        floatingActionButton = {
        },
        bottomBar = {
        },
        containerColor = grisLight
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Campo: Título
                    TextField(
                        modifier = Modifier
                            //.fillMaxWidth()
                            .weight(4f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = naranjaLight, // Fondo cuando está seleccionado
                            unfocusedContainerColor = azulLight, // Fondo cuando NO está seleccionado
                        ),
                        value = comic.title,
                        onValueChange = { addEditViewModel.updateComicState(comic.copy(title = it)) },
                        label = { Text("ISBN / Título") },
                        // Si la validación local falla o la API da error, se marca en rojo
                        isError = isError || apiUIState is AddEditViewModel.ApiComicUIState.Error
                    )
// Contenedor del botón e indicador de carga superpuesto
                   Box(contentAlignment = Alignment.Center, modifier = Modifier.wrapContentSize()) {

                        Button(
                            modifier = Modifier.size(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = naranjaLight),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = { addEditViewModel.searchComicByIsbn() } //Llama a la API
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar en API",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Si la red está cargando, dibuja el círculo de progreso
                        if (apiUIState is AddEditViewModel.ApiComicUIState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(36.dp),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
                // TEXTO DE ERROR INFERIOR EN ROJO SI FALLA LA BÚSQUEDA
                if (apiUIState is AddEditViewModel.ApiComicUIState.Error) {
                    Text(
                        text = "Error: El ISBN introducido no existe",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                // Campo: Autor
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = naranjaLight, // Fondo cuando está seleccionado
                        unfocusedContainerColor = azulLight, // Fondo cuando NO está seleccionado
                    ),
                    value = comic.author,
                    onValueChange = { addEditViewModel.updateComicState(comic.copy(author = it)) },
                    label = { Text("Autor") },
                    isError = isError
                )

                // Campo: Número de volumen
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = naranjaLight, // Fondo cuando está seleccionado
                        unfocusedContainerColor = azulLight, // Fondo cuando NO está seleccionado
                    ),
                    value = if (comic.volumeNumber == null) "" else comic.volumeNumber.toString(),
                    onValueChange = {
                        if (it.isEmpty()) {
                            // Si el usuario borra el campo, evitamos toInt() mandando un null provisional
                            addEditViewModel.updateComicState(comic.copy(volumeNumber = 0)) // O define Int? en tu modelo
                        } else {
                            addEditViewModel.updateComicState(
                                comic.copy(
                                    volumeNumber = it.toIntOrNull() ?: 0
                                )
                            )
                        }
                    },
                    label = { Text("Número de Volumen") },
                    isError = isError
                )

                //Campo url imagen:
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = naranjaLight, // Fondo cuando está seleccionado
                        unfocusedContainerColor = azulLight, // Fondo cuando NO está seleccionado
                    ),
                    value = comic.imageUrl,
                    onValueChange = { addEditViewModel.updateComicState(comic.copy(imageUrl = it)) },
                    label = { Text("Url imagen") },
                    isError = isError
                )

                Spacer(modifier = Modifier.weight(1f))

                // --- Línea de botones: SAVE, BACK, Delete ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Botón VOLVER
                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = azul1,
                            contentColor = Color.White,
                        ),
                        onClick = { addEditViewModel.navigateBack() }
                    ) {
                        Text("BACK", fontSize = 16.sp)
                    }

                    // Botón GUARDAR (Decide si crea o edita)
                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = azul1,
                            contentColor = Color.White,
                        ),
                        onClick = {
                            if (isNew) addEditViewModel.addComicFirebase()
                            else addEditViewModel.updateComicFirebase()
                        }
                    ) {
                        Text("SAVE", fontSize = 16.sp)
                    }

                    // Botón ELIMINAR (Solo activo si el cómic ya existía en Firestore)
                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = azul1,
                            contentColor = Color.White,
                        ),
                        enabled = !isNew,
                        shape = RoundedCornerShape(20.dp),
                        onClick = { addEditViewModel.deleteComicFirebase() }
                    ) {
                        Text("DELETE", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}