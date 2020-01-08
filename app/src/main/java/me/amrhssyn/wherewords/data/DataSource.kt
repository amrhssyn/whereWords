package me.amrhssyn.wherewords.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.amrhssyn.wherewords.util.Lang

interface DataSource {

    /**
     * get all words
     */
    fun getWords(): MutableLiveData<ArrayList<String>>

    /**
     * get current app language
     */
    fun getLang(): Lang

    /**
     * set app language
     */
    fun setLang(lang: Lang)

    /**
     * get game board span count
     */
    fun getSpanCount(): Int

    /**
     * set game board span count
     */
    fun setSpanCount(spanCount: Int)

    /**
     * get alphabet for using in game board
     */
    fun getAlphabet(): MutableLiveData<ArrayList<Char>>

    /**
     * add a word to trie data structure
     */
    fun addToWordTrie(word: String)

    /**
     * search a word to trie data structure
     */
    fun searchInWordTrie(word: String): Boolean

    /**
     * get
     */
    fun getFoundWord(): MutableLiveData<String>

    /**
     * set found word into found word var
     */
    fun setFoundWord(word: String)

    /**
     * set highest score into database
     */
    fun setHighestScore(record: Record)

    /**
     * get all records from database
     */
    fun getRecordList(): LiveData<List<Record>>
}