package com.avengers.ironman_analysis

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_result.*

/**
 * 分析结果的activity
 */
class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar?.hide()
        immersionBar {
            statusBarColor(R.color.colorPrimary)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }

        val list = mutableListOf<Fragment>()
        list.add(LargeImageFragment.newInstance())
        viewPager.adapter = ViewPagerAdapter(this, list)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "大图监控"
            } else {
                tab.text = "其他"
            }
        }.attach()
    }
}

class ViewPagerAdapter(activity: AppCompatActivity, private val list: MutableList<Fragment>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}