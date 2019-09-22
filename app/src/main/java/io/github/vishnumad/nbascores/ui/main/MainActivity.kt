package io.github.vishnumad.nbascores.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.github.vishnumad.nbascores.R
import io.github.vishnumad.nbascores.ui.schedule.ScheduleFragment
import io.github.vishnumad.nbascores.ui.scores.ScoresFragment
import io.github.vishnumad.nbascores.ui.standings.StandingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_CONTAINER = R.id.main_content
    }

    private var currentFragment: MainNavBarFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Listen for Bottom Nav Bar events
        main_nav_bar.setOnNavigationItemSelectedListener { handleNavSelect(it) }
        main_nav_bar.setOnNavigationItemReselectedListener { handleNavReselect() }

        if (savedInstanceState == null) {
            // Start with the scores screen
            main_nav_bar.selectedItemId = R.id.nav_scores
            replaceFragment(ScoresFragment())
        } else {
            currentFragment =
                supportFragmentManager.findFragmentById(FRAGMENT_CONTAINER) as? MainNavBarFragment
                    ?: throw IllegalStateException("Expected a MainNavBarFragment, but none was found")
        }
    }

    private fun handleNavSelect(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_scores -> {
                replaceFragment(ScoresFragment())
                true
            }
            R.id.nav_standings -> {
                replaceFragment(StandingsFragment())
                true
            }
            R.id.nav_schedule -> {
                replaceFragment(ScheduleFragment())
                true
            }
            else -> throw IllegalArgumentException("Unknown navigation item selected")
        }
    }

    private fun handleNavReselect() {
        currentFragment?.onReselected()
    }

    /**
     * Replaces the current fragment shown above the bottom navigation
     * bar with a new [MainNavBarFragment]
     *
     * Old fragments will not be added to the backstack
     */
    private fun <F> replaceFragment(fragment: F) where F : Fragment, F : MainNavBarFragment {
        currentFragment = fragment
        supportFragmentManager.beginTransaction()
            .replace(FRAGMENT_CONTAINER, fragment)
            .commit()
    }
}
