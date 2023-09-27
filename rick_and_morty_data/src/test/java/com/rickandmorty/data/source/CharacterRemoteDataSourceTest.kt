package com.rickandmorty.data.source

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rickandmorty.data.fakes.FakeCharacters
import com.rickandmorty.data.repository.CharacterRemote
import com.rickandmorty.data.utils.DataBaseTest
import junit.framework.TestCase.assertEquals
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
class CharacterRemoteDataSourceTest : DataBaseTest() {

    @Mock
    lateinit var characterRemote: CharacterRemote

    private lateinit var sut: CharacterRemoteDataSource

    @Before
    fun setUp() {
        sut = CharacterRemoteDataSource(characterRemote)
    }

    @Test
    fun `get characters should return characters from remote`() =
        dispatcher.runTest {
            // Arrange (Given)
            `when`(characterRemote.getCharacters()) doReturn FakeCharacters.getCharacters()

            // Act (When)
            val characters = sut.getCharacters()

            // Assert (Then)
            assertEquals(characters.size, 2)
            verify(characterRemote, times(1)).getCharacters()
        }

    @Test
    fun `get characters should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            whenever(characterRemote.getCharacters()) doAnswer { throw IOException() }

            // Act (When)

            try{
                sut.getCharacters()
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(characterRemote, times(1)).getCharacters()
        }

    @Test
    fun `get character with character-id should return characters from remote`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            `when`(characterRemote.getCharacter(characterId)) doReturn FakeCharacters.getCharacters()[0]

            // Act (When)
            val characters = sut.getCharacter(characterId)

            // Assert (Then)
            assertEquals(characters.name, "Rick")
            verify(characterRemote, times(1)).getCharacter(characterId)
        }

    @Test
    fun `get character with character-id should return error`() =
        dispatcher.runTest {
            // Arrange (Given)
            val characterId = 1L
            whenever(characterRemote.getCharacter(characterId)) doAnswer { throw IOException() }

            // Act (When)
            try{
                sut.getCharacter(characterId)
            }catch (exception:IOException){

                // Assert (Then)
                assertThat(
                    exception, instanceOf(IOException::class.java)
                )
            }

            verify(characterRemote, times(1)).getCharacter(characterId)
        }

    @Test
    fun `save characters should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)

            try{
                sut.saveCharacters(FakeCharacters.getCharacters())
            }catch (exception:UnsupportedOperationException){// Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )


            }


        }

    @Test
    fun `get bookmark characters should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)

            try{
                sut.getBookMarkedCharacters()
            }catch (exception:UnsupportedOperationException){

                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }


        }

    @Test
    fun `set bookmark character should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)
            try{
                sut.setCharacterBookmarked(1L)
            }catch (exception:UnsupportedOperationException){
                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }

        }

    @Test
    fun `set un-bookmark character should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)
            try{
                sut.setCharacterUnBookMarked(1L)
            }catch (exception:UnsupportedOperationException){

                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }

        }

    @Test
    fun `is cached should return error - not supported by remote`() =
        dispatcher.runTest {
            // Arrange (Given) nothing to arrange

            // Act (When)
            try{
                sut.isCached()
            }catch (exception:UnsupportedOperationException){

                // Assert (Then)
                assertThat(
                    exception,
                    instanceOf(UnsupportedOperationException::class.java)
                )
            }

        }
}
