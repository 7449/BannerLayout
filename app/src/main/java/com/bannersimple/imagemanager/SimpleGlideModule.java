package com.bannersimple.imagemanager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class SimpleGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, GlideBuilder builder) {
        builder.setMemoryCache(
                new LruResourceCache(
                        new MemorySizeCalculator.Builder(context)
                                .setMemoryCacheScreens(2)
                                .build().getMemoryCacheSize()));
    }

}