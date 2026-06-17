package com.example.mycomiclist.screens.addedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.ui.theme.azul1
import com.example.mycomiclist.ui.theme.azulLight
import com.example.mycomiclist.ui.theme.naranja1
import com.example.mycomiclist.ui.theme.naranjaLight

@Composable
fun AddEditScreen(addEditViewModel: AddEditViewModel) {
    val comic by addEditViewModel.comic.observeAsState(Comic())
    val isError by addEditViewModel.isError.observeAsState(false)
    val isNew = addEditViewModel.isNewComic

    // Recuperamos el estado de carga y error de la API
    val apiUIState = addEditViewModel.apiUIState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo: Título
        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = naranjaLight, // Fondo cuando está seleccionado
                unfocusedContainerColor = azulLight, // Fondo cuando NO está seleccionado
            ),
            value = comic.title,
            onValueChange = { addEditViewModel.updateComicState(comic.copy(title = it)) },
            label = { Text("Título") },
            isError = isError
        )

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

        // --- BOTONERA TRIPLE ---
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