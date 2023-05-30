package com.lordkajoc.myprojectshop.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lordkajoc.myprojectshop.R
import com.lordkajoc.myprojectshop.databinding.ActivityBottomNavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Bottom_Nav : AppCompatActivity() {
    lateinit var binding: ActivityBottomNavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottonNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.favoriteFragment -> replaceFragment(FavoriteFragment())
                R.id.historyFragment -> replaceFragment(FavoriteFragment())
                R.id.profileFragment-> replaceFragment(ProfileFragment())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}