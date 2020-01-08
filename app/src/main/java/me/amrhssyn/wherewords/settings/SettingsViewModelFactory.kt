package me.amrhssyn.wherewords.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * settings view model factory
 */
class SettingsViewModelFactory(private val repository: SettingsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}