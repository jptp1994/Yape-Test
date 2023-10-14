package com.yape.domain.interactor

import com.yape.domain.models.Settings
import com.yape.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetSettingsBaseUseCase = BaseUseCase<Boolean, Flow<List<Settings>>>

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : GetSettingsBaseUseCase {

    override suspend operator fun invoke(params: Boolean) = settingsRepository.getSettings(params)
}
