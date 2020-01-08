package me.amrhssyn.wherewords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * data access object of where word database
 */
@Dao
interface RecordDAO {

    /**
     * insert a record to data base
     */
    @Insert
    fun insert(record: Record)

    /**
     * get all records from database order by score
     */
    @Query("SELECT * FROM RECORD_TABLE ORDER BY score DESC")
    fun getRecordList(): LiveData<List<Record>>

    /**
     * delete all records from record table
     */
    @Query("DELETE FROM RECORD_TABLE")
    fun deleteAllRecords()


}