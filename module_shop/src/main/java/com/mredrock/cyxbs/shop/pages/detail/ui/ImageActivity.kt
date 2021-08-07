package com.mredrock.cyxbs.shop.pages.detail.ui

import android.Manifest
import android.annotation.SuppressLint
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import androidx.viewpager.widget.ViewPager
import com.aefottt.module_shop.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mredrock.cyxbs.common.config.DIR_PHOTO
import com.mredrock.cyxbs.common.ui.BaseActivity
import com.mredrock.cyxbs.common.utils.extensions.doPermissionAction
import com.mredrock.cyxbs.common.utils.extensions.saveImage
import com.mredrock.cyxbs.common.utils.extensions.setFullScreen
import com.mredrock.cyxbs.common.utils.extensions.toast
import com.mredrock.cyxbs.shop.pages.detail.adapter.BannerPagerAdapter
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.shop_activity_image.*

class ImageActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_activity_image)
        setTheme(R.style.Theme_MaterialComponents)
        setFullScreen()
        // 获取Intent传入的图片地址和当前位置
        val picUrlsArray = intent.getStringArrayExtra("picUrls")
        val pos = intent.getIntExtra("pos", 0)
        // 将传入的Array图片地址转换为List类型
        val picUrls = mutableListOf<String>()
        for (url in picUrlsArray) picUrls.add(url)
        // 进入界面后的初始化
        shop_image_tv.text = "${pos + 1} / ${picUrls.size}"
        shop_image_vp.currentItem = pos
        // 设置ViewPager
        shop_image_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {}
            override fun onPageSelected(position: Int) {
                shop_image_tv.text = "${position + 1} / ${picUrls.size}"
            }
            override fun onPageScrollStateChanged(state: Int) {}

        })
        shop_image_vp.adapter = BannerPagerAdapter(picUrls).apply {
            photoTapClick = { finish() }
            savePicClick = { bitmap, url ->
                doPermissionAction(Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    doAfterGranted {
                        MaterialAlertDialogBuilder(this@ImageActivity)
                            .setTitle(getString(R.string.shop_image_save_alert_dialog_title))
                            .setMessage(R.string.shop_image_save_alert_dialog_message)
                            .setPositiveButton("确定") { dialog, _ ->
                                val name = System.currentTimeMillis().toString() + url.split('/').lastIndex.toString()
                                Schedulers.io().scheduleDirect {

                                    this@ImageActivity.saveImage(bitmap, name)
                                    MediaScannerConnection.scanFile(this@ImageActivity,
                                        arrayOf(Environment.getExternalStorageDirectory().toString() + DIR_PHOTO),
                                        arrayOf("image/jpeg"),
                                        null)

                                    runOnUiThread {
                                        toast("图片保存于系统\"$DIR_PHOTO\"文件夹下哦")
                                        dialog.dismiss()
                                        setFullScreen()
                                    }

                                }
                            }
                            .setNegativeButton("取消") { dialog, _ ->
                                dialog.dismiss()
                                setFullScreen()
                            }
                            .show()
                    }
                }
            }
        }
    }
}