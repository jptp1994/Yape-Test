package com.rickandmorty.ui.viewmodel

import androidx.lifecycle.Observer
import com.rickandmorty.ui.fakes.FakePresentationData
import com.rickandmorty.ui.utils.PresentationBaseTest
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rick_and_morty.domain.interactor.CharacterBookmarkUseCase
import com.rick_and_morty.domain.interactor.CharacterUnBookmarkUseCase
import com.rick_and_morty.domain.interactor.GetCharacterByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailViewModelTest : PresentationBaseTest() {

    @Mock
    lateinit var charactersUseCase: GetCharacterByIdUseCase

    @Mock
    lateinit var bookmarkUseCase: CharacterBookmarkUseCase

    @Mock
    lateinit var unBookmarkUseCase: CharacterUnBookmarkUseCase

    @Mock
    private lateinit var observer: Observer<CharacterDetailUIModel>

    private lateinit var sut: CharacterDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = CharacterDetailViewModel(
            dispatcher,
            charactersUseCase,
            bookmarkUseCase,
            unBookmarkUseCase
        )
        sut.getCharacter().observeForever(observer)
    }

    @Test
    fun `get character detail with character-id should return character complete detail from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            val character = FakePresentationData.getCharacters(1)[0]
            `when`(charactersUseCase(characterId)).thenReturn(flowOf(character))

            // Act (When)
            sut.getCharacterDetail(characterId)

            // Assert (Then)
            verify(observer).onChanged(CharacterDetailUIModel.Loading)
            verify(observer).onChanged(CharacterDetailUIModel.Success(character))
        }

    @Test
    fun `get character detail with character-id should return error from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            val errorMessage = "Internal server error"
            whenever(charactersUseCase(characterId)) doAnswer { throw IOException(errorMessage) }

            // Act (When)
            sut.getCharacterDetail(characterId)

            // Assert (Then)
            verify(observer).onChanged(CharacterDetailUIModel.Loading)
            verify(observer).onChanged(CharacterDetailUIModel.Error(errorMessage))
        }

    @Test
    fun `set bookmark character should return success status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            val status = 1
            `when`(bookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // Act (When)
            sut.setBookmarkCharacter(characterId)

            // Assert (Then)
            verify(observer).onChanged(
                CharacterDetailUIModel.BookMarkStatus(
                    Bookmark.BOOKMARK,
                    true
                )
            )
        }

    @Test
    fun `set bookmark character should return fail status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            val status = 0
            `when`(bookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // Act (When)
            sut.setBookmarkCharacter(characterId)

            // Assert (Then)
            verify(observer).onChanged(
                CharacterDetailUIModel.BookMarkStatus(
                    Bookmark.BOOKMARK,
                    false
                )
            )
        }

    @Test
    fun `set un-bookmark character should return success status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            val status = 1
            `when`(unBookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // Act (When)
            sut.setUnBookmarkCharacter(characterId)

            // Assert (Then)
            verify(observer).onChanged(
                CharacterDetailUIModel.BookMarkStatus(
                    Bookmark.UN_BOOKMARK,
                    true
                )
            )
        }

    @Test
    fun `set un-bookmark character should return fail status from use-case`() =
        dispatcher.testScope.runTest {
            // Arrange (Given)
            val characterId = 1L
            val status = 0
            `when`(unBookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // Act (When)
            sut.setUnBookmarkCharacter(characterId)

            // Assert (Then)
            verify(observer).onChanged(
                CharacterDetailUIModel.BookMarkStatus(
                    Bookmark.UN_BOOKMARK,
                    false
                )
            )
        }

    @After
    fun tearDown() {
        sut.onCleared()
    }
}
