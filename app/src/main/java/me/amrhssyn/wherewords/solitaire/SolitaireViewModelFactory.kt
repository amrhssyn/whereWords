package me.amrhssyn.wherewords.solitaire

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SolitaireViewModelFactory(private val repository: SolitaireRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SolitaireViewModel(repository) as T
    }
}
