package me.amrhssyn.wherewords.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.ViewModelProviders
import me.amrhssyn.wherewords.R
import me.amrhssyn.wherewords.databinding.FragmentSettingsBinding
import me.amrhssyn.wherewords.util.BaseFragment
import me.amrhssyn.wherewords.util.Lang
import me.amrhssyn.wherewords.util.ToolbarTitleManager


/**
 * settings fragment and ui
 */
class SettingsFragment : BaseFragment() {


    private val spanCountArray = arrayOf(
        "2 * 2"     // 4
        , "3 * 3"   // 9
        , "4 * 4"   // 16
        , "5 * 5"   //25
        , "6 * 6"   //36
        , "7 * 7"   //49
        , "8 * 8"   //64
        , "9 * 9"   //81
        , "10 * 10" //100
    )


    private var settingsViewModel: SettingsViewModel? = null

    private lateinit var langFarsiBtn: Button
    private lateinit var langEnglishBtn: Button
    private lateinit var deleteRecords: Button
    private lateinit var spanCountSPNR: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        (requireActivity() as? ToolbarTitleManager)?.setToolbarTitle(getString(R.string.fragment_settings_title))
        langFarsiBtn = binding.langFarsiButton
        langEnglishBtn = binding.langEnglishButton
        spanCountSPNR = binding.spanCountSpinner
        deleteRecords = binding.deleteRecordsButton

        initViewModel()
        setLang()
        initSpanCountView()

        deleteRecordsListener()

        return binding.root
    }

    /**
     * listen to delete records button and delete all records
     */
    private fun deleteRecordsListener() {
        deleteRecords.setOnClickListener {
            settingsViewModel!!.deleteRecordsClicked()
        }


    }

    /**
     * set language of app based on and language click listener handler
     */
    private fun setLang() {

        val lang = settingsViewModel!!.getLanguage()

        if (lang == Lang.FA) {
            faLangBtnStatus(true)
            enLangBtnStatus(false)
        } else {
            faLangBtnStatus(false)
            enLangBtnStatus(true)
        }



        langFarsiBtn.setOnClickListener {
            settingsViewModel!!.setLanguage(Lang.FA)
            faLangBtnStatus(true)
            enLangBtnStatus(false)

        }

        langEnglishBtn.setOnClickListener {
            settingsViewModel!!.setLanguage(Lang.EN)
            faLangBtnStatus(false)
            enLangBtnStatus(true)

        }


    }

    /**
     * init view model of settings fragment
     */
    private fun initViewModel() {
        val repository = SettingsRepository(context!!)
        val factory = SettingsViewModelFactory(repository)
        settingsViewModel = ViewModelProviders.of(this, factory).get(SettingsViewModel::class.java)


    }

    /**
     * init span count view and click listener
     */
    private fun initSpanCountView() {


        spanCountSPNR.adapter =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, spanCountArray)


        when (settingsViewModel!!.getSpanCount()) {
            2 -> spanCountSPNR.setSelection(0)
            3 -> spanCountSPNR.setSelection(1)
            4 -> spanCountSPNR.setSelection(2)
            5 -> spanCountSPNR.setSelection(3)
            6 -> spanCountSPNR.setSelection(4)
            7 -> spanCountSPNR.setSelection(5)
            8 -> spanCountSPNR.setSelection(6)
            9 -> spanCountSPNR.setSelection(7)
            10 -> spanCountSPNR.setSelection(8)
        }


        spanCountSPNR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (position) {
                    0 -> settingsViewModel!!.setSpanCount(2)
                    1 -> settingsViewModel!!.setSpanCount(3)
                    2 -> settingsViewModel!!.setSpanCount(4)
                    3 -> settingsViewModel!!.setSpanCount(5)
                    4 -> settingsViewModel!!.setSpanCount(6)
                    5 -> settingsViewModel!!.setSpanCount(7)
                    6 -> settingsViewModel!!.setSpanCount(8)
                    7 -> settingsViewModel!!.setSpanCount(9)
                    8 -> settingsViewModel!!.setSpanCount(10)

                }

            }
        }


    }

    /**
     * manage status of farsi language button
     */
    private fun faLangBtnStatus(status: Boolean) {
        if (status) {
            langFarsiBtn.background = context!!.resources.getDrawable(R.drawable.bg_btn_fa_lang_active)
            langFarsiBtn.setTextColor(resources.getColor(R.color.languageActive))
        } else {
            langFarsiBtn.background = context!!.resources.getDrawable(R.drawable.bg_btn_fa_lang_deactive)
            langFarsiBtn.setTextColor(resources.getColor(R.color.languageDeactive))

        }

    }

    /**
     * manage status of english language button
     */
    private fun enLangBtnStatus(status: Boolean) {
        if (status) {
            langEnglishBtn.background = context!!.resources.getDrawable(R.drawable.bg_btn_en_lang_active)
            langEnglishBtn.setTextColor(resources.getColor(R.color.languageActive))
        } else {
            langEnglishBtn.background = context!!.resources.getDrawable(R.drawable.bg_btn_en_lang_deactive)
            langEnglishBtn.setTextColor(resources.getColor(R.color.languageDeactive))

        }
    }


}
