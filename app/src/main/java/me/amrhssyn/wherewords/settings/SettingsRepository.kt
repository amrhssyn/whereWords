package me.amrhssyn.wherewords.settings

import android.content.Context
import me.amrhssyn.wherewords.data.AppDatabase
import me.amrhssyn.wherewords.data.LocalDataSource
import me.amrhssyn.wherewords.data.RecordDAO
import me.amrhssyn.wherewords.util.Lang

/**
 * settings repository
 */
class SettingsRepository(var context: Context) {

    private var localDataSource: LocalDataSource =
        LocalDataSource(context)
    private var recordDAO: RecordDAO = AppDatabase.getInstance(context).recordDAO()

    /**
     * set game board span count to local data source
     */
    fun setSpanCount(spanCount: Int) {
        localDataSource.setSpanCount(spanCount)
    }

    /**
     * get game board span count from local data source
     */
    fun getSpanCount(): Int {
        return localDataSource.getSpanCount()
    }

    /**
     * set language to local data source
     */
    fun setLanguage(lang: Lang) {
        localDataSource.setLang(lang)
    }

    /**
     * get language from local data source
     */
    fun getLanguage(): Lang {
        return localDataSource.getLang()
    }

    /**
     * delete records from database(dao)
     */
    fun deleteRecords() {
        return recordDAO.deleteAllRecords()
    }

}