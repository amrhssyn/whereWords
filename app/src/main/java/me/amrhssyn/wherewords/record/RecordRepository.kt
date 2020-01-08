package me.amrhssyn.wherewords.record

import android.content.Context
import androidx.lifecycle.LiveData
import me.amrhssyn.wherewords.data.AppDatabase
import me.amrhssyn.wherewords.data.Record
import me.amrhssyn.wherewords.data.RecordDAO

/**
 * repository of record
 */
class RecordRepository(var context: Context){

    private var recordDAO: RecordDAO = AppDatabase.getInstance(context).recordDAO()

    /**
     * get record list from database(dao)
     */
    fun getRecordList():LiveData<List<Record>> {
        return recordDAO.getRecordList()
    }
}