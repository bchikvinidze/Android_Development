package com.nchikvinidze.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity, frag1: DayFragment, frag2: HourFragment): FragmentStateAdapter(activity){
    var fragmentsList = arrayListOf<Fragment>(frag1, frag2)

    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList.get(position)
    }

}