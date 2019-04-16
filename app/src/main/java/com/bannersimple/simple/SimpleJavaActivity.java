package com.bannersimple.simple;

import android.os.Bundle;

import com.bannerlayout.BannerInfo;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bannersimple.bean.SimpleData;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * by y.
 * <p>
 * Description:
 */
public class SimpleJavaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        BannerLayout bannerLayout = findViewById(R.id.banner_java);
        bannerLayout
                .initPageNumView()
                .initTips()
                .resource(SimpleData.INSTANCE.initModel())
                .switchBanner(true);
    }


    private static class SimpleJavaBean implements BannerInfo {

        private String url;
        private String title;

        SimpleJavaBean(String url, String title) {
            this.url = url;
            this.title = title;
        }

        @NotNull
        @Override
        public String getBannerUrl() {
            return url;
        }

        @Nullable
        @Override
        public String getBannerTitle() {
            return title;
        }
    }
}
