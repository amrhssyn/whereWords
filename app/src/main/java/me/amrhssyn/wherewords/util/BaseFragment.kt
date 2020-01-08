package me.amrhssyn.wherewords.util

import android.content.Context
import androidx.fragment.app.Fragment
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

open class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(CalligraphyContextWrapper.wrap(context))

    }
}