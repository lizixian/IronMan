package com.avengers.ironman_analysis

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.format.Formatter
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.TextAppearanceSpan
import android.text.style.URLSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.avengers.ironman.largeimage.LargeImageInfo
import com.avengers.ironman.largeimage.LargeImageManager
import com.avengers.ironman_analysis.adapter.EfficientAdapter
import com.avengers.ironman_analysis.adapter.ViewHolderCreator
import com.avengers.ironman_analysis.util.Spanny
import kotlinx.android.synthetic.main.layout_large_image_detail.*


class LargeImageDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_large_image_detail)
        val id = intent.getStringExtra("id")
        val largeInfo = LargeImageManager.lagerImageCache[id]

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(TimeLineItemDecoration())

        val adapter = EfficientAdapter<Spanny>().register(object : ViewHolderCreator<Spanny>() {
            override fun isForViewType(data: Spanny?, position: Int): Boolean = position == 0
            override fun getResourceId(): Int = R.layout.item_large_image_detail_top
            override fun onBindViewHolder(data: Spanny?, items: MutableList<Spanny>?, position: Int, holder: ViewHolderCreator<Spanny>, payloads: MutableList<Any>) {
                setImageBitmap(R.id.imageView, largeInfo?.bitmap)
            }
        }).register(object : ViewHolderCreator<Spanny>() {
            override fun isForViewType(data: Spanny?, position: Int): Boolean = position != 0
            override fun getResourceId(): Int = R.layout.item_large_image_detail_item
            override fun onBindViewHolder(data: Spanny?, items: MutableList<Spanny>?, position: Int, holder: ViewHolderCreator<Spanny>, payloads: MutableList<Any>) {
                val textView = findViewById<TextView>(R.id.item_title)
                textView.movementMethod = LinkMovementMethod.getInstance();
                textView.text = data
            }
        }).attach(recyclerView)

        adapter.submitList(initItemDatas(largeInfo))
    }

    private fun initItemDatas(largeInfo: LargeImageInfo?): MutableList<Spanny> {
        val list = mutableListOf<Spanny>()
        val textSpan = TextAppearanceSpan(this, android.R.style.TextAppearance_Medium)
        val sizeSpan = RelativeSizeSpan(1.2f)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#676464"))

        largeInfo?.let {
            list.add(Spanny("图片加载来源", textSpan, sizeSpan)
                    .append("\n\n" + it.framework))
            list.add(Spanny("图片url", textSpan)
                    .append("\n\n" + it.url, colorSpan, URLSpan(it.url)))

            val sizeValue = "bitmap[" + it.width + "," + it.height + "]->View[" + it.viewWidth + "," + it.viewHeight + "]"
            var caveat = ""
            if (it.width > it.viewWidth || it.height > it.viewHeight) {
                caveat += "\n警告："
                if (it.width > it.viewWidth) {
                    caveat += "\n图片 bitmap 尺寸宽度比控件尺寸大小大 " + (it.width - it.viewWidth)
                }
                if (it.height > it.viewHeight) {
                    caveat += "\n图片 bitmap 尺寸高度比控件尺寸大小大 " + (it.height - it.viewHeight)
                }
            }

            list.add(Spanny("图片尺寸", textSpan, sizeSpan)
                    .append("\n\n" + sizeValue, colorSpan)
                    .append(caveat, ForegroundColorSpan(Color.RED))
            )
            list.add(Spanny("Bitmap 所占内存大小", textSpan, sizeSpan)
                    .append("\n\n" + getBitmapSize(largeInfo.bitmap)))
            list.add(Spanny("控件所属 Activity", textSpan, sizeSpan)
                    .append("\n\n" + it.activity, colorSpan))
            list.add(Spanny("控件 ID", textSpan, sizeSpan)
                    .append("\n\n" + it.viewId.replace(" ", ""), colorSpan))
            list.add(Spanny("控件布局层级", textSpan, sizeSpan)
                    .append("\n\n" + it.layoutLevel, colorSpan))
        }
        return list
    }

    private fun getBitmapSize(bitmap: Bitmap?): String {
        val bytes = bitmap?.let {
            bitmap.rowBytes * bitmap.height
        } ?: 0
        return Formatter.formatShortFileSize(this, bytes.toLong())
    }

}