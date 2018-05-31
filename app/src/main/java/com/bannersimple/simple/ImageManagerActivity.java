package com.bannersimple.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bannersimple.bean.SimpleData;
import com.bannersimple.imagemanager.FrescoSimpleImageManager;
import com.bannersimple.imagemanager.ImageLoaderSimpleManager;
import com.bannersimple.imagemanager.PicassoSimpleImageManager;

/**
 * by y on 2017/5/28.
 */

public class ImageManagerActivity extends AppCompatActivity {

    private BannerLayout frescoBanner, imageLoaderBanner, picassoBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagemanager);
        frescoBanner = findViewById(R.id.fresco_banner);
        imageLoaderBanner = findViewById(R.id.imageloader_banner);
        picassoBanner = findViewById(R.id.picasso_banner);


        frescoBanner
                .setImageLoaderManager(new FrescoSimpleImageManager())
                .initListResources(SimpleData.initModel())
                .switchBanner(true);

        imageLoaderBanner
                .setImageLoaderManager(new ImageLoaderSimpleManager())
                .initListResources(SimpleData.initModel())
                .switchBanner(false);

        picassoBanner
                .setImageLoaderManager(new PicassoSimpleImageManager())
                .initListResources(SimpleData.initModel())
                .switchBanner(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        frescoBanner.clearBanner();
        frescoBanner = null;
        imageLoaderBanner.clearBanner();
        imageLoaderBanner = null;
        picassoBanner.clearBanner();
        picassoBanner = null;
    }
}
