/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bannerlayout.animation

import android.view.View

class RotateUpTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val rotation = ROT_MOD * position

        page.pivotX = width * 0.5f
        page.pivotY = 0f
        page.translationX = 0f
        page.rotation = rotation
    }

    override fun isPagingEnabled(): Boolean {
        return true
    }

    companion object {

        private val ROT_MOD = -15f
    }

}
