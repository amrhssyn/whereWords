package me.amrhssyn.wherewords.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "RECORD_TABLE")
data class Record(
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "time")var time: String,
    @ColumnInfo(name = "score") var score: Int,
    @ColumnInfo(name = "number")var number: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0

}
