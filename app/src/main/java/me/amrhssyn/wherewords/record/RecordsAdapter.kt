package me.amrhssyn.wherewords.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_record.view.*
import me.amrhssyn.wherewords.R
import me.amrhssyn.wherewords.data.Record

/**
 * adapter of records recycler view
 */
class RecordsAdapter : RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder>() {

    private var recordList: List<Record>?= arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder {
        return RecordsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false))

    }

    override fun getItemCount(): Int {
        return recordList!!.size
    }

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) {
        initRecord(recordList!![position], holder)
    }


    fun setRecords(records:List<Record>){
        recordList=records
        notifyDataSetChanged()
    }


    private fun initRecord(
        record: Record,
        holder: RecordsViewHolder
    ) {
        holder.recordDateTV.text = record.date
        holder.recordCountTV.text = record.number.toString()
        holder.recordScoreTV.text = record.score.toString()
        holder.recordTimeTV.text = record.time
    }


    class RecordsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recordDateTV: TextView = view.recordDateTextView as TextView
        val recordTimeTV = view.recordTimeTextView as TextView
        val recordCountTV = view.recordCountTextView as TextView
        val recordScoreTV = view.recordScoreTextView as TextView

    }
}