package com.bannersimple.bean

import java.util.*

/**
 * by y on 2017/5/28.
 */

object SimpleData {

    const val IMAGE_URL_1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309788159&di=67eb598399be0844195f1736a1a0507b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F503d269759ee3d6d2a5b193849166d224e4adea0.jpg"
    const val IMAGE_URL_2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309790818&di=d1aba9f256a4b540eb8a5168aa52da9f&imgtype=0&src=http%3A%2F%2Fpic3.16pic.com%2F00%2F55%2F48%2F16pic_5548763_b.jpg"
    const val IMAGE_URL_3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309791841&di=bf035fe9fb5fe456cb10c156127584d5&imgtype=0&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140915%2F0034034424700073_b.jpg"
    const val IMAGE_URL_4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309793610&di=b4d40aa123ac377de0b7d1828d7b906a&imgtype=0&src=http%3A%2F%2Fa4.topitme.com%2Fo%2F201101%2F03%2F12939857045446.jpg"

    const val TITLE_1 = "title A"
    const val TITLE_2 = "title B"
    const val TITLE_3 = "title C"
    const val TITLE_4 = "title D"

    fun initModel(): MutableList<SimpleBannerModel> {
        val modules = ArrayList<SimpleBannerModel>()
        modules.add(SimpleBannerModel(IMAGE_URL_1, TITLE_1))
        modules.add(SimpleBannerModel(IMAGE_URL_2, TITLE_2))
        modules.add(SimpleBannerModel(IMAGE_URL_3, TITLE_3))
        modules.add(SimpleBannerModel(IMAGE_URL_4, TITLE_4))
        return modules
    }
}
