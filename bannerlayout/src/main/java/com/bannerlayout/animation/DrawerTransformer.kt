package com.bannerlayout.animation

import android.view.View

/**
 * Created by dkzwm on 2017/3/2.
 *
 * @author dkzwm
 */
class DrawerTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        if (position <= 0) {
            page.translationX = 0f
        } else if (position > 0 && position <= 1) {
            page.translationX = -page.width / 2 * position
        }
    }
}
