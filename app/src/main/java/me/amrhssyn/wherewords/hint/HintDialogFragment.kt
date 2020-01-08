package me.amrhssyn.wherewords.hint

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import me.amrhssyn.wherewords.R
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

/**
 * hint dialog fragment is a popup page that shows a hint word list
 */
class HintsDialogFragment : DialogFragment() {
    val timer = Timer()
    var seconds = 0


    private val hintsAdapter = HintAdapter()

    private var countDownNumberTV: TextView? = null

    var currentScore = 0

    companion object {
        private const val HINTS_KEY = "Hint"
        var secondsLiveData: MutableLiveData<Int> = MutableLiveData()

        fun create(words: ArrayList<String>, score: Int): HintsDialogFragment =
            HintsDialogFragment().apply {
                currentScore = score
                arguments = Bundle().apply { putSerializable(HINTS_KEY, words) }
            }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialogfragment_hint, null)
        val builder = AlertDialog.Builder(requireContext())
            .setView(view)




        arguments?.getSerializable(HINTS_KEY)?.let { hintsAdapter.addAllHints(it as ArrayList<String>) }

        with(view.findViewById<RecyclerView>(android.R.id.list)) {
            adapter = hintsAdapter
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
        }


        view.findViewById<Button>(android.R.id.button1).setOnClickListener {
            timer.cancel()
            secondsLiveData.value = seconds
            dismiss()
        }
        this.countDownNumberTV = view.findViewById(R.id.countDownTextView)

        countDownScore()

        return builder.create()
    }

    /**
     * this function reduce score from current score by a live data
     */
    private fun countDownScore() {
        seconds = 0
        timer.scheduleAtFixedRate(0, 1000) {
            activity!!.runOnUiThread {
                seconds += 1
                countDownNumberTV!!.text = seconds.toString()
                if (seconds / 3 >= currentScore) {
                    timer.cancel()
                    secondsLiveData.value = seconds
                    dismiss()
                }
            }


        }


    }


}