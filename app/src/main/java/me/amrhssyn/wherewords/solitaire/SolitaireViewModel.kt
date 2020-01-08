package me.amrhssyn.wherewords.solitaire

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.amrhssyn.wherewords.data.Record
import me.amrhssyn.wherewords.util.WordStatus
import java.text.SimpleDateFormat
import java.util.*


class SolitaireViewModel(private val repository: SolitaireRepository) : ViewModel() {

    val calendar = Calendar.getInstance()!!
    @SuppressLint("SimpleDateFormat")
    private val dateSDF: SimpleDateFormat = SimpleDateFormat("yyyy / MM / dd ")
    @SuppressLint("SimpleDateFormat")
    private val timeSDF: SimpleDateFormat = SimpleDateFormat("HH:mm")

    private var currentDate = ""
    private var currentTime = ""

    init {
        currentDate = dateSDF.format(calendar.time)
        currentTime = timeSDF.format(calendar.time)


    }

    private var spanCount: Int? = null
    private val foundWordsList: ArrayList<String> = arrayListOf()


    init {
        spanCount = repository.getSpanCount()
    }

    fun getSpanCount(): Int {
        return repository.getSpanCount()
    }


    fun getAlphabet(): MutableLiveData<ArrayList<Char>> {
        return randomizeAlphabet(repository.getAlphabet())
    }


    private fun randomizeAlphabet(alphabet: MutableLiveData<java.util.ArrayList<Char>>): MutableLiveData<ArrayList<Char>> {
        val random = Random()

        val mutableLiveData = MutableLiveData<ArrayList<Char>>()
        val list: ArrayList<Char> = arrayListOf()

        for (i in 0 until spanCount!! * spanCount!!) {
            list.add(i, alphabet.value!![random.nextInt(alphabet.value!!.size)])

        }

        mutableLiveData.value = list
        return mutableLiveData
    }


    fun getWords(): MutableLiveData<ArrayList<String>> {
        return repository.getAllWords()
    }


    fun searchFoundWord(foundWord: String): WordStatus {
        return if (repository.searchInWordTrie(foundWord) && !foundWordsList.contains(foundWord)) {
            foundWordsList.add(foundWord)
            repository.setFoundWord(foundWord)
            setScore(foundWord)
            WordStatus.Correct
        } else if (foundWordsList.contains(foundWord)) {
            WordStatus.Repetitious
        } else {
            WordStatus.Incorrect
        }


    }

    private fun setScore(foundWord: String) {
        repository.setScore(foundWord)

    }


    fun getScore(): MutableLiveData<Int> {
        return repository.getScore()
    }

    fun getFoundWord(): MutableLiveData<String> {
        return repository.getFoundWord()
    }


    fun setHighestScore(score: Int, count: Int) {

        repository.setHighestScore(Record(currentDate, currentTime, score, count))
    }

    fun getScoreList(): LiveData<List<Record>> {
        return repository.getScoreList()
    }
}