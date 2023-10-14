package com.yape.cache.utils

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yape.cache.dao.RecipeDao
import com.yape.cache.database.YapeDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.IOException

@ExperimentalCoroutinesApi
abstract class CacheBaseTest {

    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @get:Rule
    val testRule = CoroutineTestRule()

    val testScope = testRule.testScope
    private lateinit var database: YapeDatabase
    protected lateinit var charaterDao: RecipeDao
    protected lateinit var context: Context

    @Before
    open fun setup() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, YapeDatabase::class.java)
            .allowMainThreadQueries().build()
        charaterDao = database.cachedRecipeDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        testScope.runTest {
            database.clearAllTables()
        }
        database.close()
    }
}
