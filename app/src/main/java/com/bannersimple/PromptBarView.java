package com.bannersimple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * by y on 2016/11/1
 */

public class PromptBarView extends RelativeLayout {

    TextView textView;

    public PromptBarView(Context context) {
        super(context);
        init();
    }

    public PromptBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PromptBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textView = new TextView(getContext());
        textView.setText("this is promptbar textview");
        addView(textView);
        LayoutParams roundContainerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        roundContainerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        setLayoutParams(roundContainerParams);
    }

}
