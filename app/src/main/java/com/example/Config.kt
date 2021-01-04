package com.example

import com.android.banner.BannerInfo

const val IMAGE_URL_1 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.zhezhier.com%2Fupload%2Ff%2F2e%2Ff2e138821cc244d4efa92b23736daa68.jpg&refer=http%3A%2F%2Fpic.zhezhier.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1612334661&t=f158875bbb521a0214a2708f012524be"
const val IMAGE_URL_2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309790818&di=d1aba9f256a4b540eb8a5168aa52da9f&imgtype=0&src=http%3A%2F%2Fpic3.16pic.com%2F00%2F55%2F48%2F16pic_5548763_b.jpg"
const val IMAGE_URL_3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309791841&di=bf035fe9fb5fe456cb10c156127584d5&imgtype=0&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140915%2F0034034424700073_b.jpg"
const val IMAGE_URL_4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555424496328&di=4387e412d0703a9a68ba5e490700bb80&imgtype=0&src=http%3A%2F%2Fpic8.nipic.com%2F20100713%2F1954049_091647155567_2.jpg"
const val VIDEO_URL_1 = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
const val VIDEO_URL_2 = "http://jzvd.nathen.cn/63f3f73712544394be981d9e4f56b612/69c5767bb9e54156b5b60a1b6edeb3b5-5287d2089db37e62345123a1be272f8b.mp4"

const val TITLE_1 = "title A"
const val TITLE_2 = "title B"
const val TITLE_3 = "title C"
const val TITLE_4 = "title D"
const val TITLE_VIDEO_1 = "title video 1"
const val TITLE_VIDEO_2 = "title video 2"

fun newModel(): ArrayList<SimpleBannerInfo> {
    val modules = ArrayList<SimpleBannerInfo>()
    modules.add(SimpleBannerInfo(IMAGE_URL_1, TITLE_1))
    modules.add(SimpleBannerInfo(IMAGE_URL_2, TITLE_2))
    modules.add(SimpleBannerInfo(IMAGE_URL_3, TITLE_3))
    modules.add(SimpleBannerInfo(IMAGE_URL_4, TITLE_4))
    return modules
}

fun newVideoModel(): ArrayList<SimpleBannerInfo> {
    val modules = ArrayList<SimpleBannerInfo>()
    modules.add(SimpleBannerInfo(VIDEO_URL_1, TITLE_VIDEO_1, true))
    modules.add(SimpleBannerInfo(IMAGE_URL_1, TITLE_1))
    modules.add(SimpleBannerInfo(IMAGE_URL_2, TITLE_2))
    modules.add(SimpleBannerInfo(VIDEO_URL_2, TITLE_VIDEO_2, true))
    modules.add(SimpleBannerInfo(IMAGE_URL_3, TITLE_3))
    modules.add(SimpleBannerInfo(IMAGE_URL_4, TITLE_4))
    return modules
}

class SimpleBannerInfo(image: Any, val title: String, val isVideo: Boolean = false) : BannerInfo {
    override val bannerUrl: String = image.toString()
    override val bannerTitle: String = title
}
