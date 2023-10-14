package com.yape.domain.repository

import com.yape.domain.models.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettings(isNightMode: Boolean): Flow<List<Settings>>
}
