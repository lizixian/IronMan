package com.avengers.ironman_analysis

import android.util.Log
import androidx.fragment.app.Fragment
import com.avengers.ironman.largeimage.LargeImageManager

class LargeImageFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return LargeImageFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_larget_image

    override fun init() {
        LargeImageManager.lagerImageCache.forEach {
            Log.i("XIAN", "" + it.value.toString())
        }

    }
}



