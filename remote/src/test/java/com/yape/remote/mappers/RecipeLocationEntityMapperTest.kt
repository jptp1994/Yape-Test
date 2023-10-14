package com.yape.remote.mappers

import com.yape.remote.fakes.FakeRemoteData
import com.yape.remote.utils.RemoteBaseTest
import com.yape.data.models.RecipeLocationEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeLocationEntityMapperTest : RemoteBaseTest() {

    lateinit var sut: RecipeLocationEntityMapper

    @Before
    fun setUp() {
        sut = RecipeLocationEntityMapper()
    }

    @Test
    fun `map model to entity should return converted object`() =
        dispatcher.runTest {
            // Arrange (Given)
            val locationModel = FakeRemoteData.getRecipeModel(false).recipeLocation

            // Act (When)
            val locationEntity = sut.mapFromModel(locationModel)

            // Assert (Then)
            assertThat(locationEntity, instanceOf(RecipeLocationEntity::class.java))
        }
}
