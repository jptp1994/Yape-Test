package com.yape.myapplication.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yape.app.databinding.FragmentSettingsBinding
import com.yape.myapplication.base.BaseFragment
import com.yape.myapplication.core.theme.ThemeUtils
import com.yape.myapplication.extension.observe
import com.yape.ui.viewmodel.BaseViewModel
import com.yape.ui.viewmodel.SettingUIModel
import com.yape.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, BaseViewModel>() {

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)

    override val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var settingsAdapter: SettingsAdapter

    @Inject
    lateinit var themeUtils: ThemeUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.settings, ::onViewStateChange)
        setupRecyclerView()
        viewModel.getSettings()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSettings.apply {
            adapter = settingsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        settingsAdapter.setItemClickListener { selectedSetting ->
            viewModel.setSettings(selectedSetting)
        }
    }

    private fun onViewStateChange(result: SettingUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is SettingUIModel.Error -> handleErrorMessage(result.error)
            SettingUIModel.Loading -> handleLoading(true)
            is SettingUIModel.NightMode -> {
                result.nightMode.let {
                    themeUtils.setNightMode(it)
                }
            }
            is SettingUIModel.Success -> {
                handleLoading(false)
                result.data.let {
                    settingsAdapter.list = it
                }
            }
        }
    }
}
