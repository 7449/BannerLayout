package com.bannersimple.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.bannerlayout.Interface.OnBannerChangeListener;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bannersimple.bean.SimpleData;

/**
 * by y on 2017/5/28.
 */

public class TransformerActivity extends AppCompatActivity {

    private BannerLayout transformerBanner;
    private AppCompatTextView positionTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformer);
        transformerBanner = (BannerLayout) findViewById(R.id.transformer_banner);
        Spinner spinner = (Spinner) findViewById(R.id.transformer_spinner);
        positionTv = (AppCompatTextView) findViewById(R.id.banner_position);
        positionTv.setText("select position:" + 0);
        transformerBanner
                .setBannerTransformer(BannerLayout.ANIMATION_ACCORDION)
                .setDelayTime(300)
                .initListResources(SimpleData.initModel())
                .switchBanner(true)
                .addOnPageChangeListener(
                        new OnBannerChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                positionTv.setText("select position:" + position);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });


        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        transformerBanner.setBannerTransformer(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transformerBanner.clearBanner();
        transformerBanner = null;
    }
}
