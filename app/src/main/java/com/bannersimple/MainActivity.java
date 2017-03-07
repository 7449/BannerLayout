package com.bannersimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bannerlayout.model.BannerModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MainAdapter());
        setContentView(recyclerView);
//        setContentView(R.layout.activity_main);

//        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner);

//        bannerLayout
//                .initListResources(initSystemNetWorkModel())
//                .setErrorImageView(R.drawable.ic_favorite_black_24dp)
//                .setDuration(3000)
//                .setTipsWidthAndHeight(BannerLayout.MATCH_PARENT, 100)
//                .initTipsDotsSelector(R.drawable.banner)
//                .setVertical(true)
//                .setViewPagerTouchMode(true)
//                .setTitleSetting(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), 23)
//                .setTipsBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent))
//                .setTipsSite(BannerLayout.CENTER_IN_PARENT)
//                .setTitleMargin(50,22)
//                .setTitleSite(BannerLayout.CENTER_IN_PARENT)
//                .setDotsWidthAndHeight(50,80)
//                .setDotsSite(BannerLayout.CENTER_IN_PARENT)
//                .setDotsMargin(20,20)
//                .setNormalRadius(5f)
//                .setEnabledRadius(5f)
//                .setNormalColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
//                .setEnabledColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBackground))
//                .setBannerTransformer(BannerAnimation.ACCORDION)
//                .setBannerTransformer(new VerticalTransformer())
//                .setPageNumViewRadius(0.5f)
//                .setPageNumViewPadding(20, 20, 20, 20)
//                .setPageNumViewMargin(20, 20, 20, 20)
//                .setPageNumViewTextColor(getColor(R.color.colorAccent))
//                .setPageNumViewBackgroundColor(getColor(R.color.colorAccent))
//                .setPageNumViewTextSize(33)
//                .setPageNumViewSite(BannerLayout.PAGE_NUM_VIEW_SITE_TOP_CENTER)
//                .setPageNumViewMark("%")
//                .setPageNumViewMark(getString(R.string.mark))
//                .initPageNumView()
//                .initTips()
//                .initTips(true, true, true)
//                .start(true)
//                .setOnBannerClickListener(new OnBannerClickListener() {
//                    @Override
//                    public void onBannerClick(View view, int position, Object model) {
//                        Toast.makeText(view.getContext(), "" + position, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }


    /**
     * Comes with the Model class, the use of network data
     */
    private List<BannerModel> initSystemNetWorkModel() {
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "At that time just love, this time to break up"));
        mDatas.add(new BannerModel("error image test", "Shame it ~"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "The legs are not long but thin"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "Late at night"));
        return mDatas;
    }

}
