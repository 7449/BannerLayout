package com.bannersimple;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bannerlayout.model.BannerModel;
import com.bannerlayout.widget.BannerLayout;
import com.bannerlayout.widget.BannerRound;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.bannerLayout);
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "那个时候刚恋爱，这个时候放分手"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "羞羞呢～"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "腿不长 但细"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "深夜了"));

        List<BannerModel> mBanner = new ArrayList<>();
        mBanner.add(new BannerModel(R.drawable.banner1));
        mBanner.add(new BannerModel(R.drawable.banner2));
        mBanner.add(new BannerModel(R.drawable.banner3));
        mBanner.add(new BannerModel(R.drawable.banner4));

        int[] mImage = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
        String[] mTitle = new String[]{"bannerl", "banner2", "banner3"};
        bannerLayout
                .initImageListResources(mDatas)
//                .setImageLoaderManage(new ImageLoader()) //自己定义加载图片的方式
//                .setRoundContainerHeight(200)
                .setTitleSetting(ContextCompat.getColor(this, R.color.colorPrimary), -1)
//                .addPromptBar(new PromptBarView(getBaseContext()))//initAdapter之前调用生效
//                .addOnBannerPageChangeListener(new BannerOnPage())
                .initAdapter()
                .initRound(true, true, true, null, BannerRound.BANNER_ROUND_POSITION.LEFT, BannerRound.BANNER_TITLE_POSITION.CENTERED)
                .start(true);

    }

    public class BannerOnPage implements BannerLayout.OnBannerPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            Log.i(getClass().getSimpleName(), position + "");
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
