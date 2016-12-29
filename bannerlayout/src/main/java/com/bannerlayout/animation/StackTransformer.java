package com.bannerlayout.animation;

import android.view.View;

@SuppressWarnings("ALL")
public class StackTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setTranslationX(position < 0 ? 0f : -view.getWidth() * position);
    }

}
