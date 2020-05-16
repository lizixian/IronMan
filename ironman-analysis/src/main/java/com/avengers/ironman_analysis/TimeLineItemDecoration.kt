package com.avengers.ironman_analysis

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TimeLineItemDecoration : RecyclerView.ItemDecoration() {
    private val mCircleSize = 14f        //圆圈的半径
    private var mPaint: Paint = Paint()  //画笔
    private val mPaintSize = 6f          //画笔宽度
    private val mPaintColor = "#B8B8B8"  //画笔默认颜色

    init {
        mPaint.strokeWidth = mPaintSize
        mPaint.color = Color.parseColor(mPaintColor)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (view != parent.getChildAt(0)) {
            val rect = Rect(200, 0, 0, 0) //使item从outRect中右移200px
            outRect.set(rect)
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val left = child.left.toFloat()
            val top = child.top.toFloat()
            val bottom = child.bottom.toFloat()
            val right = child.right.toFloat()
            val params = child.layoutParams as RecyclerView.LayoutParams
            val bottomMargin = params.bottomMargin.toFloat()
            val topMargin = params.topMargin.toFloat()

            val leftX = left / 2.toFloat()
            val height = child.height.toFloat()

            if (childCount == 1) {
                canvas.drawLine(leftX, top, leftX, bottom - height / 2, mPaint)
            } else {
                when (i) {
//                    0 -> {
//                        canvas.drawLine(leftX, top + height / 2, leftX, bottom + bottomMargin, mPaint);
//                    }
                    childCount - 1 -> {
                        canvas.drawLine(leftX, top - topMargin, leftX, bottom - height / 2, mPaint);
                    }
                    else -> {
                        canvas.drawLine(leftX, top - topMargin, leftX, bottom - height / 2, mPaint);
                        canvas.drawLine(leftX, top + height / 2, leftX, bottom + bottomMargin, mPaint);
                    }
                }
            }
            if (i != 0) {
                canvas.drawCircle(leftX, top + height / 2, mCircleSize, mPaint)
            }
        }
    }
}