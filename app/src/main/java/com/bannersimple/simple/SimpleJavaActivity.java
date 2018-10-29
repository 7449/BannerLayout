package com.bannersimple.simple;

import android.os.Bundle;

import com.bannerlayout.BannerModelCallBack;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

        List<SimpleJavaBean> list = new ArrayList<>();
        list.add(new SimpleJavaBean("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "At that time just love, this time to break up"));
        list.add(new SimpleJavaBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491588490192&di=c7c9dfd2fc4b1eeb5a4a874ec9a30d1d&imgtype=0&src=http%3A%2F%2Fmvimg2.meitudata.com%2F55713dd0165c89055.jpg", ""));
        list.add(new SimpleJavaBean("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "The legs are not long but thin"));
        list.add(new SimpleJavaBean("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "Late at night"));
        BannerLayout bannerLayout = findViewById(R.id.banner_java);
        bannerLayout
                .initPageNumView()
                .initTips()
                .resource(list)
                .switchBanner(true);
    }


    private static class SimpleJavaBean implements BannerModelCallBack {

        private String url;
        private String title;

        SimpleJavaBean(String url, String title) {
            this.url = url;
            this.title = title;
        }

        @Override
        public String getBannerUrl() {
            return url;
        }

        @NotNull
        @Override
        public String getBannerTitle() {
            return title;
        }
    }
}
