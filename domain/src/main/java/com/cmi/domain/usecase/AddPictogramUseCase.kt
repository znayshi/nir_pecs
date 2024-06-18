package com.cmi.domain.usecase

import com.cmi.domain.entity.Card
import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddPictogramUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke(pictogram: Card) = flow {
        return@flow localDataSource.addPictogram(pictogram = pictogram).collect { none ->
            emit(none)
        }
    }.flowOn(Dispatchers.IO)

}