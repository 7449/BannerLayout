package com.bannersimple;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.Interface.OnBannerPageChangeListener;
import com.bannerlayout.animation.AccordionTransformer;
import com.bannerlayout.animation.BackgroundToForegroundTransformer;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.animation.CubeInTransformer;
import com.bannerlayout.animation.CubeOutTransformer;
import com.bannerlayout.animation.RotateDownTransformer;
import com.bannerlayout.bannerenum.BANNER_ANIMATION;
import com.bannerlayout.bannerenum.BANNER_ROUND_POSITION;
import com.bannerlayout.bannerenum.BANNER_TITLE_POSITION;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.util.ImageLoaderManage;
import com.bannerlayout.widget.BannerLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
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


        List<ImageModel> list = new LinkedList<>();
        list.add(new ImageModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "那个时候刚恋爱，这个时候放分手", "banner1"));
        list.add(new ImageModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "羞羞呢～", "banner2"));
        list.add(new ImageModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "腿不长 但细", "banner3"));
        list.add(new ImageModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "深夜了", "banner4"));


        /**
         * 自定义动画，这里直接用系统动画代替
         */
        List<BannerTransformer> transformers = new ArrayList<>();
        transformers.add(new RotateDownTransformer());
        transformers.add(new AccordionTransformer());
        transformers.add(new BackgroundToForegroundTransformer());
        transformers.add(new CubeOutTransformer());
        transformers.add(new CubeInTransformer());

        /**
         * 系统动画
         */
        List<BANNER_ANIMATION> enumTransformer = new ArrayList<>();
        enumTransformer.add(BANNER_ANIMATION.ACCORDION);
        enumTransformer.add(BANNER_ANIMATION.CUBE_IN);
        enumTransformer.add(BANNER_ANIMATION.CUBE_OUT);
        enumTransformer.add(BANNER_ANIMATION.DEFAULT);
        enumTransformer.add(BANNER_ANIMATION.DEPTH_PAGE);

        bannerLayout
                .initImageListResources(list) //自定义model类
//                .setImageLoaderManage(new ImageLoader()) //自己定义加载图片的方式
//                .setRoundContainerHeight(200)
//                .initImageArrayResources(mImage)
                .setTitleSetting(ContextCompat.getColor(this, R.color.colorPrimary), -1)
//                .addPromptBar(new PromptBarView(getBaseContext()))//initAdapter之前调用生效
//                .addOnBannerPageChangeListener(new BannerOnPage())
                .initAdapter()
                .initRound(true, true, true, null, BANNER_ROUND_POSITION.LEFT, BANNER_TITLE_POSITION.CENTERED)
                .setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position, Object model) {
                        ImageModel imageModel = (ImageModel) model;
                        Toast.makeText(getApplicationContext(), imageModel.getTestText(), Toast.LENGTH_SHORT).show();
                        //如果是array  返回的object是传入的数组对象
//                        int[] image = (int[]) model;
//                        Toast.makeText(getApplicationContext(), image[position], Toast.LENGTH_SHORT).show();

                    }
                })
//                .setBannerTransformer(new FlipVerticalTransformer())  //切换动画效果
                .setBannerSystemTransformerList(enumTransformer) //开启随机动画
//                .setDuration(3000) //切换速度
                .start();
    }

    public class ImageLoader implements ImageLoaderManage {

        @Override
        public void display(Context context, ImageView imageView, Object url, Object model) {
            Picasso.with(context).load((String) url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
        }
    }

    public class BannerOnPage implements OnBannerPageChangeListener {

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
