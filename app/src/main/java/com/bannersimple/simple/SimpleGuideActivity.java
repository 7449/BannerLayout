package com.bannersimple.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bannersimple.SimpleOnBannerChangeListener;
import com.bannersimple.bean.SimpleData;

public class SimpleGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_guide);
        final BannerLayout guideBanner = findViewById(R.id.banner_guide);
        final AppCompatButton guideButton = findViewById(R.id.button_guide);
        guideButton.setVisibility(View.GONE);
        guideBanner
                .initTips()
                .setGuide(true)
                .setTipsDotsSelector(R.drawable.banner)
                .setDotsSite(BannerLayout.CENTER)
                .setTipsWidthAndHeight(BannerLayout.MATCH_PARENT, 300)
                .setDotsWidthAndHeight(30, 30)
                .initListResources(SimpleData.update());

        guideBanner.addOnPageChangeListener(new SimpleOnBannerChangeListener() {
            @Override
            public void onPageSelected(int position) {
                guideButton.setVisibility(position == guideBanner.getImageList().size() - 1 ? View.VISIBLE : View.GONE);
            }
        });

        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "开启", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
