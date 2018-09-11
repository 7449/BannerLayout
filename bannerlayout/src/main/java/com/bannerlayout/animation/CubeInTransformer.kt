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

class CubeInTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        // Rotate the fragment on the left or right edge
        page.pivotX = (if (position > 0) 0 else page.width).toFloat()
        page.pivotY = 0f
        page.rotationY = -90f * position
    }

    public override fun isPagingEnabled(): Boolean {
        return true
    }

}
