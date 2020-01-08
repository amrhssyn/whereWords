package me.amrhssyn.wherewords.util

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import me.amrhssyn.wherewords.solitaire.SolitaireRepository
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base))
    }

    override fun onCreate() {
        super.onCreate()

        val solitaireRepository = SolitaireRepository(this)
        val lang = solitaireRepository.getLang()


        if (lang == Lang.FA) {
            val configuration = resources.configuration
            configuration.setLayoutDirection(Locale("fa"))
            configuration.setLocale(Locale("fa")) // API 17+ only.
            resources.updateConfiguration(configuration, resources.displayMetrics)

        } else if (lang == Lang.EN) {
            val configuration = resources.configuration
            configuration.setLayoutDirection(Locale("en"))
            configuration.setLocale(Locale("en")) // API 17+ only.
            resources.updateConfiguration(configuration, resources.displayMetrics)

        }


    }

    override fun onConfigurationChanged(newConfig: Configuration?) {


        val solitaireRepository = SolitaireRepository(this)
        val lang = solitaireRepository.getLang()


        super.onConfigurationChanged(newConfig)
        if (lang == Lang.FA) {
            val configuration = resources.configuration
            configuration.setLayoutDirection(Locale("fa"))
            configuration.setLocale(Locale("fa")) // API 17+ only.

            resources.updateConfiguration(configuration, resources.displayMetrics)

        } else if (lang == Lang.EN) {
            val configuration = resources.configuration
            configuration.setLayoutDirection(Locale("en"))
            configuration.setLocale(Locale("en")) // API 17+ only.

            resources.updateConfiguration(configuration, resources.displayMetrics)

        }
    }
}