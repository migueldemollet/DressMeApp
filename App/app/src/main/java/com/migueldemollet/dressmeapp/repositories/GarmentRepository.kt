package com.migueldemollet.dressmeapp.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Source
import com.migueldemollet.dressmeapp.model.Garment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GarmentRepository
@Inject
constructor(
    private val garmentList: CollectionReference
){
    fun getGarmentList() : Flow<Result<List<Garment>>> = flow {
        try {
            emit(Result.Loading<List<Garment>>())

            val garmentList = garmentList.get(Source.CACHE).await().map { document ->
                document.toObject(Garment::class.java)
            }

            emit(Result.Success<List<Garment>>(data = garmentList))
        } catch (e: Exception) {
            emit(Result.Error<List<Garment>>(message = e.localizedMessage ?: "Error"))
        }
    }

    fun getGarmentById(id: String) : Flow<Result<Garment>> = flow {
        try {
            emit(Result.Loading<Garment>())

            val garment = garmentList.whereEqualTo("id", id).get(Source.CACHE).await().toObjects(Garment::class.java).first()

            emit(Result.Success<Garment>(data = garment))
        } catch (e: Exception) {
            emit(Result.Error<Garment>(message = e.localizedMessage ?: "Error"))
        }
    }

    fun getGarmentListByFilters(id: String, color: String, type: String) : Flow<Result<List<Garment>>> = flow {
        try {
            emit(Result.Loading<List<Garment>>())
            val garmentList = garmentList
                .whereNotEqualTo("id", id)
                .whereEqualTo("color", color)
                .whereEqualTo("type", type)
                .get(Source.CACHE).await().map { document ->
                    document.toObject(Garment::class.java)
                }

            emit(Result.Success<List<Garment>>(data = garmentList))
        } catch (e: Exception) {
            emit(Result.Error<List<Garment>>(message = e.localizedMessage ?: "Error"))
        }
    }
}