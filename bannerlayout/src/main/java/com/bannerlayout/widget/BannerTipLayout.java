package com.bannerlayout.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bannerlayout.bannerenum.BANNER_ROUND_POSITION;
import com.bannerlayout.bannerenum.BANNER_TIP_LAYOUT_POSITION;
import com.bannerlayout.bannerenum.BANNER_TITLE_POSITION;

/**
 * by y on 2016/10/25
 */
public class BannerTipLayout extends RelativeLayout {

    private TextView textView;
    private LinearLayout linearLayout;

    public BannerTipLayout(Context context) {
        super(context);
    }

    public BannerTipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerTipLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Initialize the dots
     *
     * @param roundSize           The number of small dots
     * @param roundSelector       State Selector
     * @param roundWidth          Small dots width
     * @param roundHeight         Small dots height
     * @param roundLeftMargin     Small dots marginLeft
     * @param roundRightMargin    Small dots marginRight
     * @param bannerRoundPosition Small dot in what position, three optional, left in the right by default on the right
     */
    public void setRound(int roundSize, int roundSelector, int roundWidth, int roundHeight, int roundLeftMargin, int roundRightMargin, BANNER_ROUND_POSITION bannerRoundPosition) {
        linearLayout = new LinearLayout(getContext());
        for (int i = 0; i < roundSize; i++) {
            View view = new View(getContext());
            view.setBackgroundResource(roundSelector);
            if (i == 0) {
                view.setEnabled(true);
            } else {
                view.setEnabled(false);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(roundWidth, roundHeight);
            view.setLayoutParams(params);
            params.leftMargin = roundLeftMargin;
            params.rightMargin = roundRightMargin;
            linearLayout.addView(view);
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        if (bannerRoundPosition == null) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            switch (bannerRoundPosition) {
                case CENTERED:
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    break;
                case LEFT:
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    break;
                case RIGHT:
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    break;
            }
        }
        addView(linearLayout, params);
    }

    /**
     * Initialize  this
     *
     * @param roundContainerWidth           BannerRound Width
     * @param roundContainerHeight          BannerRound Height
     * @param bannerRoundContainerPosition  this In what position, three optional, on the bottom of the default for the bottom
     * @param isBackgroundColor             Whether to display background shadows
     * @param roundContainerBackgroundColor Background shadow color
     */
    public void setBannerTip(float roundContainerWidth, float roundContainerHeight, BANNER_TIP_LAYOUT_POSITION bannerRoundContainerPosition, boolean isBackgroundColor, int roundContainerBackgroundColor) {
        LayoutParams roundContainerParams = new LayoutParams((int) roundContainerWidth, (int) roundContainerHeight);
        if (bannerRoundContainerPosition == null) {
            roundContainerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else {
            switch (bannerRoundContainerPosition) {
                case BOTTOM:
                    roundContainerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    break;
                case TOP:
                    roundContainerParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    break;
                case CENTERED:
                    roundContainerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    break;
            }
        }
        setLayoutParams(roundContainerParams);
        if (isBackgroundColor) {
            setBackgroundResource(roundContainerBackgroundColor);
        }
    }

    /**
     * Update the dot position
     */
    public void changeRoundPosition(int position, int newPosition) {
        if (linearLayout != null) {
            linearLayout.getChildAt(position).setEnabled(false);
            linearLayout.getChildAt(newPosition).setEnabled(true);
        }
    }

    /**
     * Update title, the default on the left
     */
    public void setTitle(int textColor, float textSize, int leftMargin, int rightMargin, float titleWidth, float titleHeight, BANNER_TITLE_POSITION bannerTitlePosition) {
        textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams params = new LayoutParams((int) titleWidth, (int) titleHeight);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.leftMargin = leftMargin;
        params.rightMargin = rightMargin;
        if (bannerTitlePosition == null) {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            switch (bannerTitlePosition) {
                case CENTERED:
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    break;
                case LEFT:
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    break;
                case RIGHT:
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    break;
            }
        }
        addView(textView, params);
    }

    public void setTitle(String title) {
        if (textView != null && title != null) {
            textView.setText(title);
        }
    }

    public void clearText() {
        if (textView != null) {
            textView.setText("");
        }
    }
}
