package com.example.mibancaapp.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mibancaapp.dashboard.mycards.MyCardsFragment
import com.example.mibancaapp.dashboard.pay.PayFragment
import com.example.mibancaapp.dashboard.movements.MyMovementsFragment

class DashboardPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyCardsFragment()
            1 -> PayFragment()
            2 -> MyMovementsFragment()
            else -> MyCardsFragment()
        }
    }
}