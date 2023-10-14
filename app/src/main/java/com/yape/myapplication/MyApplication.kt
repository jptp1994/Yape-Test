package com.yape.myapplication

import android.app.Application
import com.yape.myapplication.core.theme.ThemeUtils
import com.yape.ui.utils.PresentationPreferencesHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var themeUtils: ThemeUtils

    @Inject
    lateinit var preferencesHelper: PresentationPreferencesHelper

    override fun onCreate() {
        super.onCreate()
        initNightMode()
    }

    /**
     * Initialize Night Mode based on user last saved state (day/night themes).
     */
    private fun initNightMode() {
        themeUtils.setNightMode(preferencesHelper.isNightMode)
    }
}
