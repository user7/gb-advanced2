package com.gb.advanced2.externals.ui

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.utils.viewById
import com.gb.advanced2.R
import com.gb.advanced2.app.createMainScope
import com.gb.advanced2.externals.ui.description.DescriptionFragment
import com.gb.advanced2.externals.ui.history.HistoryFragment
import com.gb.advanced2.externals.ui.main.SearchFragment
import com.gb.advanced2.externals.ui.navigation.Navigator
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.core.scope.Scope

class MainActivity : AppCompatActivity() {
    private val scope: Scope by lazy { createMainScope() }
    private lateinit var navigatorHolder: NavigatorHolder
    private val mainContainer by viewById<FragmentContainerView>(R.id.main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var splashScreen = installSplashScreen()
        setContentView(R.layout.main_activity)
        navigatorHolder = scope.get()
        splashScreen.setOnExitAnimationListener { screen ->
            val slideDown = ObjectAnimator.ofFloat(
                screen.view,
                View.TRANSLATION_Y,
                0f,
                -screen.view.height.toFloat(),
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = 2000
                doOnEnd { screen.remove() }
                start()
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}