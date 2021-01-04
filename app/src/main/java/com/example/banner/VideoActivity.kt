package com.example.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.Jzvd
import com.android.banner.shadow.addTipLayout
import com.example.VideoImageLoader
import com.example.databinding.ActivityVideoBinding
import com.example.newVideoModel
import com.example.viewBinding

class VideoActivity : AppCompatActivity() {

    private val viewBind by viewBinding(ActivityVideoBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind.videoBanner
                .delayTime(5000)
                .setOnBannerImageLoader(VideoImageLoader())
                .resource(newVideoModel())
                .addTipLayout()
    }

    override fun onDestroy() {
        Jzvd.releaseAllVideos()
        super.onDestroy()
    }
}
