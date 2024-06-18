package com.cmi.presentation.pecs.tape

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.usecase.GetLastPecsCardUseCase
import com.cmi.domain.usecase.SaveCardPecsIdUseCase
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.toPictogramModel
import kotlinx.coroutines.launch

class TapeViewModel(
    private val getLastPecsPictogramsUseCase: GetLastPecsCardUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<TapeUiState> by lazy {
        MutableLiveData<TapeUiState>()
    }
    val uiState: LiveData<TapeUiState> = _uiState

    init {
        loadInformation()
    }

    private fun loadInformation() = viewModelScope.launch {
        getLastPecsPictogramsUseCase()
            .collect { mapPecs ->
                emitUiState(
                    mainPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_MAIN]?.toPictogramModel(),
                    actionFirstPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_FIRST_ACTION]?.toPictogramModel(),
                    actionSecondPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ACTION]?.toPictogramModel(),
                    attributeFirstPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_ATTRIBUTE]?.toPictogramModel(),
                    attributeSecondPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE]?.toPictogramModel(),
                    launchSound = Any()
                )
            }
    }

    private fun emitUiState(
        mainPictogramModel: PictogramModel? = null,
        actionFirstPictogramModel: PictogramModel? = null,
        actionSecondPictogramModel: PictogramModel? = null,
        attributeFirstPictogramModel: PictogramModel? = null,
        attributeSecondPictogramModel: PictogramModel? = null,
        launchSound: Any? = null
    ) {
        _uiState.value = TapeUiState(
            mainPictogramModel = mainPictogramModel,
            actionFirstPictogramModel = actionFirstPictogramModel,
            actionSecondPictogramModel = actionSecondPictogramModel,
            attributeFirstPictogramModel = attributeFirstPictogramModel,
            attributeSecondPictogramModel = attributeSecondPictogramModel,
            launchSound = launchSound
        )
    }
}

data class TapeUiState(
    val mainPictogramModel: PictogramModel? = null,
    val actionFirstPictogramModel: PictogramModel? = null,
    val actionSecondPictogramModel: PictogramModel? = null,
    val attributeFirstPictogramModel: PictogramModel? = null,
    val attributeSecondPictogramModel: PictogramModel? = null,
    val launchSound: Any? = null
)