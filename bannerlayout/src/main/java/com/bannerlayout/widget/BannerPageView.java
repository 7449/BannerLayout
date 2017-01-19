package com.bannerlayout.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bannerlayout.util.BannerSelectorUtils;

/**
 * by y on 2017/1/6
 */

public class BannerPageView extends TextView {
    public BannerPageView(Context context) {
        super(context);
    }

    public BannerPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    RelativeLayout.LayoutParams initPageView(PageNumViewInterface pageNumViewInterface) {
        RelativeLayout.LayoutParams pageParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        pageParams.rightMargin = pageNumViewInterface.getPageNumViewRightMargin();
        pageParams.topMargin = pageNumViewInterface.getPageNumViewTopMargin();
        pageParams.leftMargin = pageNumViewInterface.getPageNumViewLeftMargin();
        pageParams.bottomMargin = pageNumViewInterface.getPageNumViewBottomMargin();
        switch (pageNumViewInterface.pageNumViewSite()) {
            case BannerLayout.PAGE_NUM_VIEW_SITE_TOP_LEFT:
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case BannerLayout.PAGE_NUM_VIEW_SITE_TOP_RIGHT:
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            case BannerLayout.PAGE_NUM_VIEW_SITE_BOTTOM_LEFT:
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case BannerLayout.PAGE_NUM_VIEW_SITE_BOTTOM_RIGHT:
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            case BannerLayout.PAGE_NUM_VIEW_SITE_CENTER_LEFT:
                pageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case BannerLayout.PAGE_NUM_VIEW_SITE_CENTER_RIGHT:
                pageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            case BannerLayout.PAGE_NUM_VIEW_SITE_TOP_CENTER:
                pageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case BannerLayout.PAGE_NUM_VIEW_SITE_BOTTOM_CENTER:
                pageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                pageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
        }
        setTextColor(ContextCompat.getColor(getContext(), pageNumViewInterface.getPageNumViewTextColor()));
        setTextSize(pageNumViewInterface.getPageNumViewTextSize());
        setPadding(pageNumViewInterface.getPageNumViewPaddingLeft(),
                pageNumViewInterface.getPageNumViewPaddingTop(),
                pageNumViewInterface.getPageNumViewPaddingRight(),
                pageNumViewInterface.getPageNumViewPaddingBottom());
        //noinspection deprecation
        setBackgroundDrawable(BannerSelectorUtils.getPageView(pageNumViewInterface.getPageNumViewRadius(),
                ContextCompat.getColor(getContext(), pageNumViewInterface.getPageNumViewBackgroundColor())));
        return pageParams;
    }

    public interface PageNumViewInterface {

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
