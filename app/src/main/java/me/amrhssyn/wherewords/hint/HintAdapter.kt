package me.amrhssyn.wherewords.hint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_hint_word.view.*
import me.amrhssyn.wherewords.R


/**
 * hint recycler view adapter
 */
class HintAdapter : RecyclerView.Adapter<HintAdapter.HintViewHolder>() {


    private val hintList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        return HintViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hint_word,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.hintWordTV.layoutDirection = View.LAYOUT_DIRECTION_RTL
        holder.hintWordTV.text = hintList[position]


    }

    override fun getItemCount() = hintList.size


    fun addAllHints(elements: ArrayList<String>) {
        hintList.addAll(elements)
        notifyDataSetChanged()
    }

    class HintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hintWordTV = itemView.hintWordTextView as TextView

    }

}
