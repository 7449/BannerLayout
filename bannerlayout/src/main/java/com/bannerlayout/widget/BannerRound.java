package com.bannerlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * by y on 2016/10/25
 */
public class BannerRound extends RelativeLayout {

    private TextView textView;
    private LinearLayout linearLayout;

    public BannerRound(Context context) {
        super(context);
    }

    public BannerRound(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerRound(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化小圆点
     *
     * @param roundSize           小圆点个数
     * @param roundSelector       状态选择器
     * @param roundWidth          小圆点width
     * @param roundHeight         小圆点height
     * @param roundLeftMargin     小圆点的marginLeft
     * @param roundRightMargin    小圆点的marginRight
     * @param bannerRoundPosition 小圆点处于什么位置，三种可选，左 中 右 默认在右边
     */
    public void addRound(int roundSize, int roundSelector, int roundWidth, int roundHeight, int roundLeftMargin, int roundRightMargin, BANNER_ROUND_POSITION bannerRoundPosition) {
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
     * 初始化this
     *
     * @param roundContainerWidth           BannerRound高度
     * @param roundContainerHeight          BannerRound宽度
     * @param bannerRoundContainerPosition  this 处于什么位置，三种可选，上 中 下 默认为最下面
     * @param isBackgroundColor             是否显示背景阴影
     * @param roundContainerBackgroundColor 背景阴影颜色
     */
    public void settingBannerRound(int roundContainerWidth, int roundContainerHeight, BannerLayout.BANNER_ROUND_CONTAINER_POSITION bannerRoundContainerPosition, boolean isBackgroundColor, int roundContainerBackgroundColor) {
        LayoutParams roundContainerParams = new
                LayoutParams(roundContainerWidth, roundContainerHeight);
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
     * 更新小圆点位置
     */
    public void changeRoundPosition(int position, int newPosition) {
        if (linearLayout != null) {
            linearLayout.getChildAt(position).setEnabled(false);
            linearLayout.getChildAt(newPosition).setEnabled(true);
        }
    }

    /**
     * 更新title,默认在左边
     */
    public void addTitle(int textColor, float textSize, int leftMargin, int rightMargin, BANNER_TITLE_POSITION bannerTitlePosition) {
        textView = new TextView(getContext());
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    /**
     * 设置title
     */
    public void setTitle(String title) {
        if (textView != null) {
            textView.setText(title);
        }
    }

    /**
     * 清除title
     */
    public void clearText() {
        if (textView != null) {
            textView.setText("");
        }
    }

    /**
     * bannerRound位置
     */
    public enum BANNER_ROUND_POSITION {
        LEFT, CENTERED, RIGHT
    }

    /**
     * bannerTitle位置
     */
    public enum BANNER_TITLE_POSITION {
        LEFT, CENTERED, RIGHT
    }

}
