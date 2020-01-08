package me.amrhssyn.wherewords.solitaire


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_solitaire.*
import me.amrhssyn.wherewords.R
import me.amrhssyn.wherewords.data.Record
import me.amrhssyn.wherewords.databinding.FragmentSolitaireBinding
import me.amrhssyn.wherewords.hint.HintsDialogFragment
import me.amrhssyn.wherewords.util.BaseFragment
import me.amrhssyn.wherewords.util.MyTouchListener
import me.amrhssyn.wherewords.util.ToolbarTitleManager
import me.amrhssyn.wherewords.util.WordStatus

/**
 * solitaire game ui
 */
class SolitaireFragment : BaseFragment(), MyTouchListener.OnTouchActionListener {

    private lateinit var charList: ArrayList<Char>
    private lateinit var gameBoardAdapter: SolitaireAdapter
    private lateinit var foundWordsAdapter: FoundWordsAdapter
    private lateinit var gameBoardRV: RecyclerView
    private lateinit var foundWordsRV: RecyclerView
    private lateinit var viewModel: SolitaireViewModel
    private val foundWordsList: ArrayList<String> = arrayListOf()
    private var currentWord = StringBuffer()
    private var spanCount: Int = 0
    private var highestScore: Int = 0
    private val countDownScoreStep = 3
    private var currentScore: Int = 0
    private var oldPosition: Int = 0


