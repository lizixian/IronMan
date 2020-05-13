package com.avengers.ironman_analysis

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    private var mRootView: View? = null
    private var inflater: LayoutInflater? = null

    var mContext: Context? = null
    var mActivity: Activity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView != null) {
            val parent = mRootView!!.parent as ViewGroup
            parent?.removeView(mRootView)
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false)
            mActivity = activity
            mContext = mActivity
            this.inflater = inflater
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    abstract fun getLayoutId(): Int

    abstract fun init()

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }
}