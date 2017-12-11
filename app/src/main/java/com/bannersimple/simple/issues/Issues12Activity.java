package com.bannersimple.simple.issues;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;

import static com.bannersimple.bean.SimpleData.initModel;


/**
 * by y on 25/07/2017.
 * <p>
 * Issues sample : https://github.com/7449/BannerLayout/issues/12
 */

public class Issues12Activity extends AppCompatActivity {

    private static final String TAG = Issues12Activity.class.getSimpleName();

    private BannerLayout bannerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_12);
        bannerLayout = (BannerLayout) findViewById(R.id.issues_12_banner);

        bannerLayout
                .initPageNumView()
                .initTips()
                .setTipsDotsSelector(R.drawable.banner)
                .setPageNumViewMargin(12, 12, 12, 12)
                .initListResources(initModel())
                .switchBanner(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bannerLayout != null) {
            bannerLayout.clearBanner();
        }
    }

}
