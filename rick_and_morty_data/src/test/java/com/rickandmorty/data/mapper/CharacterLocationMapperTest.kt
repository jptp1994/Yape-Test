package com.rickandmorty.data.mapper


import com.rickandmorty.data.fakes.FakeCharacters
import com.rickandmorty.data.utils.DataBaseTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterLocationMapperTest : DataBaseTest() {

    private lateinit var sut: CharacterLocationMapper

    @Before
    fun setUp() {
        sut = CharacterLocationMapper()
    }

    @Test
    fun `map  location entity to location should return converted location`() =
        dispatcher.runTest {
            // Arrange (Given)
            val locationFake = FakeCharacters.getCharacters()[0].characterLocation

            // Act (When)
            val locationMapper = sut.mapFromEntity(locationFake)

            // Assert (Then)
            assertEquals(locationMapper.name, "Earth")
        }

    @Test
    fun `map  location to location entity should return converted location`() =
        dispatcher.runTest {
            // Arrange (Given)
            val locationFake = FakeCharacters.getCharacter().characterLocation

            // Act (When)
            val locationMapper = sut.mapToEntity(locationFake)

            // Assert (Then)
            assertEquals(locationMapper.name, "Earth")
        }
}
