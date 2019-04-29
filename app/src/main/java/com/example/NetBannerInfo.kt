package com.example

import com.bannerlayout.BannerInfo


/**
 * by y on 2016/10/24
 */

const val IMAGE_URL_1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555424445326&di=37dedee3d5fe4dc5b1b0c8a95db525ec&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F99e897262540ef4d4b08a6903233ef90628f97765726e-hR8I8H_fw658"
const val IMAGE_URL_2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309790818&di=d1aba9f256a4b540eb8a5168aa52da9f&imgtype=0&src=http%3A%2F%2Fpic3.16pic.com%2F00%2F55%2F48%2F16pic_5548763_b.jpg"
const val IMAGE_URL_3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548309791841&di=bf035fe9fb5fe456cb10c156127584d5&imgtype=0&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140915%2F0034034424700073_b.jpg"
const val IMAGE_URL_4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555424496328&di=4387e412d0703a9a68ba5e490700bb80&imgtype=0&src=http%3A%2F%2Fpic8.nipic.com%2F20100713%2F1954049_091647155567_2.jpg"

const val TITLE_1 = "title A"
const val TITLE_2 = "title B"
const val TITLE_3 = "title C"
const val TITLE_4 = "title D"

fun newModel(): ArrayList<NetBannerInfo> {
    val modules = ArrayList<NetBannerInfo>()
    modules.add(NetBannerInfo(IMAGE_URL_1, TITLE_1))
    modules.add(NetBannerInfo(IMAGE_URL_2, TITLE_2))
    modules.add(NetBannerInfo(IMAGE_URL_3, TITLE_3))
    modules.add(NetBannerInfo(IMAGE_URL_4, TITLE_4))
    return modules
}

class NetBannerInfo(image: Any, var title: String) : BannerInfo {
    override val bannerUrl: String = image.toString()
    override val bannerTitle: String = title
}
