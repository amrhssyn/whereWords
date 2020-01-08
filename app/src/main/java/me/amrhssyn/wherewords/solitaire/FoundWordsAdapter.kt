package me.amrhssyn.wherewords.solitaire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_found_word.view.*
import me.amrhssyn.wherewords.R

/**
 * found words recycler view adapter
 */
class FoundWordsAdapter : RecyclerView.Adapter<FoundWordsAdapter.FoundWordsViewHolder>() {

    private var foundWordList = mutableListOf<String>()
    fun addWord(foundWord: String) {
        foundWordList.add(foundWord)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundWordsViewHolder {
        return FoundWordsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_found_word,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return foundWordList.size
    }

    override fun onBindViewHolder(holder: FoundWordsViewHolder, position: Int) {
        holder.foundWordTextview.text = foundWordList[position]

    }


    class FoundWordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foundWordTextview = itemView.foundWordTextview as TextView

    }
}