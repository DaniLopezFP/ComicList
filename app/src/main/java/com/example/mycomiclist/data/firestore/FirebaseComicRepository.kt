package com.example.mycomiclist.data.firestore

import com.example.mycomiclist.domain.model.Comic
import com.example.mycomiclist.domain.repository.ComicRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirebaseComicsRepository(
    private val firestore: FirebaseFirestore
) : ComicRepository {
    // 1. LEER CÓMICS (Estructura de flujo continuo)
    override fun getAllComics(userId: String): Flow<List<Comic>> {
        return firestore.collection("users")
            .document(userId)
            .collection("comics")
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(Comic::class.java)
            }
    }

    // 2. AÑADIR CÓMIC
    override suspend fun addComic(userId: String, comic: Comic) {
        // Generamos un documento vacío para obtener un ID automático único
        val docRef = firestore.collection("users")
            .document(userId)
            .collection("comics")
            .document()

        // Creamos la copia del cómic inyectándole ese ID autogenerado
        val comicWithId = comic.copy(id = docRef.id)

        // Guardamos en la nube y esperamos (.await) en segundo plano
        docRef.set(comicWithId).await()
    }

    // 3. ACTUALIZAR CÓMIC (O cambiar el estado de Leído/Pendiente)
    override suspend fun updateComic(userId: String, comic: Comic) {
        firestore.collection("users")
            .document(userId)
            .collection("comics")
            .document(comic.id) // Buscamos el documento por el ID existente
            .set(comic) // Reemplaza los datos en Firestore
            .await()
    }

    // 4. ELIMINAR CÓMIC
    override suspend fun deleteComic(userId: String, comicId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("comics")
            .document(comicId)
            .delete() // Borra el documento del servidor
            .await()
    }
}