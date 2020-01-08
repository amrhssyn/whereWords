package me.amrhssyn.wherewords.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * records view model factory
 */
class RecordViewModelFactory(private val repository: RecordRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordsViewModel::class.java)) {
            return RecordsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}