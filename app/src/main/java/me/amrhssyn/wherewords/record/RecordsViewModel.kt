package me.amrhssyn.wherewords.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.amrhssyn.wherewords.data.Record

/**
 * records view model
 */
class RecordsViewModel(val repository: RecordRepository) : ViewModel() {

    /**
     * get all records from repository
     */
    fun getRecordList(): LiveData<List<Record>> {
        return repository.getRecordList()

    }

}