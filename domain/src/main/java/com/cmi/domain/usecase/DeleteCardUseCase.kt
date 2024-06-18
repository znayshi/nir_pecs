package com.cmi.domain.usecase

import com.cmi.domain.entity.Card
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteCardUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(pictograms: List<Card>) = flow {
        return@flow localDataSource.deletePictograms(pictograms = pictograms).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}