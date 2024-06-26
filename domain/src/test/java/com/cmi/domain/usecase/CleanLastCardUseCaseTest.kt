package com.cmi.domain.usecase

import com.cmi.domain.system.LocalDataSource
import com.cmi.domain.usecase.SaveCardPecsIdUseCase.Companion.PICTOGRAM_INVALID_ID
import com.cmi.domain.util.FakeLocalDataSource
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CleanLastCardUseCaseTest {

    private lateinit var cleanLastPictogramsUseCase: CleanLastCardUseCase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = FakeLocalDataSource()
        cleanLastPictogramsUseCase = CleanLastCardUseCase(localDataSource)
    }

    @Test
    fun `execute clean last pictograms use case`() = runBlocking {
        FakeLocalDataSource.reset()

        cleanLastPictogramsUseCase.invoke().single()

        assert(localDataSource.getMainPictogramId() == PICTOGRAM_INVALID_ID)
        assert(localDataSource.getFirstActionPictogramId() == PICTOGRAM_INVALID_ID)
        assert(localDataSource.getSecondActionPictogramId() == PICTOGRAM_INVALID_ID)
        assert(localDataSource.getAttributePictogramId() == PICTOGRAM_INVALID_ID)
        assert(localDataSource.getSecondPictogramAttributeId() == PICTOGRAM_INVALID_ID)
    }
}