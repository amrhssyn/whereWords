package me.amrhssyn.wherewords.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    }

}