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

import com.bannerlayout.bannerenum.BannerDotsSite;
import com.bannerlayout.bannerenum.BannerTipsSite;
import com.bannerlayout.bannerenum.BannerTitleSite;

/**
 * by y on 2016/10/25
 */
class BannerTipsLayout extends RelativeLayout {

    private TextView textView;
    private LinearLayout linearLayout;

    public BannerTipsLayout(Context context) {
        super(context);
    }

    public BannerTipsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerTipsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Initialize the dots
     *
     * @param bannerDotsSite dot in what site, three optional, left in the right by default on the right
     */
    void setDots(
            BannerDotsSite bannerDotsSite,
            DotsInterface dotsInterface) {

        linearLayout = new LinearLayout(getContext());
        for (int i = 0; i < dotsInterface.dotsCount(); i++) {
            View view = new View(getContext());
            view.setBackgroundResource(dotsInterface.dotsSelector());
            if (i == 0) {
                view.setEnabled(true);
            } else {
                view.setEnabled(false);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotsInterface.dotsWidth(), dotsInterface.dotsHeight());
            view.setLayoutParams(params);
            params.leftMargin = dotsInterface.dotsLeftMargin();
            params.rightMargin = dotsInterface.dotsRightMargin();
            linearLayout.addView(view);
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        if (bannerDotsSite == null) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            switch (bannerDotsSite) {
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
     * @param bannerTipsSite this In what site, three optional, on the bottom of the default for the bottom
     */
    void setBannerTips(
            BannerTipsSite bannerTipsSite,
            TipsInterface tipsInterface) {
        LayoutParams tipsParams = new LayoutParams((int) tipsInterface.tipsWidth(), (int) tipsInterface.tipsHeight());
        if (bannerTipsSite == null) {
            tipsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else {
            switch (bannerTipsSite) {
                case BOTTOM:
                    tipsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    break;
                case TOP:
                    tipsParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    break;
                case CENTERED:
                    tipsParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    break;
            }
        }
        setLayoutParams(tipsParams);
        if (tipsInterface.isBackgroundColor()) {
            setBackgroundResource(tipsInterface.tipsLayoutBackgroundColor());
        }
    }

    /**
     * Update the dot position
     */
    void changeRoundPosition(int position, int newPosition) {
        if (linearLayout != null) {
            linearLayout.getChildAt(position).setEnabled(false);
            linearLayout.getChildAt(newPosition).setEnabled(true);
        }
    }


    /**
     * Update title, the default on the left
     */
    void setTitle(
            BannerTitleSite bannerTitleSite,
            TitleInterface titleInterface) {
        textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(titleInterface.titleColor());
        textView.setTextSize(titleInterface.titleSize());
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams params = new LayoutParams((int) titleInterface.titleWidth(), (int) titleInterface.titleHeight());
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.leftMargin = titleInterface.titleLeftMargin();
        params.rightMargin = titleInterface.titleRightMargin();
        if (bannerTitleSite == null) {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            switch (bannerTitleSite) {
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

    void setTitle(String title) {
        if (textView != null && title != null) {
            textView.setText(title);
        }
    }

    void clearText() {
        if (textView != null) {
            textView.setText("");
        }
    }

    interface TipsInterface {
        float tipsWidth();

        float tipsHeight();

        int tipsLayoutBackgroundColor();

        boolean isBackgroundColor();
    }

    interface TitleInterface {
        int titleColor();

        float titleSize();

        int titleLeftMargin();

        int titleRightMargin();

        float titleWidth();

        float titleHeight();
    }

    interface DotsInterface {
        int dotsCount();

        int dotsSelector();

        int dotsHeight();

        int dotsWidth();

        int dotsLeftMargin();

        int dotsRightMargin();
    }
}
