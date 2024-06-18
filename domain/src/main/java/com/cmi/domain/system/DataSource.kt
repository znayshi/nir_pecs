package com.cmi.domain.system

import com.cmi.domain.entity.Category
import com.cmi.domain.entity.Card
import kotlinx.coroutines.flow.Flow

sealed interface DataSource

interface LocalDataSource : DataSource {
    suspend fun getCategories(): Flow<List<Category>>

    suspend fun getPictogramsByCategory(categoryId: Int): Flow<List<Card>>

    suspend fun getPictograms(): Flow<List<Card>>

    suspend fun getPictogram(pictogramId: Int): Card

    suspend fun addPictogram(pictogram: Card): Flow<Unit>

    suspend fun addCategory(category: Category): Flow<Unit>

    suspend fun updatePictogram(pictogram: Card): Flow<Unit>

    suspend fun updateCategory(category: Category): Flow<Unit>

    fun getMainPictogramId(): Int
    fun setMainPictogramId(pictogramId: Int): Flow<Unit>

    fun getFirstActionPictogramId(): Int
    fun setFirstActionPictogramId(pictogramId: Int): Flow<Unit>

    fun getSecondActionPictogramId(): Int
    fun setSecondActionPictogramId(pictogramId: Int): Flow<Unit>

    fun getAttributePictogramId(): Int
    fun setAttributePictogramId(pictogramId: Int): Flow<Unit>

    fun getSecondPictogramAttributeId(): Int
    fun setSecondAttributePictogramId(pictogramId: Int): Flow<Unit>

    suspend fun updateCategories(categories: List<Category>) : Flow<Unit>

    suspend fun updatePictograms(pictograms: List<Card>) : Flow<Unit>

    suspend fun deletePictograms(pictograms: List<Card>) : Flow<Unit>

    suspend fun deleteCategories(categories: List<Category>): Flow<Unit>
}