    //private val foundWordsList: ArrayList<String> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSolitaireBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        gameBoardRV = binding.solitaireRecyclerView
        foundWordsRV = binding.foundWordsRecyclerView
        setToolbarTitle(getString(R.string.solitaire_fragment_title))
        initViewModel()
        setGameSettings()
        initGameBoardRV()
        initFoundWordsRV()
        subscribeUi()
        return binding.root
    }

    /**
     * this function inits found words recycler view stuffs
     */
    private fun initFoundWordsRV() {
        foundWordsRV.layoutManager =
                GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        foundWordsAdapter = FoundWordsAdapter()
        foundWordsRV.adapter = foundWordsAdapter
    }

    /**
     * this function inits view model classes and stuffs
     */
    private fun initViewModel() {
        val repository = SolitaireRepository(requireContext())
        val factory = SolitaireViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory)
            .get(SolitaireViewModel::class.java)

    }

    /**
     * set toolbar title
     */
    private fun setToolbarTitle(text: String) {
        (requireActivity() as? ToolbarTitleManager)?.setToolbarTitle(text)
    }

    /**
     * set game settings
     */
    private fun setGameSettings() {
        spanCount = viewModel.getSpanCount()

    }

    /**
     * subscribe observers of solitaire view model
     */
    @SuppressLint("SetTextI18n")
    private fun subscribeUi() {

        /**
         * get alphabets observer
         */
        viewModel.getAlphabet().observe(this, Observer<ArrayList<Char>> {
            charList = it
            gameBoardAdapter.addAll(it)


        })

        /**
         * get words observer
         */
        viewModel.getWords().observe(this, Observer<List<String>> {

        })

        /**
         * get found word observer
         */
        viewModel.getFoundWord().observe(this,
            Observer<String> { t ->
                foundWordsList.add(t)
                foundWordsAdapter.addWord(t)
            })


        /**
         * get highest score list observer for first score
         */
        viewModel.getScoreList().observe(this, Observer<List<Record>> {
            if (!it.isNullOrEmpty()) {
                highestScore = it.first().score
                setHighestScore(highestScore)

            } else {
                highestScore = 0
                setHighestScore(highestScore)


            }
        })


        /**
         * get score observer
         */
        viewModel.getScore().observe(this,
            Observer<Int> {
                currentScore += it
                if (currentScore >= highestScore) {
                    highestScore = currentScore
                    setHighestScore(highestScore)
                }
                setCurrentScore(currentScore)
            })


        /**
         * get hint usage settings observer
         */
        HintsDialogFragment.secondsLiveData.observe(this, Observer<Int> {
            if (currentScore != 0) {
                if (highestScore == currentScore) {
                    highestScore -= it / countDownScoreStep
                    setHighestScore(highestScore)

                }
                currentScore -= it / countDownScoreStep
                setCurrentScore(currentScore)
            }

        })

    }

    /**
     * set highest score to ui
     */
    private fun setHighestScore(thisScore: Int) {
        highestScoreTextView.text = getString(R.string.fragment_solitaire_highestScore, thisScore.toString())

    }

    /**
     * set current score to ui
     */
    private fun setCurrentScore(thisScore: Int) {
        currentScoreTextView.text = getString(R.string.fragment_solitaire_currentScore, thisScore.toString())

    }

    /**
     * init game board recycler view
     */
    private fun initGameBoardRV() {
        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanCount = spanCount
        gameBoardRV.layoutManager = layoutManager

        val width = resources.displayMetrics.widthPixels * 90 / 100
        gameBoardRV.layoutParams.width = width
        gameBoardRV.layoutParams.height = width

        gameBoardRV.addOnItemTouchListener(MyTouchListener(this))
        gameBoardAdapter = SolitaireAdapter(spanCount)
        gameBoardRV.adapter = gameBoardAdapter


    }

    /**
     * listener of game board touch change listener
     */
    override fun onInterceptTouchEvent(position: Int) {

        if (isPositionValid(position)) {
            currentWord.append(charList[position])
            setToolbarTitle(currentWord.toString())
            gameBoardAdapter.changeItemSelectionStatus(position, true)


        }

    }

    /**
     * check touched position in game board is valid or not
     */
    private fun isPositionValid(position: Int): Boolean {
        if (!gameBoardAdapter.getSelectedPosition(position)) {
            if (
                oldPosition == 0
                || oldPosition == position + spanCount
                || oldPosition == position - spanCount
                || oldPosition == position + 1
                || oldPosition == position - 1
            ) {
                oldPosition = position
                return true
            }
        }
        return false
    }

    /**
     * this function called when touch ends
     */
    override fun touchEventFinished() {

        val foundWordStatus = viewModel.searchFoundWord(currentWord.toString())
        when (foundWordStatus) {
            WordStatus.Correct -> setToolbarTitle(getString(R.string.fragment_solitaire_correctWordStatus))
            WordStatus.Repetitious -> setToolbarTitle(getString(R.string.fragment_solitaire_repetitiousWordStatus))
            WordStatus.Incorrect -> setToolbarTitle(getString(R.string.fragment_solitaire_wrongWordStatus))
        }
        for (i in 0 until charList.size) {
            gameBoardAdapter.changeItemSelectionStatus(i, false)


        }
        oldPosition = 0
        currentWord.delete(0, currentWord.lastIndex + 1)
    }


    override fun onStop() {
        super.onStop()

        if (currentScore >= highestScore && currentScore != 0) {
            viewModel.setHighestScore(currentScore, foundWordsList.size)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_hint, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_menu_hint_item).isEnabled = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            me.amrhssyn.wherewords.R.id.action_menu_hint_item -> {

                if (currentScore == 0) {
                    Toast.makeText(context, getString(R.string.fragment_solitaire_hintUsageError), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    showHintDialog()
                }


                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showHintDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
        builder.setTitle(getString(R.string.fragment_solitaire_hintDialogTile))
            .setMessage(getString(R.string.fragment_solitaire_hintDialogMessage, countDownScoreStep.toString()))
            .setPositiveButton(getString(R.string.fragment_solitaire_hintDialogYes)) { dialog, which ->
                HintsDialogFragment.create(viewModel.getWords().value!!, currentScore).show(childFragmentManager, null)
            }
            .setNegativeButton(getString(R.string.fragment_solitaire_hintDialogNo)) { dialog, which ->
                dialog.dismiss()
            }
            .show()


    }


}
