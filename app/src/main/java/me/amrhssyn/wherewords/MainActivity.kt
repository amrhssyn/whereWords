package me.amrhssyn.wherewords


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import me.amrhssyn.wherewords.databinding.ActivityMainBinding
import me.amrhssyn.wherewords.util.BaseActivity
import me.amrhssyn.wherewords.util.ToolbarTitleManager

class MainActivity : BaseActivity(), ToolbarTitleManager {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        setupNavigation()
        setupBottomNavMenu(navController)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        binding.toolbar.setupWithNavController(
            navController, AppBarConfiguration(
                setOf(
                    R.id.solitaireFragment,
                    R.id.recordsFragment,
                    R.id.settingsFragment
                )
            )
        )
    }

    private fun setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        //  setupActionBarWithNavController(navController)


    }


    private fun setupBottomNavMenu(navController: NavController) {
        binding.menuBottomNavigationView.setupWithNavController(navController)

    }

    override fun setToolbarTitle(title: String) {
        binding.toolbar.title = title

    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()


}

