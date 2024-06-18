package com.cmi.presentation.pecs.pictogram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmi.domain.system.PECS
import com.cmi.domain.usecase.GetLastPecsCardUseCase
import com.cmi.domain.usecase.GetCardByCategoryUseCase
import com.cmi.domain.usecase.SaveCardPecsIdUseCase
import com.cmi.domain.usecase.SaveCardPecsIdUseCase.Companion.PICTOGRAM_INVALID_ID
import com.cmi.domain.usecase.SaveCardPecsIdUseCase.Companion.PICTOGRAM_MAIN
import com.cmi.domain.usecase.UpdateCardPriorityUseCase
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.model.PictogramModel
import com.cmi.presentation.model.mapper.getPictogramsMapFormat
import com.cmi.presentation.model.mapper.toPictogram
import com.cmi.presentation.model.mapper.toPictogramModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

open class PictogramViewModel(
    private val categoryModel: CategoryModel,
    private val getPictogramsByCategoryUseCase: GetCardByCategoryUseCase,
    private val updatePictogramPriorityUseCase: UpdateCardPriorityUseCase,
    private val savePictogramPecsIdUseCase: SaveCardPecsIdUseCase,
    private val getLastPecsPictogramsUseCase: GetLastPecsCardUseCase
) : ViewModel() {

    private val _pictograms: MutableLiveData<Map<Int, List<PictogramModel>>> by lazy {
        MutableLiveData<Map<Int, List<PictogramModel>>>().also {
            it.value = emptyMap()
        }
    }
    val pictograms: LiveData<Map<Int, List<PictogramModel>>> = _pictograms

    private val _uiState: MutableLiveData<PictogramUiState> by lazy {
        MutableLiveData<PictogramUiState>()
    }
    val uiState: LiveData<PictogramUiState> = _uiState

    init {
        getPictogramsByCategory()
    }

    private fun getPictogramsByCategory() = viewModelScope.launch {
        getPictogramsByCategoryUseCase(categoryId = categoryModel.categoryId ?: 0)
            .catch { throwable ->
                Timber.e(throwable)
            }.collect { pictograms ->
                _pictograms.value = getPictogramsMapFormat(
                    screenItems = PECS.PICTOGRAMS_IN_SCREEN,
                    items = pictograms.filter { it.isSelected == true }
                        .map { it.toPictogramModel() }
                )
            }
    }

    fun updatePictogramPriority(pictogramModel: PictogramModel) = viewModelScope.launch {
        updatePictogramPriorityUseCase(pictogram = pictogramModel.toPictogram()).collect()
    }


    // Main
    fun saveMainPictogram(pictogramModel: PictogramModel) = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            PICTOGRAM_MAIN,
            pictogramId = pictogramModel.pictogramId ?: PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun removeMainPictogram() = viewModelScope.launch {
        savePictogramPecsIdUseCase(PICTOGRAM_MAIN, pictogramId = PICTOGRAM_INVALID_ID).collect()
    }

    // Actions
    fun saveFirstActionPictogram(pictogramModel: PictogramModel) = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_FIRST_ACTION,
            pictogramId = pictogramModel.pictogramId ?: PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun removeFirstActionPictogram() = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_FIRST_ACTION,
            pictogramId = PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun saveSecondActionPictogram(pictogramModel: PictogramModel) = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ACTION,
            pictogramId = pictogramModel.pictogramId ?: PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun removeSecondActionPictogram() = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ACTION,
            pictogramId = PICTOGRAM_INVALID_ID
        ).collect()
    }


    //Attributes
    fun saveFirstAttributePictogram(pictogramModel: PictogramModel) = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_ATTRIBUTE,
            pictogramId = pictogramModel.pictogramId ?: PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun removeFirstAttributePictogram() = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_ATTRIBUTE,
            pictogramId = PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun saveSecondAttributePictogram(pictogramModel: PictogramModel) = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE,
            pictogramId = pictogramModel.pictogramId ?: PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun removeSecondAttributePictogram() = viewModelScope.launch {
        savePictogramPecsIdUseCase(
            SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE,
            pictogramId = PICTOGRAM_INVALID_ID
        ).collect()
    }

    fun loadInformation() = viewModelScope.launch {
        emitUiState()
        getLastPecsPictogramsUseCase()
            .collect { mapPecs ->
                emitUiState(
                    mainPictogramModel = mapPecs[PICTOGRAM_MAIN]?.toPictogramModel(),
                    actionFirstPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_FIRST_ACTION]?.toPictogramModel(),
                    actionSecondPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ACTION]?.toPictogramModel(),
                    attributeFirstPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_ATTRIBUTE]?.toPictogramModel(),
                    attributeSecondPictogramModel = mapPecs[SaveCardPecsIdUseCase.PICTOGRAM_SECOND_ATTRIBUTE]?.toPictogramModel()
                )
            }
    }

    private fun emitUiState(
        mainPictogramModel: PictogramModel? = null,
        actionFirstPictogramModel: PictogramModel? = null,
        actionSecondPictogramModel: PictogramModel? = null,
        attributeFirstPictogramModel: PictogramModel? = null,
        attributeSecondPictogramModel: PictogramModel? = null
    ) {
        _uiState.value = PictogramUiState(
            mainPictogramModel = mainPictogramModel,
            actionFirstPictogramModel = actionFirstPictogramModel,
            actionSecondPictogramModel = actionSecondPictogramModel,
            attributeFirstPictogramModel = attributeFirstPictogramModel,
            attributeSecondPictogramModel = attributeSecondPictogramModel
        )
    }
}

data class PictogramUiState(
    val mainPictogramModel: PictogramModel? = null,
    val actionFirstPictogramModel: PictogramModel? = null,
    val actionSecondPictogramModel: PictogramModel? = null,
    val attributeFirstPictogramModel: PictogramModel? = null,
    val attributeSecondPictogramModel: PictogramModel? = null
)
