package com.bannersimple;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * by y on 2017/5/16
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

}
