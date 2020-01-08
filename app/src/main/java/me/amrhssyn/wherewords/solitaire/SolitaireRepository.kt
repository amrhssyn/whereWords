package me.amrhssyn.wherewords.solitaire

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.amrhssyn.wherewords.data.LocalDataSource
import me.amrhssyn.wherewords.data.Record
import me.amrhssyn.wherewords.util.FoundWordScoreCalculator
import me.amrhssyn.wherewords.util.Lang

class SolitaireRepository(context: Context) {

    companion object {
        var score: MutableLiveData<Int> = MutableLiveData()
    }

    init {
        score.value = 0
    }


    private var localDataSource: LocalDataSource =
        LocalDataSource(context)


    fun getAlphabet(): MutableLiveData<ArrayList<Char>> {
        return localDataSource.getAlphabet()
    }


    fun getLang(): Lang {
        return localDataSource.getLang()
    }

    fun getAllWords(): MutableLiveData<ArrayList<String>> {
        return localDataSource.getWords()
    }


    fun getSpanCount(): Int {
        return localDataSource.getSpanCount()
    }

    fun searchInWordTrie(word: String): Boolean {
        return localDataSource.searchInWordTrie(word)
    }

    fun getFoundWord(): MutableLiveData<String> {
        return localDataSource.getFoundWord()
    }

    fun setFoundWord(word: String) {
        return localDataSource.setFoundWord(word)
    }

    fun setHighestScore(record: Record) {
        return localDataSource.setHighestScore(record)
    }

    fun getScoreList(): LiveData<List<Record>> {
        return localDataSource.getRecordList()
    }


    fun setScore(foundWord: String) {
        score.value = FoundWordScoreCalculator.find(foundWord)

    }

    fun getScore(): MutableLiveData<Int> {
        return score
    }


}