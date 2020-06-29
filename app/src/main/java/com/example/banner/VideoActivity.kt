package com.example.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.Jzvd
import com.android.banner.shadow.addTipLayout
import com.example.R
import com.example.VideoImageLoader
import com.example.newVideoModel
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoBanner
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
