package androidx.banner.example

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.banner.listener.BannerItem
import androidx.viewbinding.ViewBinding

const val IMAGE_URL_1 =
    "https://p1.img.cctvpic.com/photoworkspace/2018/05/18/2018051814594647287.jpg"
const val IMAGE_URL_2 =
    "https://p1.img.cctvpic.com/photoworkspace/2018/05/18/2018051814220084352.jpg"
const val IMAGE_URL_3 =
    "https://p1.img.cctvpic.com/photoworkspace/2018/05/18/2018051814245872100.jpg"
const val IMAGE_URL_4 =
    "https://p1.img.cctvpic.com/photoworkspace/2018/05/18/2018051814175817985.jpg"

const val TITLE_1 = "治愈系小可爱和你说晚安~"
const val TITLE_2 = "太妃糖：麻麻，我走啦！"
const val TITLE_3 = "冷静冷静，这也太有爱了吧~"
const val TITLE_4 = "福豹：跟我一起嗨~"

fun newModel(): ArrayList<SimpleBannerItem> {
    val modules = ArrayList<SimpleBannerItem>()
    modules.add(SimpleBannerItem(IMAGE_URL_1, TITLE_1))
    modules.add(SimpleBannerItem(IMAGE_URL_2, TITLE_2))
    modules.add(SimpleBannerItem(IMAGE_URL_3, TITLE_3))
    modules.add(SimpleBannerItem(IMAGE_URL_4, TITLE_4))
    return modules
}

class SimpleBannerItem(image: Any, title: String) : BannerItem {
    override val bannerUrl: String = image.toString()
    override val bannerTitle: String = title
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val invoke = bindingInflater.invoke(layoutInflater)
        setContentView(invoke.root)
        invoke
    }
