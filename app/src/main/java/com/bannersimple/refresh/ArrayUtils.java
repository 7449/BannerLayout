package com.bannersimple.refresh;

import android.support.annotation.NonNull;

import com.bannersimple.bean.SimpleBannerModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * by y on 2017/4/11
 */

public class ArrayUtils {


    public static List<SimpleBannerModel> initArrayResources(@NonNull Object[] imageArray) {
        List<SimpleBannerModel> imageArrayList = new ArrayList<>();
        for (Object url : Arrays.asList(imageArray)) {
            imageArrayList.add(new SimpleBannerModel(url));
        }
        return imageArrayList;
    }

    public static List<SimpleBannerModel> initArrayResources(
            @NonNull Object[] imageArray,
            @NonNull String[] imageArrayTitle) {
        List<Object> url = Arrays.asList(imageArray);
        List<String> title = Arrays.asList(imageArrayTitle);
        List<SimpleBannerModel> imageArrayList = new ArrayList<>();
        SimpleBannerModel bannerModel;
        for (int i = 0; i < url.size(); i++) {
            bannerModel = new SimpleBannerModel();
            bannerModel.setImage(url.get(i));
            bannerModel.setTitle(title.get(i));
            imageArrayList.add(bannerModel);
        }

        return imageArrayList;
    }

}
