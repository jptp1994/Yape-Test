package com.rickandmorty.data.source

import com.rickandmorty.data.fakes.FakeCharacters
import com.rickandmorty.data.utils.DataBaseTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rickandmorty.data.repository.CharacterCache
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
import java.io.IOException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterCacheDataSourceTest : DataBaseTest() {

    @Mock
    lateinit var characterCache: CharacterCache

    private lateinit var sut: CharacterCacheDataSource

    @Before
    fun setUp() {
        sut = CharacterCacheDataSource(characterCache)
    }

    @Test
    fun `get characters should return characters from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(characterCache.getCharacters()) doReturn FakeCharacters.getCharacters()

            // Act (When)
            val characters = sut.getCharacters()

            // Assert (Then)
            assertEquals(characters.size, 2)
            verify(characterCache, times(1)).getCharacters()
        }

    @Test
    fun `get characters should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(characterCache.getCharacters()) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getCharacters()
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(characterCache, times(1)).getCharacters()
        }

    @Test
    fun `get character with character-id should return characters from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            `when`(characterCache.getCharacter(characterId)) doReturn FakeCharacters.getCharacters()[0]

            // Act (When)
            val characters = sut.getCharacter(characterId)

            // Assert (Then)
            assertEquals(characters.name, "Rick")
            verify(characterCache, times(1)).getCharacter(characterId)
        }

    @Test
    fun `get character with character-id should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(characterCache.getCharacter(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getCharacter(characterId)
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(characterCache, times(1)).getCharacter(characterId)
        }

    @Test
    fun `save character passed character list should save character into local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characters = FakeCharacters.getCharacters()
            // Act (When)
            sut.saveCharacters(characters)

            // Assert (Then)
            verify(characterCache, times(1)).saveCharacters(characters)
            verify(characterCache, times(1)).setLastCacheTime(any())
        }

    @Test
    fun `save character passed character list should return error failed to save last time`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characters = FakeCharacters.getCharacters()
            whenever(characterCache.saveCharacters(characters)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.saveCharacters(characters)
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(characterCache, times(1)).saveCharacters(characters)
            verify(characterCache, times(0)).setLastCacheTime(any())
        }

    @Test
    fun `get bookmark characters should return characters from local cache`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(characterCache.getBookMarkedCharacters()) doReturn FakeCharacters.getCharacters()

            // Act (When)
            val characters = sut.getBookMarkedCharacters()

            // Assert (Then)
            assertEquals(characters.size, 2)
            verify(characterCache, times(1)).getBookMarkedCharacters()
        }

    @Test
    fun `get bookmark characters should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(characterCache.getBookMarkedCharacters()) doAnswer { throw IOException() }


            // Act (When)
            try{
                sut.getBookMarkedCharacters()
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }


            verify(characterCache, times(1)).getBookMarkedCharacters()
        }

    @Test
    fun `set bookmark characters should return success status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 1
            `when`(characterCache.setCharacterBookmarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setCharacterBookmarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(characterCache, times(1)).setCharacterBookmarked(characterId)
        }

    @Test
    fun `set bookmark characters should return fail status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 0
            `when`(characterCache.setCharacterBookmarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setCharacterBookmarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(characterCache, times(1)).setCharacterBookmarked(characterId)
        }

    @Test
    fun `set bookmark characters should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(characterCache.setCharacterBookmarked(characterId)) doAnswer { throw IOException() }

            // Act (When)

            try{
                sut.setCharacterBookmarked(characterId)
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(characterCache, times(1)).setCharacterBookmarked(characterId)
        }

    @Test
    fun `set un-bookmark characters should return success status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 1
            `when`(characterCache.setCharacterUnBookMarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setCharacterUnBookMarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(characterCache, times(1)).setCharacterUnBookMarked(characterId)
        }

    @Test
    fun `set un-bookmark characters should return fail status`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            val statusFake = 0
            `when`(characterCache.setCharacterUnBookMarked(characterId)) doReturn statusFake

            // Act (When)
            val resultStatus = sut.setCharacterUnBookMarked(characterId)

            // Assert (Then)
            assertEquals(resultStatus, statusFake)
            verify(characterCache, times(1)).setCharacterUnBookMarked(characterId)
        }

    @Test
    fun `set in-bookmark characters should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(characterCache.setCharacterUnBookMarked(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.setCharacterUnBookMarked(characterId)
            }catch (exception:IOException){
                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(characterCache, times(1)).setCharacterUnBookMarked(characterId)
        }

    @Test
    fun `is cached should return true`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(characterCache.isCached()) doReturn true

            // Act (When)
            val resultStatus = sut.isCached()

            // Assert (Then)
            assertTrue(resultStatus)
            verify(characterCache, times(1)).isCached()
        }

    @Test
    fun `is cached should return false`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(characterCache.isCached()) doReturn false

            // Act (When)
            val resultStatus = sut.isCached()

            // Assert (Then)
            assertFalse(resultStatus)
            verify(characterCache, times(1)).isCached()
        }
}
