package me.amrhssyn.wherewords.data

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.amrhssyn.wherewords.util.Lang
import java.io.InputStreamReader
import java.util.*


class LocalDataSource(var context: Context) : DataSource {

    private var recordDAO: RecordDAO = AppDatabase.getInstance(context).recordDAO()

    private val mutableLiveData = MutableLiveData<String>()


    companion object {
        private const val SHARED_PREFERENCES_NAME = "WHERE_WORD"
        private const val SHARED_PREFERENCES_LANG = "LANG"
        private const val SHARED_PREFERENCES_SPAN_COUNT = "SPAN_COUNT"
    }

    private val sharedPref: SharedPreferences
    private val trie = Trie()


    init {
        sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun setSpanCount(spanCount: Int) {
        val editor = sharedPref.edit()
        editor.putString(SHARED_PREFERENCES_SPAN_COUNT, spanCount.toString())
        editor.apply()
    }

    override fun getSpanCount(): Int {
        val spanCount = sharedPref.getString(SHARED_PREFERENCES_SPAN_COUNT, 5.toString())
        return spanCount.toInt()
    }


    override fun getLang(): Lang {

        val lang = sharedPref.getString(SHARED_PREFERENCES_LANG, Lang.FA.name)

        if (lang == "fa") {
            val configuration = context.resources.configuration
            configuration.setLayoutDirection(Locale("fa"))
            configuration.setLocale(Locale("fa")) // API 17+ only.
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        } else if (lang == "en") {
            val configuration = context.resources.configuration
            configuration.setLayoutDirection(Locale("en"))
            configuration.setLocale(Locale("en")) // API 17+ only.
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)

        }

        return if (lang!! == "FA") Lang.FA else Lang.EN
    }

    override fun setLang(lang: Lang) {
        val editor = sharedPref.edit()
        editor.putString(SHARED_PREFERENCES_LANG, lang.name)
        editor.apply()


        if (lang == Lang.FA) {
            val configuration = context.resources.configuration
            configuration.setLayoutDirection(Locale("fa"))
            configuration.setLocale(Locale("fa")) // API 17+ only.
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            resetApplication()
        } else if (lang == Lang.EN) {
            val configuration = context.resources.configuration
            configuration.setLayoutDirection(Locale("en"))
            configuration.setLocale(Locale("en")) // API 17+ only.
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            resetApplication()

        }

    }



    override fun getWords(): MutableLiveData<ArrayList<String>> {
        val mutableLiveData = MutableLiveData<ArrayList<String>>()
        val list: ArrayList<String> = arrayListOf()

        val fileName = if (getLang() == Lang.FA) {
            "persian_words.txt"

        } else {
            "english_words.txt"
        }
        val stream = context.assets.open(fileName)
        val reader = InputStreamReader(stream)

        reader.forEachLine {
            list.add(it)
            addToWordTrie(it)
        }
        mutableLiveData.value = list

        return mutableLiveData
    }


    override fun getAlphabet(): MutableLiveData<ArrayList<Char>> {
        val mutableLiveData = MutableLiveData<ArrayList<Char>>()
        val list: ArrayList<Char> = arrayListOf()

        if (getLang() == Lang.FA) {
            Alphabet.PERSIAN_ALPHABET.forEach {
                list.add(it)
            }
        } else {

            Alphabet.ENGLISH_ALPHABET.forEach {
                list.add(it)
            }
        }

        mutableLiveData.value = list
        return mutableLiveData
    }


    override fun addToWordTrie(word: String) {
        trie.insert(word)
    }

    override fun searchInWordTrie(word: String): Boolean {
        return trie.search(word)
    }


    override fun setFoundWord(word: String) {
        mutableLiveData.value = word
    }

    override fun getFoundWord(): MutableLiveData<String> {
        return mutableLiveData
    }

    override fun setHighestScore(record: Record) {
        recordDAO.insert(record)
    }

    override fun getRecordList(): LiveData<List<Record>> {
        return recordDAO.getRecordList()
    }

    private fun resetApplication() {
        val i = context.packageManager.getLaunchIntentForPackage(context.packageName)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }


}