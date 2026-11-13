package com.example.shiyan6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, InfoFragment(), TAG_INFO)
                .add(R.id.fragment_container, TwoCardGameFragment(), TAG_GAME2)
                .add(R.id.fragment_container, ThreeCardGameFragment(), TAG_GAME3)
                .hide(fragmentByTag(TAG_GAME2))
                .hide(fragmentByTag(TAG_GAME3))
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

    private fun fragmentByTag(tag: String): Fragment =
        supportFragmentManager.findFragmentByTag(tag)!!

    private fun showFragment(tag: String) {
        val tx = supportFragmentManager.beginTransaction()
        tx.hide(fragmentByTag(TAG_INFO))
        tx.hide(fragmentByTag(TAG_GAME2))
        tx.hide(fragmentByTag(TAG_GAME3))
        tx.show(fragmentByTag(tag))
        tx.commit()
    }

    companion object {
        private const val TAG_INFO = "info"
        private const val TAG_GAME2 = "game2"
        private const val TAG_GAME3 = "game3"
    }
}
