package com.yape.cache

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yape.cache.fakes.FakeCacheData
import com.yape.cache.utils.CacheBaseTest
import com.yape.cache.mapper.RecipeCacheMapper
import com.yape.cache.mapper.RecipeIngredientCacheMapper
import com.yape.cache.mapper.RecipeLocationCacheMapper
import com.yape.cache.utils.CachePreferencesHelper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class RecipeCacheImpTest : CacheBaseTest() {

    private val locationMapper = RecipeLocationCacheMapper()
    private val ingredientMapper = RecipeIngredientCacheMapper()
    private val recipeCacheMapper = RecipeCacheMapper(locationMapper,ingredientMapper)
    private lateinit var preferencesHelper: CachePreferencesHelper
    private lateinit var sut: RecipeCacheImp

    @Before
    override fun setup() {
        super.setup()
        preferencesHelper = CachePreferencesHelper(context)
        sut = RecipeCacheImp(charaterDao, recipeCacheMapper, preferencesHelper)
    }

    @Test
    fun `get recipes should return success recipes from local room cache`() =
        testScope.runTest {
            // Arrange (Given)
            val recipeEntity = FakeCacheData.getFakeRecipeEntity(7)
            // Saving recipes to database so we can get it when select query executes
            sut.saveRecipes(recipeEntity)

            // Act (When)
            val recipes = sut.getRecipes()

            // Assert (Then)
            assertEquals(recipes.size, 7)
        }

    @Test
    fun `get recipes should return success recipes from local room cache with empty list`() =
        testScope.runTest{
            // Arrange (Given) no arrange

            // Act (When)
            val recipes = sut.getRecipes()

            // Assert (Then)
            assertTrue(recipes.isEmpty())
        }

    @Test
    fun `get character with specific id should return success character from local room cache`() =
        testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            val characterEntity = FakeCacheData.getFakeRecipeEntity(1, false)
            // Saving recipes to database so we can get it when select query executes
            sut.saveRecipes(characterEntity)

            // Act (When)
            val character = sut.getRecipe(characterId)

            // Assert (Then)
            assertNotNull(character)
            assertEquals(character.id, 1)
        }

    @Test
    fun `save character should return saved recipes from local room cache`() =
        testScope.runTest {
            // Arrange (Given)
            val characterEntity = FakeCacheData.getFakeRecipeEntity(7)
            // Act (When)
            sut.saveRecipes(characterEntity)
            val recipes = sut.getRecipes()
            // Assert (Then)
            assertEquals(recipes.size, 7)
        }

    @Test
    fun `get bookmark recipes should return success bookmarked recipes from local room cache`() =
        testScope.runTest {
            // Arrange (Given)
            // Saving recipes to database so we can get it when select query executes
            val characterEntity =
                FakeCacheData.getFakeRecipeEntity(size = 7, isBookmarked = true)
            sut.saveRecipes(characterEntity)

            // Act (When)
            val bookmarkrecipes = sut.getBookMarkedRecipes()

            // Assert (Then)
            assertEquals(bookmarkrecipes.size, 7)
        }

    @Test
    fun `get bookmark recipes should return success bookmarked recipes from local room cache with empty list`() =
        testScope.runTest {
            // Arrange (Given) no arrange

            // Act (When)
            val recipes = sut.getBookMarkedRecipes()

            // Assert (Then)
            assertTrue(recipes.isEmpty())
        }

    @Test
    fun `set bookmark with specific id should return success status from local room cache`() =
        testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            // Saving recipes to database so we can get it when select query executes
            val characterEntity = FakeCacheData.getFakeRecipeEntity(1, false)
            sut.saveRecipes(characterEntity)

            // Act (When)
            val bookmarkStatus = sut.setRecipeBookmarked(characterId)

            // Assert (Then)
            assertEquals(bookmarkStatus, 1)
        }

    @Test
    fun `set bookmark with specific id should return fail status from local room cache`() =
        testScope.runTest{
            // Arrange (Given)
            val characterId = 1L

            // Act (When)
            val bookmarkStatus = sut.setRecipeBookmarked(characterId)

            // Assert (Then)
            assertEquals(bookmarkStatus, 0)
        }

    @Test
    fun `set un-bookmark with specific id should return success status from local room cache`() =
        testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            // Saving recipes to database so we can get it when select query executes
            val characterEntity = FakeCacheData.getFakeRecipeEntity(1, false)
            sut.saveRecipes(characterEntity)

            // Act (When)
            val bookmarkStatus = sut.setRecipeUnBookMarked(characterId)

            // Assert (Then)
            assertEquals(bookmarkStatus, 1)
        }

    @Test
    fun `set un-bookmark with specific id should return fail status from local room cache`() =
        testScope.runTest {
            // Arrange (Given)
            val characterId = 1L

            // Act (When)
            val bookmarkStatus = sut.setRecipeUnBookMarked(characterId)

            // Assert (Then)
            assertEquals(bookmarkStatus, 0)
        }

    @Test
    fun `is cached should return success true`() =
        testScope.runTest {
            // Arrange (Given)
            // Saving recipes to database so we can get it when select query executes
            val characterEntity = FakeCacheData.getFakeRecipeEntity(1, false)
            sut.saveRecipes(characterEntity)

            // Act (When)
            val isCached = sut.isCached()

            // Assert (Then)
            assertTrue(isCached)
        }

    @Test
    fun `is cached should return success false`() =
        testScope.runTest {
            // Arrange (Given)
            // Act (When)
            val isCached = sut.isCached()

            // Assert (Then)
            assertFalse(isCached)
        }

    @Test
    fun `set last cached time should return saved time`() =
        testScope.runTest {
            // Arrange (Given)
            val time = 1000L
            // Act (When)
            sut.setLastCacheTime(time)
            val lastCacheTime = preferencesHelper.lastCacheTime

            // Assert (Then)
            assertEquals(lastCacheTime, lastCacheTime)
        }

    @Test
    fun `is expired cache time cached true`() =
        testScope.runTest {
            // Arrange (Given)
            // Act (When)
            val isExpired = sut.isExpired()

            // Assert (Then)
            assertTrue(isExpired)
        }

    @Test
    fun `is expired cache time cached false`() =
        testScope.runTest {
            // Arrange (Given)
            val time = System.currentTimeMillis()
            sut.setLastCacheTime(time)
            // Act (When)

            val isExpired = sut.isExpired()

            // Assert (Then)
            assertFalse(isExpired)
        }
}
