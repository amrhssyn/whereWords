package me.amrhssyn.wherewords.solitaire

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_letter.view.*
import me.amrhssyn.wherewords.R

/**
 * solitaire game board adapter
 */
class SolitaireAdapter(private var spanCount: Int) :
    RecyclerView.Adapter<SolitaireAdapter.SolitaireGameViewHolder>() {


    private lateinit var context: Context
    private var letters: ArrayList<Char> = arrayListOf()
    private var selectedPositions = Array(spanCount * spanCount) { false }

    private var width = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolitaireGameViewHolder {
        context = parent.context
        width = context.resources.displayMetrics.widthPixels * 80 / 100
        return SolitaireGameViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_letter, parent, false))

    }

    override fun getItemCount(): Int {
        return letters.size

    }

    override fun onBindViewHolder(holder: SolitaireGameViewHolder, position: Int) {
        val word = letters[position]
        holder.letterText.text = word.toString()


        if (selectedPositions[position]) {
            holder.container.background = context.resources.getDrawable(R.drawable.bg_item_letter_active)
        } else {
            holder.container.background = context.resources.getDrawable(R.drawable.bg_item_letter_deactive)
        }

        //todo dimen this sizes
        holder.container.layoutParams.width = width / spanCount
        holder.container.layoutParams.height = width / spanCount


    }

    /**
     * add a char to game board
     */
    fun add(pos: Int = 0, item: Char) {
        letters.add(pos, item)
        notifyItemChanged(pos)
    }

    /**
     * add char list to game board
     */
    fun addAll(items: List<Char>) {
        letters.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * change item selection status and update game board ui
     */
    fun changeItemSelectionStatus(position: Int, isSelected: Boolean) {
        selectedPositions[position] = isSelected
        notifyDataSetChanged()

    }

    /**
     * get all selected positions from game board
     */
    fun getSelectedPosition(position: Int): Boolean {
        return selectedPositions[position]

    }

    class SolitaireGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var container: LinearLayout = itemView.containerLinearLayout as LinearLayout
        var letterText: TextView = itemView.letterTextView as TextView

    }


}


