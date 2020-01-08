package me.amrhssyn.wherewords.settings

import androidx.lifecycle.ViewModel
import me.amrhssyn.wherewords.util.Lang

/**
 * settings view model handler
 */
class SettingsViewModel(val repository: SettingsRepository) : ViewModel() {

    /**
     * set span count to repository
     */
    fun setSpanCount(spanCount: Int) {
        repository.setSpanCount(spanCount)
    }

    /**
     * get span count from repository
     */
    fun getSpanCount(): Int {
        return repository.getSpanCount()
    }

    /**
     * set language count to repository
     */
    fun setLanguage(lang: Lang) {
        repository.setLanguage(lang)
    }

    /**
     * get language count from repository
     */
    fun getLanguage(): Lang {
        return repository.getLanguage()
    }

    /**
     * delete records from repository
     */
    fun deleteRecordsClicked() {
        repository.deleteRecords()
    }
}