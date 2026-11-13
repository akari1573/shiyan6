package com.example.shiyan6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shiyan6.fragment.InfoFragment
import com.example.shiyan6.fragment.ThreeCardGameFragment
import com.example.shiyan6.fragment.TwoCardGameFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        if (savedInstanceState == null) {
            val infoFragment = InfoFragment()
            val game2Fragment = TwoCardGameFragment()
            val game3Fragment = ThreeCardGameFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, infoFragment, TAG_INFO)
                .add(R.id.fragment_container, game2Fragment, TAG_GAME2)
                .add(R.id.fragment_container, game3Fragment, TAG_GAME3)
                .hide(game2Fragment)
                .hide(game3Fragment)
                .commit()
        }

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_info -> showFragment(TAG_INFO)
                R.id.nav_game2 -> showFragment(TAG_GAME2)
                R.id.nav_game3 -> showFragment(TAG_GAME3)
            }
            true
        }
    }

    private fun showFragment(tag: String) {
        val tx = supportFragmentManager.beginTransaction()
        for (t in arrayOf(TAG_INFO, TAG_GAME2, TAG_GAME3)) {
            supportFragmentManager.findFragmentByTag(t)?.let { f ->
                if (t == tag) tx.show(f) else tx.hide(f)
            }
        }
        tx.commit()
    }

    companion object {
        private const val TAG_INFO = "info"
        private const val TAG_GAME2 = "game2"
        private const val TAG_GAME3 = "game3"
    }
}
