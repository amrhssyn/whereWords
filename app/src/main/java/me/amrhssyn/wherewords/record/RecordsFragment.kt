package me.amrhssyn.wherewords.record


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.amrhssyn.wherewords.R
import me.amrhssyn.wherewords.data.Record
import me.amrhssyn.wherewords.databinding.FragmentRecordsBinding
import me.amrhssyn.wherewords.util.BaseFragment
import me.amrhssyn.wherewords.util.ToolbarTitleManager


/**
 * adapter of records recycler view
 */
class RecordsFragment : BaseFragment() {

    private var recordsViewModel: RecordsViewModel? = null
    private var recordsList: List<Record>? = null
    var adapter: RecordsAdapter? = null
    private lateinit var recordListRv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecordsBinding.inflate(inflater, container, false)
        (requireActivity() as? ToolbarTitleManager)?.setToolbarTitle(getString(R.string.fragment_records_title))
        recordListRv = binding.recordListRecyclerView
        initRecordsRV()
        initViewModel()
        subscribeUi()
        return binding.root
    }

    /**
     * this function initialize view model with repository and factory
     */
    private fun initViewModel() {
        val repository = RecordRepository(context!!)
        val factory = RecordViewModelFactory(repository)
        recordsViewModel = ViewModelProviders.of(this, factory).get(RecordsViewModel::class.java)

    }

    /**
     * this function subscribes records list
     */
    private fun subscribeUi() {

        recordsViewModel!!.getRecordList().observe(this,
            Observer<List<Record>> {
                if (it.isNullOrEmpty()) {

                    //todo emptyState
                } else {
                    recordsList = it
                    adapter!!.setRecords(it)

                }
            })


    }

    /**
     * this function initialize records recycler view
     */
    private fun initRecordsRV() {
        recordListRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = RecordsAdapter()
        recordListRv.adapter = adapter

    }


}