package com.avengers.ironman_analysis

import android.graphics.Color
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.avengers.ironman.utils.DeviceUtils
import com.avengers.ironman.utils.DisplayUtils
import com.avengers.ironman_analysis.adapter.EfficientAdapter
import com.avengers.ironman_analysis.adapter.ViewHolderCreator
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.NetworkUtils
import kotlinx.android.synthetic.main.fragment_app_info.*


class AppInfoFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return AppInfoFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_app_info

    override fun init() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = EfficientAdapter<AppInfo>()
            .register(object : ViewHolderCreator<AppInfo>() {
                override fun isForViewType(data: AppInfo?, position: Int): Boolean =
                    data?.viewType == 1

                override fun getResourceId(): Int = R.layout.item_appinfo
                override fun onBindViewHolder(
                    data: AppInfo?, items: MutableList<AppInfo>?, position: Int,
                    holder: ViewHolderCreator<AppInfo>, payloads: MutableList<Any>
                ) {
                    setTextColor(R.id.title, Color.parseColor("#9E9A9C"))
                    setTextSize(R.id.title, 14)
                    setText(R.id.title, data?.title)
                    setText(R.id.desc, data?.desc)
                }
            }).register(object : ViewHolderCreator<AppInfo>() {
                override fun isForViewType(data: AppInfo?, position: Int): Boolean =
                    data?.viewType == 0

                override fun getResourceId(): Int = R.layout.item_appinfo
                override fun onBindViewHolder(
                    data: AppInfo?, items: MutableList<AppInfo>?, position: Int,
                    holder: ViewHolderCreator<AppInfo>, payloads: MutableList<Any>
                ) {
                    setText(R.id.title, data?.title)
                    setText(R.id.desc, data?.desc)
                }
            }).attach(recyclerView)

        val list = mutableListOf<AppInfo>()
        val pageInfo = DeviceUtils.getPackageInfo(activity)
        list.add(AppInfo("App信息", "", 1))
        list.add(AppInfo("包名", pageInfo.packageName))
        list.add(AppInfo("应用版本名", pageInfo.versionName))
        list.add(AppInfo("应用版本号", pageInfo.versionCode.toString()))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.add(AppInfo("最低系统版本号", context?.applicationInfo?.minSdkVersion.toString()))
        }
        list.add(AppInfo("目标系统版本号", context?.applicationInfo?.targetSdkVersion.toString()))
        list.add(AppInfo("手机信息", "", 1))
        list.add(AppInfo("手机型号", DeviceUtils.getPhoneModel()))
        list.add(AppInfo("系统版本", DeviceUtils.getPhoneSysVersion()))
        list.add(AppInfo("sd卡剩余空间", DeviceUtils.getSDCardSpace(activity)))
        list.add(AppInfo("系统剩余空间", DeviceUtils.getRomSpace(activity)))
//        list.add(AppInfo("当前时刻内存使用情况", DeviceUtils.getMemorySpace(activity)))
        list.add(AppInfo("分辨率", DisplayUtils.getResolution(activity)))
        list.add(AppInfo("屏幕尺寸", DisplayUtils.getScreenInch(activity).toString()))
        list.add(AppInfo("是否Root", DeviceUtils.isDeviceRooted().toString()))
        list.add(AppInfo("DENSITY(密度)", DeviceUtils.getScreenDensity().toString()))
        list.add(AppInfo("IP", NetworkUtils.getIPAddress(true) ?: "null"))
        list.add(AppInfo("MAC", com.blankj.utilcode.util.DeviceUtils.getMacAddress() ?: "null"))
        list.add(AppInfo("Sign MD5", AppUtils.getAppSignatureMD5()))
        list.add(AppInfo("Sign SHA1", AppUtils.getAppSignatureSHA1()))
        list.add(AppInfo("Sign SHA256", AppUtils.getAppSignatureSHA256()))
        list.add(AppInfo("", ""))
        adapter.submitList(list)
    }
}

data class AppInfo(var title: String, var desc: String, var viewType: Int = 0)

