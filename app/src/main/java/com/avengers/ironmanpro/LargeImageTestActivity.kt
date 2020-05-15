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
        list.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.veer.com%2Fphoto%2F&psig=AOvVaw2H9hGXwZZw1OAFwMAifg7u&ust=1589621612875000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKj7oo_ItekCFQAAAAAdAAAAABAW")
        adapter.submitList(list)
    }
}