package com.yape.remote.mappers

import com.yape.remote.fakes.FakeRemoteData
import com.yape.remote.utils.RemoteBaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.yape.data.models.RecipeEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeEntityMapperTest : RemoteBaseTest() {

    @Mock
    lateinit var mapper: RecipeLocationEntityMapper

    @Mock
    lateinit var mapperIngredient:RecipeIngredientEntityMapper


    lateinit var sut: RecipeEntityMapper

    @Before
    fun setUp() {
        sut = RecipeEntityMapper(mapper,mapperIngredient)
    }

    @Test
    fun `map model to entity should return converted object`() =
        dispatcher.runTest {
            // Arrange (Given)
            val recipeModel = FakeRemoteData.getRecipeModel(false)
            `when`(mapper.mapFromModel(recipeModel.recipeLocation)) doReturn FakeRemoteData.getRecipeEntity(
                false
            ).recipeLocation

            // Act (When)
            val recipeEntity = sut.mapFromModel(recipeModel)

            // Assert (Then)
            assertThat(recipeEntity, instanceOf(RecipeEntity::class.java))
            assertEquals(recipeEntity.id, 1)
            assertTrue(recipeEntity.name.isNotEmpty())
            verify(mapper).mapFromModel(any())
        }
}
