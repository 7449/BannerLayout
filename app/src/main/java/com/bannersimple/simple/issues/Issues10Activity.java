package com.bannersimple.simple.issues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bannersimple.bean.SimpleBannerModel;

import java.util.List;

import static com.bannersimple.bean.SimpleData.getAlterData;
import static com.bannersimple.bean.SimpleData.getData;


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

    private int dotWidthAndHeight = 0;
    private int dotMargin = 0;

    private boolean isShowTips = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        bannerLayout = (BannerLayout) findViewById(R.id.banner);

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        Log.i(TAG, dm.widthPixels + "   " + dm.heightPixels);


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bannerLayout != null) {
            bannerLayout.clearBanner();
        }
    }
}
