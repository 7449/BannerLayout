package com.bannersimple.simple.issues;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bannersimple.SimpleOnBannerChangeListener;
import com.bannersimple.bean.SimpleBannerModel;

import java.util.List;

import static com.bannersimple.bean.SimpleData.getAlterData;
import static com.bannersimple.bean.SimpleData.getData;
import static com.bannersimple.bean.SimpleData.getlnstagramData;


/**
 * by y on 25/07/2017.
 * <p>
 * Issues sample : https://github.com/7449/BannerLayout/issues/10
 * <p>
 * test:
 * onePlus 3T  1080;
 * virtual machine  480;
 */

/**
 * Just a simple example, so only two resolutions are tested
 */
public class Issues10Activity extends AppCompatActivity {

    private static final String TAG = Issues10Activity.class.getSimpleName();

    private BannerLayout bannerLayout;
    private BannerLayout bannerlnstagram;
    private LinearLayout linearLayout;

    private boolean isShowTips = false;
    private int preEnablePosition = 0;
    private int sizelnstagram;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        bannerLayout = (BannerLayout) findViewById(R.id.banner);
        bannerlnstagram = (BannerLayout) findViewById(R.id.banner_lnstagram);
        linearLayout = (LinearLayout) findViewById(R.id.ll_view);

        test();
        testlnstagram();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bannerLayout != null) {
            bannerLayout.clearBanner();
        }
        if (bannerlnstagram != null) {
            bannerlnstagram.clearBanner();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void testlnstagram() {
        List<SimpleBannerModel> data = getlnstagramData();
        bannerlnstagram
                .setPageNumViewMargin(10)
                .initPageNumView()
                .initListResources(data)
                .setDelayTime(1000)
                .switchBanner(false);


        //  dots
        sizelnstagram = data.size();
        linearLayout.removeAllViews();
        for (int i = 0; i < sizelnstagram; i++) {
            View view = new View(this);
            view.setBackground(ContextCompat.getDrawable(this, R.drawable.banner));
            view.setEnabled(i == 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            view.setLayoutParams(params);
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER;
            params.leftMargin = 5;
            params.rightMargin = 5;
            linearLayout.addView(view);
        }

        bannerlnstagram
                .addOnPageChangeListener(
                        new SimpleOnBannerChangeListener() {
                            @Override
                            public void onPageSelected(int position) {
                                super.onPageSelected(position);
                                linearLayout.getChildAt(position).setEnabled(true);
                                linearLayout.getChildAt(preEnablePosition).setEnabled(false);
                                preEnablePosition = position;
                                View startView = linearLayout.getChildAt(0);
                                View endView = linearLayout.getChildAt(sizelnstagram - 1);
                                if (position == sizelnstagram - 1) {
                                    startView.setScaleX(0.6f);
                                    startView.setScaleY(0.6f);
                                    endView.setScaleX(1);
                                    endView.setScaleY(1);
                                } else if (position == 0) {
                                    startView.setScaleX(1);
                                    startView.setScaleY(1);
                                    endView.setScaleX(0.6f);
                                    endView.setScaleY(0.6f);
                                }
                            }
                        });
    }


    private void test() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        Log.i(TAG, dm.widthPixels + "   " + dm.heightPixels);
        int dotWidthAndHeight;
        int dotMargin;
        if (dm.widthPixels >= 1080) {
            dotWidthAndHeight = 15;
            dotMargin = 6;
        } else {
            dotWidthAndHeight = 6;
            dotMargin = 3;
        }
        bannerLayout
                .setPageNumViewMargin(10)
                .setDotsSite(BannerLayout.CENTER)
                .setDotsWidthAndHeight(dotWidthAndHeight, dotWidthAndHeight)
                .setDotsMargin(dotMargin)
                .initTips()
                .initPageNumView()
                .initListResources(getData())
                .switchBanner(true);
        findViewById(R.id.btn_alter_count)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alterBannerCount();
                            }
                        });
    }

    private void alterBannerCount() {
        if (bannerLayout == null) {
            return;
        }
        List<SimpleBannerModel> alterData;
        if (!isShowTips) {
            alterData = getAlterData();
        } else {
            alterData = getData();
        }
        isShowTips = !isShowTips;
        int size = alterData.size();
        if (size <= 1) {
            bannerLayout
                    .initTips(false, false, false)
                    .initListResources(alterData)
                    .switchBanner(false);
            Toast.makeText(this, "size <=1 , stopBanner , not show tipsLayout", Toast.LENGTH_SHORT).show();
        } else {
            bannerLayout
                    .initTips(true, true, true)
                    .initListResources(alterData)
                    .switchBanner(true);
            Toast.makeText(this, "size >1 , startBanner , show tipsLayout", Toast.LENGTH_SHORT).show();
        }
    }

}
