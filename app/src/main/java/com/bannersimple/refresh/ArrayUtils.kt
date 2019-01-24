package com.bannersimple.refresh

import com.bannersimple.bean.SimpleBannerModel
import java.util.*

/**
 * by y on 2017/4/11
 */

object ArrayUtils {

    fun initArrayResources(
            imageArray: Array<Any>,
            imageArrayTitle: Array<String>): MutableList<SimpleBannerModel> {
        val url = Arrays.asList(*imageArray)
        val title = Arrays.asList(*imageArrayTitle)
        val imageArrayList = ArrayList<SimpleBannerModel>()
        var bannerModel: SimpleBannerModel
        for (i in url.indices) {
            bannerModel = SimpleBannerModel(url[i], title[i])
            imageArrayList.add(bannerModel)
        }
        return imageArrayList
    }

}
