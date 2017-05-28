package com.bannerlayout.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bannerlayout.util.BannerSelectorUtils;

/**
 * by y on 2017/1/6
 */
class BannerPageView extends AppCompatTextView {
    public BannerPageView(Context context) {
        super(context);
    }

    public BannerPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    FrameLayout.LayoutParams initPageView(PageNumViewInterface pageNumViewInterface) {
        FrameLayout.LayoutParams pageParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        pageParams.setMargins(
                pageNumViewInterface.getPageNumViewLeftMargin(),
                pageNumViewInterface.getPageNumViewTopMargin(),
                pageNumViewInterface.getPageNumViewRightMargin(),
                pageNumViewInterface.getPageNumViewBottomMargin()
        );
        switch (pageNumViewInterface.pageNumViewSite()) {
            case BannerLayout.PAGE_NUM_VIEW_TOP_LEFT:
                break;
            case BannerLayout.PAGE_NUM_VIEW_TOP_RIGHT:
                pageParams.gravity = Gravity.RIGHT | Gravity.TOP;
                break;
            case BannerLayout.PAGE_NUM_VIEW_BOTTOM_LEFT:
                pageParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
                break;
            case BannerLayout.PAGE_NUM_VIEW_BOTTOM_RIGHT:
                pageParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                break;
            case BannerLayout.PAGE_NUM_VIEW_CENTER_LEFT:
                pageParams.gravity = Gravity.LEFT | Gravity.CENTER;
                break;
            case BannerLayout.PAGE_NUM_VIEW_CENTER_RIGHT:
                pageParams.gravity = Gravity.RIGHT | Gravity.CENTER;
                break;
            case BannerLayout.PAGE_NUM_VIEW_TOP_CENTER:
                pageParams.gravity = Gravity.TOP | Gravity.CENTER;
                break;
            case BannerLayout.PAGE_NUM_VIEW_BOTTOM_CENTER:
                pageParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
                break;
        }
        setTextColor(pageNumViewInterface.getPageNumViewTextColor());
        setTextSize(pageNumViewInterface.getPageNumViewTextSize());
        setPadding(pageNumViewInterface.getPageNumViewPaddingLeft(),
                pageNumViewInterface.getPageNumViewPaddingTop(),
                pageNumViewInterface.getPageNumViewPaddingRight(),
                pageNumViewInterface.getPageNumViewPaddingBottom());
        setBackgroundDrawable(BannerSelectorUtils.getShape(pageNumViewInterface.getPageNumViewRadius(),
                pageNumViewInterface.getPageNumViewBackgroundColor()));
        return pageParams;
    }

    interface PageNumViewInterface {

        int getPageNumViewTopMargin();

        int getPageNumViewRightMargin();

        int getPageNumViewBottomMargin();

        int getPageNumViewLeftMargin();

        int pageNumViewSite();

        int getPageNumViewTextColor();

        float getPageNumViewTextSize();

        int getPageNumViewPaddingTop();

        int getPageNumViewPaddingLeft();

        int getPageNumViewPaddingBottom();

        int getPageNumViewPaddingRight();

        float getPageNumViewRadius();

        int getPageNumViewBackgroundColor();

    }

}
