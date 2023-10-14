package com.yape.data

import com.yape.data.fakes.FakeRecipes
import com.yape.data.utils.DataBaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.yape.data.mapper.RecipeMapper
import com.yape.data.models.RecipeEntity
import com.yape.data.repository.RecipeDataSource
import com.yape.data.source.RecipeDataSourceFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeRepositoryImpTest : DataBaseTest() {

    @Mock
    lateinit var dataSourceFactory: RecipeDataSourceFactory

    @Mock
    lateinit var recipeMapper: RecipeMapper

    @Mock
    lateinit var dataSource: RecipeDataSource

    private lateinit var sut: RecipeRepositoryImp

    @Before
    fun setUp() {
        sut = RecipeRepositoryImp(dataSourceFactory, recipeMapper)
    }

    @Test
    fun `get recipes with cached true should return character list from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            val isCached = true
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.isCached()) doReturn isCached
            `when`(dataSourceFactory.getDataStore(isCached)) doReturn dataSource
            `when`(
                dataSourceFactory.getDataStore(isCached).getRecipes()
            ) doReturn FakeRecipes.getRecipes()

            // Act (When)
            val recipes = sut.gerRecipes().single()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(dataSourceFactory, times(2)).getCacheDataSource()
            verify(dataSource, times(1)).isCached()
            verify(dataSourceFactory, times(2)).getDataStore(isCached)
            verify(dataSourceFactory.getDataStore(isCached), times(1)).getRecipes()
            verify(recipeMapper, times(2)).mapFromEntity(any())
        }

    @Test
    fun `get recipes with cached true should return character list from local cache and saved the recipes to local db`() =
        dispatcher.runTest {
            // Arrange (Given)
            val isCached = true
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.isCached()) doReturn isCached
            `when`(dataSourceFactory.getDataStore(isCached)) doReturn dataSource
            `when`(
                dataSourceFactory.getDataStore(isCached).getRecipes()
            ) doReturn FakeRecipes.getRecipes()

            // Act (When)
            val recipes = sut.gerRecipes().single()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(dataSourceFactory, times(2)).getCacheDataSource()
            verify(dataSource, times(1)).isCached()
            verify(dataSourceFactory, times(2)).getDataStore(isCached)
            verify(dataSourceFactory.getDataStore(isCached), times(1)).getRecipes()
            verify(recipeMapper, times(2)).mapFromEntity(any())
            verify(recipeMapper, times(2)).mapToEntity(anyOrNull())
            verify(dataSourceFactory.getCacheDataSource(), times(1)).saveRecipes(any())
        }

    @Test
    fun `get recipes with cached false should return character list from remote`() =
        dispatcher.runTest {
            // Arrange (Given)
            val isCached = false
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.isCached()) doReturn isCached
            `when`(dataSourceFactory.getDataStore(isCached)) doReturn dataSource
            `when`(
                dataSourceFactory.getDataStore(isCached).getRecipes()
            ) doReturn FakeRecipes.getRecipes()

            // Act (When)
            val recipes = sut.gerRecipes().single()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(dataSourceFactory, times(2)).getCacheDataSource()
            verify(dataSource, times(1)).isCached()
            verify(dataSourceFactory, times(2)).getDataStore(isCached)
            verify(dataSourceFactory.getDataStore(isCached), times(1)).getRecipes()
            verify(recipeMapper, times(2)).mapFromEntity(any())
        }

    @Test
    fun `get character with id should return character from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.getRecipe(characterId)) doReturn FakeRecipes.getRecipes()[0]
            `when`(recipeMapper.mapFromEntity(any())) doReturn FakeRecipes.getRecipe()

            // Act (When)
            val character = sut.getRecipe(characterId).single()

            // Assert (Then)
            assertEquals(character.id, 1)
            assertEquals(character.name, "Rick")
            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSourceFactory.getCacheDataSource(), times(1)).getRecipe(characterId)
            verify(recipeMapper, times(1)).mapFromEntity(any())
        }

    @Test
    fun `get character with id should return character from remote`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val mockCharacter = mock<RecipeEntity> { RecipeEntity::class.java }
            `when`(mockCharacter.image) doReturn ""
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.getRecipe(characterId)) doReturn mockCharacter
            `when`(dataSourceFactory.getRemoteDataSource()) doReturn dataSource

            // Act (When)
            sut.getRecipe(characterId).single()

            // Assert (Then)
            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSourceFactory.getCacheDataSource(), times(2)).getRecipe(characterId)
            verify(dataSourceFactory, times(1)).getRemoteDataSource()
            verify(dataSourceFactory.getRemoteDataSource(), times(2)).getRecipe(characterId)
            verify(recipeMapper, times(1)).mapFromEntity(any())
        }

    @Test
    fun `get bookmark recipes should return character from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.getBookMarkedRecipes()) doReturn FakeRecipes.getRecipes()

            // Act (When)
            val recipes = sut.getBookMarkedRecipes().single()

            // Assert (Then)
            assertEquals(recipes.size, 2)
            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).getBookMarkedRecipes()
            verify(recipeMapper, times(2)).mapFromEntity(any())
        }

    @Test
    fun `get bookmark recipes should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            whenever(dataSource.getBookMarkedRecipes()) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getBookMarkedRecipes().single()
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )

            }

            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).getBookMarkedRecipes()
        }

    @Test
    fun `set bookmark character should return bookmark status fail`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val mockResult = 0 // Fail result
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.setRecipeBookmarked(characterId)) doReturn mockResult

            // Act (When)
            val bookmarkStatus = sut.setRecipeBookmarked(characterId).single()

            // Assert (Then)
            assertEquals(bookmarkStatus, mockResult)
            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).setRecipeBookmarked(characterId)
        }

    @Test
    fun `set bookmark character should return bookmark status success`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val mockResult = 1
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.setRecipeBookmarked(characterId)) doReturn mockResult

            // Act (When)
            val bookmarkStatus = sut.setRecipeBookmarked(characterId).single()

            // Assert (Then)
            assertEquals(bookmarkStatus, mockResult)
            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).setRecipeBookmarked(characterId)
        }

    @Test
    fun `set bookmark character should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            whenever(dataSource.setRecipeBookmarked(characterId)) doAnswer { throw IOException() }

            // Act (When)

            try{
                sut.setRecipeBookmarked(characterId).single()
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).setRecipeBookmarked(characterId)
        }

    @Test
    fun `set un-bookmark character should return bookmark status fail`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val mockResult = 0 // Fail result
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.setRecipeUnBookMarked(characterId)) doReturn mockResult

            // Act (When)
            val bookmarkStatus = sut.setRecipeUnBookMarked(characterId).single()

            // Assert (Then)
            assertEquals(bookmarkStatus, mockResult)
            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).setRecipeUnBookMarked(characterId)
        }

    @Test
    fun `set un-bookmark character should return bookmark status success`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val mockResult = 1
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            `when`(dataSource.setRecipeUnBookMarked(characterId)) doReturn mockResult

            // Act (When)
            val bookmarkStatus = sut.setRecipeUnBookMarked(characterId).single()

            // Assert (Then)
            assertEquals(bookmarkStatus, mockResult)
            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).setRecipeUnBookMarked(characterId)
        }

    @Test
    fun `set un-bookmark character should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            `when`(dataSourceFactory.getCacheDataSource()) doReturn dataSource
            whenever(dataSource.setRecipeUnBookMarked(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.setRecipeUnBookMarked(characterId).single()
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(dataSourceFactory, times(1)).getCacheDataSource()
            verify(dataSource, times(1)).setRecipeUnBookMarked(characterId)
        }
}
