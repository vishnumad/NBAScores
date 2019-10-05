package io.github.vishnumad.nbascores.ui.main

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import io.github.inflationx.viewpump.ViewPumpContextWrapper
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

        setupBottomNavFont()

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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
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

    private fun setupBottomNavFont() {
        val boldTypeFace = Typeface.createFromAsset(assets, getString(R.string.font_bold))
        val regularTypeFace = Typeface.createFromAsset(assets, getString(R.string.font_regular))

        main_nav_bar.children.forEach { child ->
            if (child is BottomNavigationMenuView) {
                child.children.forEach { item ->
                    val smallLabel = item.findViewById<TextView>(com.google.android.material.R.id.smallLabel)
                    val largeLabel = item.findViewById<TextView>(com.google.android.material.R.id.largeLabel)
                    smallLabel.typeface = regularTypeFace
                    largeLabel.typeface = boldTypeFace
                }
            }
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
