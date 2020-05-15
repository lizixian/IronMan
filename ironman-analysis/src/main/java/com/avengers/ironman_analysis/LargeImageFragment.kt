package com.avengers.ironman_analysis

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.avengers.ironman.largeimage.LargeImageInfo
import com.avengers.ironman.largeimage.LargeImageManager
import com.avengers.ironman_analysis.adapter.EfficientAdapter
import com.avengers.ironman_analysis.adapter.ViewHolderCreator
import kotlinx.android.synthetic.main.fragment_larget_image.*

class LargeImageFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return LargeImageFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_larget_image

    override fun init() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = EfficientAdapter<LargeImageInfo>().register(object :
            ViewHolderCreator<LargeImageInfo>() {
            override fun isForViewType(data: LargeImageInfo?, position: Int): Boolean =
                data is LargeImageInfo

            override fun getResourceId(): Int = R.layout.item_large_image
            override fun onBindViewHolder(
                data: LargeImageInfo?,
                items: MutableList<LargeImageInfo>?,
                position: Int,
                holder: ViewHolderCreator<LargeImageInfo>,
                payloads: MutableList<Any>
            ) {
//                setImageBitmap(R.id.largeImage, data?.bitmap)
                setText(R.id.item_pos, (position + 1).toString())
                setText(R.id.currActivity, data?.activity + "/" + data?.layoutLevel)
                setText(R.id.currTime, "监控于 " + data?.time)
                itemClicked(View.OnClickListener {
                    val intent = Intent(activity, LargeImageDetailActivity::class.java)
                    intent.putExtra("id", data?.id)
                    startActivity(intent)
                })
//                setText(R.id.image_url, data?.url)
//                setText(R.id.framework, data?.framework)
//                setText(
//                    R.id.image_size,
//                    "bitmap[" + data?.width + "," + data?.height + "]->View[" + data?.viewWidth + "," + data?.viewHeight + "]"
//                )
//                setText(R.id.view_id, data?.viewId)
//                setText(R.id.view_level, data?.layoutLevel)
            }
        }).attach(recyclerView)
        val list = mutableListOf<LargeImageInfo>()
        LargeImageManager.lagerImageCache.forEach {
            list.add(it.value)
        }
        adapter.submitList(list)
    }
}
