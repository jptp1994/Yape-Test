package com.yape.data.source

import com.yape.data.utils.DataBaseTest
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.yape.data.repository.RecipeCache
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeDataSourceFactoryTest : DataBaseTest() {

    @Mock
    lateinit var recipeCache: RecipeCache

    @Mock
    lateinit var dataSourceCache: RecipeCacheDataSource

    @Mock
    lateinit var dataSourceRemote: RecipeRemoteDataSource

    private lateinit var sut: RecipeDataSourceFactory

    @Before
    fun setUp() {
        sut = RecipeDataSourceFactory(recipeCache, dataSourceCache, dataSourceRemote)
    }

    @Test
    fun `get data store with cache should return recipes from cache data-source`() =
        dispatcher.runTest {
            // Arrange (Given)
            val isCached = true
            `when`(recipeCache.isExpired()) doReturn false
            // Act (When)
            val dataSource = sut.getDataStore(isCached)
            // Assert (Then)
            assertThat(dataSource, instanceOf(RecipeCacheDataSource::class.java))
            verify(recipeCache, times(1)).isExpired()
        }

    @Test
    fun `get data store with cache should return recipes from remote data-source`() =
        dispatcher.runTest {
            // Arrange (Given)
            val isCached = true
            `when`(recipeCache.isExpired()) doReturn true
            // Act (When)
            val dataSource = sut.getDataStore(isCached)
            // Assert (Then)
            assertThat(dataSource, instanceOf(RecipeRemoteDataSource::class.java))
            verify(recipeCache, times(1)).isExpired()
        }
}
