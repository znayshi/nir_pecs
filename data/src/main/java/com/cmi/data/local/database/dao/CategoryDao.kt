package com.cmi.data.local.database.dao

import androidx.room.*
import android.util.Log
import com.cmi.data.local.database.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category ORDER BY priority DESC")
    suspend fun getCategoriesEntities(): List<CategoryEntity>

    @Query("UPDATE category SET priority=:newPriority WHERE categoryId=:categoryId")
    suspend fun updateCategoryPriority(categoryId: Int, newPriority: Int)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateCategories(categoriesEntities: List<CategoryEntity>)

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category")
    fun getCategories(): List<CategoryEntity> {
        val categories = queryCategories()
        Log.d("CategoryDao", "Loaded categories: $categories")
        return categories
    }
    @Query("SELECT * FROM category")
    fun queryCategories(): List<CategoryEntity>
}