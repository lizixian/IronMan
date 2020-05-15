package com.avengers.ironman_analysis.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


abstract class ViewHolderCreator<T> {
    abstract fun isForViewType(data: T?, position: Int): Boolean
    abstract fun getResourceId(): Int
    abstract fun onBindViewHolder(
        data: T?,
        items: MutableList<T>?,
        position: Int,
        holder: ViewHolderCreator<T>,
        payloads: MutableList<Any>
    )

    private var itemView: View? = null

    fun registerItemView(itemView: View?) {
        this.itemView = itemView
    }

    fun <V : View> findViewById(viewId: Int): V {
        checkItemView()
        return itemView!!.findViewById(viewId)
    }

    fun setText(viewId: Int, text: String?): ViewHolderCreator<T> {
        val textView: TextView = findViewById(viewId)
        textView.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): ViewHolderCreator<T> {
        val imageView: ImageView = findViewById(viewId)
        imageView.setImageResource(resId)
        return this
    }

    fun setImageBitmap(viewId: Int, bm: Bitmap?): ViewHolderCreator<T> {
        val imageView: ImageView = findViewById(viewId)
        imageView.setImageBitmap(bm)
        return this
    }

    fun setImageDrawable(
        viewId: Int,
        drawable: Drawable?
    ): ViewHolderCreator<T> {
        val imageView: ImageView = findViewById(viewId)
        imageView.setImageDrawable(drawable)
        return this
    }

    fun setBackground(
        viewId: Int,
        drawable: Drawable?
    ): ViewHolderCreator<T> {
        val imageView: ImageView = findViewById(viewId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.background = drawable
        } else {
            imageView.setBackgroundDrawable(drawable)
        }
        return this
    }

    fun setBackgroundResource(viewId: Int, resid: Int): ViewHolderCreator<T> {
        val imageView: ImageView = findViewById(viewId)
        imageView.setBackgroundResource(resid)
        return this
    }

    fun visible(id: Int): ViewHolderCreator<T> {
        findViewById<View>(id).visibility = View.VISIBLE
        return this
    }

    fun invisible(id: Int): ViewHolderCreator<T> {
        findViewById<View>(id).visibility = View.INVISIBLE
        return this
    }

    fun gone(id: Int): ViewHolderCreator<T> {
        findViewById<View>(id).visibility = View.GONE
        return this
    }

    fun visibility(id: Int, visibility: Int): ViewHolderCreator<T> {
        findViewById<View>(id).visibility = visibility
        return this
    }

    fun setTextColor(id: Int, color: Int): ViewHolderCreator<T> {
        val view: TextView = findViewById(id)
        view.setTextColor(color)
        return this
    }

    fun setTextSize(id: Int, sp: Int): ViewHolderCreator<T> {
        val view: TextView = findViewById(id)
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp.toFloat())
        return this
    }

    fun clicked(id: Int, listener: View.OnClickListener?): ViewHolderCreator<T> {
        findViewById<View>(id).setOnClickListener(listener)
        return this
    }

    fun itemClicked(listener: View.OnClickListener?): ViewHolderCreator<T> {
        itemView?.setOnClickListener(listener)
        return this
    }

    fun longClicked(id: Int, listener: OnLongClickListener?): ViewHolderCreator<T> {
        findViewById<View>(id).setOnLongClickListener(listener)
        return this
    }

    fun enable(id: Int, enable: Boolean): ViewHolderCreator<T> {
        findViewById<View>(id).setEnabled(enable)
        return this
    }

    fun enable(id: Int): ViewHolderCreator<T> {
        findViewById<View>(id).isEnabled = true
        return this
    }

    fun disable(id: Int): ViewHolderCreator<T> {
        findViewById<View>(id).isEnabled = false
        return this
    }

    fun addView(id: Int, vararg views: View?): ViewHolderCreator<T> {
        val viewGroup: ViewGroup = findViewById(id)
        for (view in views) {
            viewGroup.addView(view)
        }
        return this
    }

    fun addView(
        id: Int,
        view: View?,
        params: ViewGroup.LayoutParams?
    ): ViewHolderCreator<T> {
        val viewGroup: ViewGroup = findViewById(id)
        viewGroup.addView(view, params)
        return this
    }

    fun removeAllViews(id: Int): ViewHolderCreator<T> {
        val viewGroup: ViewGroup = findViewById(id)
        viewGroup.removeAllViews()
        return this
    }

    fun removeView(id: Int, view: View?): ViewHolderCreator<T> {
        val viewGroup: ViewGroup = findViewById(id)
        viewGroup.removeView(view)
        return this
    }

    private fun checkItemView() {
        if (itemView == null) {
            throw NullPointerException("itemView is null")
        }
    }
}