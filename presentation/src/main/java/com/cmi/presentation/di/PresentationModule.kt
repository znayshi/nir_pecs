package com.cmi.presentation.di

import com.cmi.data.di.DataServiceLocator
import com.cmi.data.local.preferences.SurveyPreferences
import com.cmi.domain.usecase.*
import com.cmi.presentation.config.add.category.AddCategoryViewModel
import com.cmi.presentation.config.add.card.AddCardViewModel
import com.cmi.presentation.config.delete.category.DeleteCategoryViewModel
import com.cmi.presentation.config.delete.card.DeletePictogramViewModel
import com.cmi.presentation.config.edit.SelectCategoryViewModel
import com.cmi.presentation.config.edit.category.EditCategoryViewModel
import com.cmi.presentation.config.edit.card.EditPictogramViewModel
import com.cmi.presentation.config.edit.card.select.SelectPictogramViewModel
import com.cmi.presentation.config.select.category.SelectCategoryForPecsViewModel
import com.cmi.presentation.config.select.card.SelectPictogramForPecsViewModel
import com.cmi.presentation.model.CategoryModel
import com.cmi.presentation.pecs.category.CategoryViewModel
import com.cmi.presentation.pecs.pictogram.PictogramViewModel
import com.cmi.presentation.pecs.tape.TapeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { (itemsPerScreen: Int) ->
        CategoryViewModel(
            itemsPerScreen = itemsPerScreen,
            cleanLastPictogramsUseCase = CleanLastCardUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (itemsPerScreen: Int) ->
        AddCardViewModel(
            itemsPerScreen = itemsPerScreen,
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            addPictogramUseCase = AddPictogramUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        AddCategoryViewModel(
            addCategoryUseCase = AddCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        SelectCategoryViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        EditCategoryViewModel(
            updateCategoryUseCase = UpdateCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        SelectPictogramViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetCardByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        EditPictogramViewModel(
            updatePictogramUseCase = UpdateCardsUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        SelectCategoryForPecsViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            updateCategoriesUseCase = UpdateCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        SelectPictogramForPecsViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetCardByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            updatePictogramsUseCase = UpdateCardUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        DeleteCategoryViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            deleteCategoriesUseCase = DeleteCategoriesUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        DeletePictogramViewModel(
            getPictogramsByCategoryUseCase = GetCardByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            deletePictogramsUseCase = DeleteCardUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel { (categoryModel: CategoryModel) ->
        PictogramViewModel(
            categoryModel = categoryModel,
            getPictogramsByCategoryUseCase = GetCardByCategoryUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            updatePictogramPriorityUseCase = UpdateCardPriorityUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            savePictogramPecsIdUseCase = SaveCardPecsIdUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            ),
            getLastPecsPictogramsUseCase = GetLastPecsCardUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }

    viewModel {
        TapeViewModel(
            getLastPecsPictogramsUseCase = GetLastPecsCardUseCase(
                localDataSource = DataServiceLocator.provideLocalDataSource(androidContext())
            )
        )
    }



}
