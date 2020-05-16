package com.avengers.ironmanpro

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.avengers.ironman_analysis.adapter.EfficientAdapter
import com.avengers.ironman_analysis.adapter.ViewHolderCreator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_large_image_test.*

class LargeImageTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_large_image_test)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = EfficientAdapter<String>()
                .register(object : ViewHolderCreator<String>() {
                    override fun isForViewType(data: String?, position: Int): Boolean = data is String
                    override fun getResourceId(): Int = R.layout.item_large_image_test
                    override fun onBindViewHolder(
                            data: String?, items: MutableList<String>?, position: Int,
                            holder: ViewHolderCreator<String>, payloads: MutableList<Any>
                    ) {
                        val image = findViewById<ImageView>(R.id.image)
                        Glide.with(this@LargeImageTestActivity).load(data).into(image)
                    }
                }).attach(recyclerView)

        val list = mutableListOf<String>()
        list.add("https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg")
        list.add("https://img95.699pic.com/photo/50046/5562.jpg_wh300.jpg")
        list.add("https://static.runoob.com/images/demo/demo2.jpg")
        list.add("https://img95.699pic.com/photo/50055/5642.jpg_wh300.jpg")
        list.add("http://pic1.win4000.com/wallpaper/a/5832b75a99770.jpg")
        list.add("http://pic1.win4000.com/wallpaper/c/584e0d51534b6.jpg")
        list.add("https://www.qqkw.com/d/file/p/2018/06-15/feec0eedfdbbe29ebefc2f7bc97e4b1d.jpg")
        adapter.submitList(list)
    }
}