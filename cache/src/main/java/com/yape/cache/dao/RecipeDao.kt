package com.yape.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yape.cache.models.RecipeCacheEntity

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe")
    fun getRecipes(): List<RecipeCacheEntity>

    @Query("SELECT * FROM recipe WHERE  id = :id")
    fun getRecipe(id: Long): RecipeCacheEntity

    @Query("SELECT * FROM recipe WHERE is_bookmarked = 1")
    fun getBookMarkedRecipe(): List<RecipeCacheEntity>

    @Query("DELETE FROM recipe")
    fun clearRecipes(): Int

    @Query("UPDATE recipe SET is_bookmarked = 1 WHERE id = :id")
    fun bookmarkRecipe(id: Long): Int

    @Query("UPDATE recipe SET is_bookmarked = 0 WHERE id = :id")
    fun unBookmarkRecipe(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(vararg recipe: RecipeCacheEntity)
}
