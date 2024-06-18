package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CleanLastCardUseCase(private val localDataSource: LocalDataSource) {

    suspend operator fun invoke() = flow {
        localDataSource.apply {
            setMainPictogramId(SaveCardPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setFirstActionPictogramId(SaveCardPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setSecondActionPictogramId(SaveCardPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setAttributePictogramId(SaveCardPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
            setSecondAttributePictogramId(SaveCardPecsIdUseCase.PICTOGRAM_INVALID_ID).collect()
        }
        return@flow emit(Unit)
    }.flowOn(Dispatchers.IO)

}