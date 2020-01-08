package me.amrhssyn.wherewords.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.amrhssyn.wherewords.util.SingletonHolder

/**
 * where words application database that implemented by jetpack room library
 */
@Database(entities = [Record::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDAO(): RecordDAO

    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext,
            AppDatabase::class.java, "whereWords.db"
        ).allowMainThreadQueries()
            .build()
    })


}