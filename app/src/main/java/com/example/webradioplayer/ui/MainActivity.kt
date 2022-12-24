package com.example.webradioplayer.ui

import android.os.Bundle
import com.example.webradioplayer.R
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.main_activity)

            // Add product list fragment if this is first creation
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment == null) {
                val fragment =  GenreFragment() //PlaylistFragment()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
            }

    }
}