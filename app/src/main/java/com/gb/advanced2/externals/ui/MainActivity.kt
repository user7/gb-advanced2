package com.gb.advanced2.externals.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gb.advanced2.R
import com.gb.advanced2.externals.ui.description.DescriptionFragment
import com.gb.advanced2.externals.ui.history.HistoryFragment
import com.gb.advanced2.externals.ui.main.SearchFragment
import com.gb.advanced2.externals.ui.navigation.Navigator
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val navigatorHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(object : Navigator {
            private fun pushFragment(fragment: Fragment) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            override fun goToHistory() = pushFragment(HistoryFragment())
            override fun goToSearch() = pushFragment(SearchFragment())
            override fun goToDescription() = pushFragment(DescriptionFragment())
            override fun goBack() = supportFragmentManager.popBackStack()
        })
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}