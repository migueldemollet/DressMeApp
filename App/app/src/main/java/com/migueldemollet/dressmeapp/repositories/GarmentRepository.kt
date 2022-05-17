package com.migueldemollet.dressmeapp.repositories

import com.google.firebase.firestore.CollectionReference
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

            val garmentList = garmentList.get().await().map { document ->
                document.toObject(Garment::class.java)
            }

            emit(Result.Success<List<Garment>>(data = garmentList))
        } catch (e: Exception) {
            emit(Result.Error<List<Garment>>(message = e.localizedMessage ?: "Error"))
        }
    }
}