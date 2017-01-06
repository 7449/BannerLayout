package com.bannerlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.Interface.OnBannerPageChangeListener;
import com.bannerlayout.Interface.OnBannerTitleListener;
import com.bannerlayout.Interface.ViewPagerCurrent;
import com.bannerlayout.R;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.bannerenum.BannerAnimationType;
import com.bannerlayout.exception.BannerException;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.util.BannerHandlerUtils;
import com.bannerlayout.util.BannerSelectorUtils;
import com.bannerlayout.util.TransformerUtils;
import com.bannerlayout.widget.BannerTipsLayout.DotsInterface;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * by y on 2016/10/24
 */

public class BannerLayout extends RelativeLayout
        implements ViewPagerCurrent, ViewPager.OnPageChangeListener,
        BannerAdapter.OnBannerImageClickListener,
        DotsInterface,
        BannerTipsLayout.TitleInterface,
        BannerTipsLayout.TipsInterface,
        BannerPageView.PageNumViewInterface {

    /**
     * pageNumberView site type
     */
    public static final int PAGE_NUM_VIEW_SITE_TOP_LEFT = 0;
    public static final int PAGE_NUM_VIEW_SITE_TOP_RIGHT = 1;
    public static final int PAGE_NUM_VIEW_SITE_BOTTOM_LEFT = 2;
    public static final int PAGE_NUM_VIEW_SITE_BOTTOM_RIGHT = 3;
    public static final int PAGE_NUM_VIEW_SITE_CENTER_LEFT = 4;
    public static final int PAGE_NUM_VIEW_SITE_CENTER_RIGHT = 5;
    public static final int PAGE_NUM_VIEW_SITE_TOP_CENTER = 6;
    public static final int PAGE_NUM_VIEW_SITE_BOTTOM_CENTER = 7;
    /**
     * tipsLayout location marker
     */
    public static final int ALIGN_PARENT_LEFT = 9;
    public static final int ALIGN_PARENT_TOP = 10;
    public static final int ALIGN_PARENT_RIGHT = 11;
    public static final int ALIGN_PARENT_BOTTOM = 12;
    public static final int CENTER_IN_PARENT = 13;

    private List<BannerTransformer> transformerList = null; //Animation collection
    private OnBannerClickListener onBannerClickListener = null;
    private List<? extends BannerModel> imageList = null;
    private List<BannerModel> imageArrayList = null;
    private BannerViewPager viewPager = null;
    private BannerHandlerUtils bannerHandlerUtils = null;
    private BannerTipsLayout bannerTipLayout = null;
    private ImageLoaderManager imageLoaderManage = null; //Image Load Manager
    private View tipsView = null; //The custom hint bar must take over viewpager's OnPageChangeListener method
    private OnBannerPageChangeListener onBannerPageChangeListener = null;
    private OnBannerTitleListener onBannerTitleListener = null;
    private BannerAdapter adapter = null;//viewpager adapter
    private BannerPageView pageView = null; // viewpager page count textView

    private int preEnablePosition = 0;

    private int dotsWidth;// dots width
    private int dotsHeight;// dots height
    private boolean isStartRotation;//Whether auto rotation is enabled or not is not enabled by default
    private boolean isTipsBackground;//Whether to display a  dots background
    private boolean viePagerTouchMode; //Viewpager can manually slide, the default can
    private boolean isVisibleTitle;//Whether to display the title default is not displayed
    private boolean isVisibleDots;//Whether to display the dots default display
    private float titleSize;//font size
    private int titleColor;//font color
    private long delayTime; //Rotation time
    private int dotsLeftMargin; //The dots are marginLeft
    private int dotsRightMargin;//The dots are marginRight
    private int titleLeftMargin;//title marginLeft
    private int titleRightMargin;//title marginRight
    private float titleWidth;//title width
    private float titleHeight;// title height
    private float tipsLayoutHeight; //BannerTipsLayout height
    private float tipsLayoutWidth; // BannerTipsLayout width
    private int tipsBackgroundColor; //BannerTipsLayout BackgroundColor
    private int dotsSelector; //Dots State Selector
    private int errorImageView;//Glide Load error placeholder
    private int placeImageView;//Placeholder in glide loading
    private int mDuration;//Viewpager switching speed
    private boolean isVertical;//Whether the vertical sliding ,The default is not
    private float enabledRadius;
    private float normalRadius;
    private int enabledColor; //dots enabledColor
    private int normalColor;//dots normalColor
    private int tipsSite;
    private int dotsSite;
    private int titleSite;

    /**
     * pageNumView parameter
     */
    private float pageNumViewRadius;
    private int pageNumViewPaddingTop;
    private int pageNumViewPaddingLeft;
    private int pageNumViewPaddingBottom;
    private int pageNumViewPaddingRight;
    private int pageNumViewTopMargin;
    private int pageNumViewRightMargin;
    private int pageNumViewBottomMargin;
    private int pageNumViewLeftMargin;
    private int pageNumViewSite;
    private int pageNumViewTextColor;
    private int pageNumViewBackgroundColor;
    private int pageNumViewTextSize;

    @IntDef({ALIGN_PARENT_BOTTOM, ALIGN_PARENT_TOP, CENTER_IN_PARENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TipsSiteMode {
    }

    @IntDef({CENTER_IN_PARENT, ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DotsAndTitleSiteMode {
    }


    @IntDef({PAGE_NUM_VIEW_SITE_TOP_LEFT,
            PAGE_NUM_VIEW_SITE_TOP_RIGHT,
            PAGE_NUM_VIEW_SITE_BOTTOM_LEFT,
            PAGE_NUM_VIEW_SITE_BOTTOM_RIGHT,
            PAGE_NUM_VIEW_SITE_CENTER_LEFT,
            PAGE_NUM_VIEW_SITE_CENTER_RIGHT,
            PAGE_NUM_VIEW_SITE_TOP_CENTER,
            PAGE_NUM_VIEW_SITE_BOTTOM_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageNumViewSiteMode {
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BannerLayout);

        isTipsBackground = typedArray.getBoolean(R.styleable.BannerLayout_is_tips_background, BannerDefaults.IS_TIPS_LAYOUT_BACKGROUND);
        tipsBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_tips_background, ContextCompat.getColor(getContext(), BannerDefaults.TIPS_LAYOUT_BACKGROUND));
        tipsLayoutWidth = typedArray.getDimension(R.styleable.BannerLayout_tips_width, BannerDefaults.TIPS_LAYOUT_WIDTH);
        tipsLayoutHeight = typedArray.getDimension(R.styleable.BannerLayout_tips_height, BannerDefaults.TIPS_LAYOUT_HEIGHT);

        isVisibleDots = typedArray.getBoolean(R.styleable.BannerLayout_dots_visible, BannerDefaults.IS_VISIBLE_DOTS);
        dotsLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_dots_left_margin, BannerDefaults.DOTS_LEFT_MARGIN);
        dotsRightMargin = typedArray.getInteger(R.styleable.BannerLayout_dots_right_margin, BannerDefaults.DOTS_RIGHT_MARGIN);
        dotsWidth = typedArray.getInteger(R.styleable.BannerLayout_dots_width, BannerDefaults.DOTS_WIDth);
        dotsHeight = typedArray.getInteger(R.styleable.BannerLayout_dots_height, BannerDefaults.DOTS_HEIGHT);
        dotsSelector = typedArray.getResourceId(R.styleable.BannerLayout_dots_selector, BannerDefaults.DOTS_SELECTOR);
        enabledRadius = typedArray.getFloat(R.styleable.BannerLayout_enabledRadius, BannerDefaults.ENABLED_RADIUS);
        enabledColor = typedArray.getColor(R.styleable.BannerLayout_enabledColor, ContextCompat.getColor(getContext(), BannerDefaults.ENABLED_COLOR));
        normalRadius = typedArray.getFloat(R.styleable.BannerLayout_normalRadius, BannerDefaults.NORMAL_RADIUS);
        normalColor = typedArray.getColor(R.styleable.BannerLayout_normalColor, ContextCompat.getColor(getContext(), BannerDefaults.NORMAL_COLOR));

        delayTime = typedArray.getInteger(R.styleable.BannerLayout_delay_time, BannerDefaults.DELAY_TIME);
        isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_start_rotation, BannerDefaults.IS_START_ROTATION);
        viePagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_view_pager_touch_mode, BannerDefaults.VIEW_PAGER_TOUCH_MODE);
        errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_glide_error_image, BannerDefaults.GLIDE_ERROR_IMAGE);
        placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_glide_place_image, BannerDefaults.GLIDE_PIACE_IMAGE);
        mDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, BannerDefaults.BANNER_DURATION);
        isVertical = typedArray.getBoolean(R.styleable.BannerLayout_banner_isVertical, BannerDefaults.IS_VERTICAL);

        isVisibleTitle = typedArray.getBoolean(R.styleable.BannerLayout_title_visible, BannerDefaults.TITLE_VISIBLE);
        titleColor = typedArray.getColor(R.styleable.BannerLayout_title_color, BannerDefaults.TITLE_COLOR);
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_title_size, BannerDefaults.TITLE_SIZE);
        titleRightMargin = typedArray.getInteger(R.styleable.BannerLayout_title_right_margin, BannerDefaults.TITLE_RIGHT_MARGIN);
        titleLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_title_left_margin, BannerDefaults.TITLE_LEFT_MARGIN);
        titleWidth = typedArray.getDimension(R.styleable.BannerLayout_title_width, BannerDefaults.TITLE_WIDTH);
        titleHeight = typedArray.getDimension(R.styleable.BannerLayout_title_height, BannerDefaults.TITLE_HEIGHT);
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_title_size, BannerDefaults.TITLE_SIZE);

        tipsSite = typedArray.getInteger(R.styleable.BannerLayout_tips_site, ALIGN_PARENT_BOTTOM);
        dotsSite = typedArray.getInteger(R.styleable.BannerLayout_dots_site, ALIGN_PARENT_RIGHT);
        titleSite = typedArray.getInteger(R.styleable.BannerLayout_title_site, ALIGN_PARENT_LEFT);
        pageNumViewSite = typedArray.getInteger(R.styleable.BannerLayout_pageNumView_site, PAGE_NUM_VIEW_SITE_TOP_RIGHT);

        pageNumViewRadius = typedArray.getFloat(R.styleable.BannerLayout_page_num_view_radius, BannerDefaults.PAGE_NUM_VIEW_RADIUS);
        pageNumViewPaddingTop = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_paddingTop, BannerDefaults.PAGE_NUM_VIEW_PADDING_TOP);
        pageNumViewPaddingLeft = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_paddingLeft, BannerDefaults.PAGE_NUM_VIEW_PADDING_LEFT);
        pageNumViewPaddingBottom = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_paddingBottom, BannerDefaults.PAGE_NUM_VIEW_PADDING_BOTTOM);
        pageNumViewPaddingRight = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_paddingRight, BannerDefaults.PAGE_NUM_VIEW_PADDING_RIGHT);
        pageNumViewTopMargin = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_marginTop, BannerDefaults.PAGE_NUM_VIEW_TOP_MARGIN);
        pageNumViewRightMargin = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_marginRight, BannerDefaults.PAGE_NUM_VIEW_RIGHT_MARGIN);
        pageNumViewBottomMargin = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_marginBottom, BannerDefaults.PAGE_NUM_VIEW_BOTTOM_MARGIN);
        pageNumViewLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_marginLeft, BannerDefaults.PAGE_NUM_VIEW_LEFT_MARGIN);
        pageNumViewTextColor = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_textColor, BannerDefaults.PAGE_NUL_VIEW_TEXT_COLOR);
        pageNumViewBackgroundColor = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_BackgroundColor, BannerDefaults.PAGE_NUM_VIEW_BACKGROUND);
        pageNumViewTextSize = typedArray.getInteger(R.styleable.BannerLayout_page_num_view_textSize, BannerDefaults.PAGE_NUM_VIEW_SIZE);

        typedArray.recycle();

    }


    public BannerLayout(Context context) {
        super(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (viewPager != null) {
//            return viewPager.onTouchEvent(event);
//        }
//        return super.onTouchEvent(event);
//    }


    public BannerLayout initPageNumView() {
        if (pageView != null) {
            removeView(pageView);
            pageView = null;
        }
        pageView = new BannerPageView(getContext());
        pageView.setText(1 + " / " + getDotsSize());
        addView(pageView, pageView.initPageView(this));
        return this;
    }


    /**
     * Initialize the dots using the default parameters
     */
    public BannerLayout initTips() {
        initTips(isTipsBackground, isVisibleDots, isVisibleTitle);
        return this;
    }


    /**
     * Initialize the dots control, do not initialize this method if you select custom hint bar
     *
     * @param isBackgroundColor Whether to display the background color
     * @param isVisibleDots     Whether to display small dots, the default display
     * @param isVisibleTitle    Whether to display title, the default is not displayed
     */
    public BannerLayout initTips(boolean isBackgroundColor,
                                 boolean isVisibleDots,
                                 boolean isVisibleTitle) {

        if (tipsView != null) {
            throw new BannerException(getString(R.string.tipsView_no_null));
        }
        if (imageList == null) {
            throw new BannerException(getString(R.string.banner_adapterType_null));
        }
        this.isTipsBackground = isBackgroundColor;
        this.isVisibleDots = isVisibleDots;
        this.isVisibleTitle = isVisibleTitle;
        if (bannerTipLayout != null) {
            removeView(bannerTipLayout);
            bannerTipLayout = null;
        }
        bannerTipLayout = new BannerTipsLayout(getContext());
        bannerTipLayout.removeAllViews();
        if (isVisibleDots) {
            bannerTipLayout.setDots(this);
        }
        if (isVisibleTitle) {
            bannerTipLayout.setTitle(this);
            if (onBannerTitleListener != null) {
                bannerTipLayout.setTitle(onBannerTitleListener.getTitle(0));
            } else {
                bannerTipLayout.setTitle(imageList.get(0).getTitle());
            }
        }
        bannerTipLayout.setBannerTips(this);
        addView(bannerTipLayout);
        return this;
    }


    /**
     * Initializes a List image resource
     */
    public BannerLayout initListResources(List<? extends BannerModel> imageList) {
        if (imageList == null) {
            throw new BannerException(getString(R.string.list_null));
        }
        this.imageList = imageList;
        initAdapter();
        return this;
    }


    /**
     * Initializes an Array image resource
     */
    public BannerLayout initArrayResources(Object[] imageArray) {
        if (imageArray == null) {
            throw new BannerException(getString(R.string.array_null));
        }
        imageArrayList = new ArrayList<>();
        for (Object url : Arrays.asList(imageArray)) {
            imageArrayList.add(new BannerModel(url));
        }
        initListResources(imageArrayList);
        return this;
    }

    /**
     * Initializes an Array image resource
     */
    public BannerLayout initArrayResources(Object[] imageArray, String[] imageArrayTitle) {
        if (imageArray == null || imageArrayTitle == null) {
            throw new BannerException(getString(R.string.array_null_));
        }
        imageArrayList = new ArrayList<>();
        List<Object> url = Arrays.asList(imageArray);
        List<String> title = Arrays.asList(imageArrayTitle);
        if (url.size() != title.size()) {
            throw new BannerException(getString(R.string.array_size_lnconsistent));
        }
        BannerModel bannerModel;
        for (int i = 0; i < url.size(); i++) {
            bannerModel = new BannerModel();
            bannerModel.setImage(url.get(i));
            bannerModel.setTitle(title.get(i));
            imageArrayList.add(bannerModel);
        }
        initListResources(imageArrayList);
        return this;
    }

    /**
     * Initialize the rotation handler
     */
    public BannerLayout start(boolean isStartRotation) {
        start(isStartRotation, delayTime);
        return this;
    }

    /**
     * Initialize the rotation handler
     */
    public BannerLayout start(boolean isStartRotation, long delayTime) {
        bannerHandlerUtils = new BannerHandlerUtils(this, viewPager.getCurrentItem());
        bannerHandlerUtils.setDelayTime(delayTime);
        this.delayTime = delayTime;
        this.isStartRotation = isStartRotation;
        if (isStartRotation && getDotsSize() > 1) {
            startBanner();
        }
        return this;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isStartRotation && bannerHandlerUtils != null && getDotsSize() > 1) {
            restoreBanner();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isStartRotation && bannerHandlerUtils != null && getDotsSize() > 1) {
            stopBanner();
        }
    }


    /**
     * init adapter();
     * This method must be called after the init image resource
     */
    private void initAdapter() {
        if (viewPager != null) {
            removeView(viewPager);
            viewPager = null;
        }
        viewPager = new BannerViewPager(getContext());
        viewPager.setDuration(mDuration);
        viewPager.setVertical(isVertical);
        viewPager.setViewTouchMode(viePagerTouchMode);

        //Gallery ? See google play home page, a simple test

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        viewPager.setOffscreenPageLimit(3);
//        viewPager.setPageMargin(10);
//        setClipChildren(false);
//        viewPager.setClipChildren(false);
//        params.rightMargin = 60;
//        params.leftMargin = 60;
//        addView(viewPager, params);
        addView(viewPager);
        viewPager.addOnPageChangeListener(this);
        adapter = new BannerAdapter(imageList);
        adapter.setPlaceImage(placeImageView);
        adapter.setErrorImage(errorImageView);
        adapter.setImageLoaderManage(imageLoaderManage);
        adapter.setImageClickListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % getDotsSize()));
    }


    @Override
    public void setCurrentItem(int page) {
        if (viewPager != null) {
            viewPager.setCurrentItem(page);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onBannerPageChangeListener != null && tipsView != null) {
            onBannerPageChangeListener.onPageScrolled(position % getDotsSize(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % getDotsSize();
        if (pageView != null) {
            pageView.setText(newPosition + 1 + " / " + getDotsSize());
        }
        if (onBannerPageChangeListener != null && tipsView != null) {
            onBannerPageChangeListener.onPageSelected(newPosition);
        } else {
            if (bannerTipLayout != null) {
                if (isVisibleDots) {
                    bannerTipLayout.changeDotsPosition(preEnablePosition, newPosition);
                }
                if (isVisibleTitle) {
                    bannerTipLayout.clearText();
                    if (onBannerTitleListener != null) {
                        bannerTipLayout.setTitle(onBannerTitleListener.getTitle(newPosition));
                    } else {
                        bannerTipLayout.setTitle(imageList.get(newPosition).getTitle());
                    }
                }
            }
            preEnablePosition = newPosition;
            if (transformerList != null && transformerList.size() > 1 && !isVertical) {
                viewPager.setPageTransformer(true, transformerList.get((int) (Math.random() * transformerList.size())));
            }
            if (bannerHandlerUtils != null && isStartRotation) {
                bannerHandlerUtils.sendMessage(Message.obtain(bannerHandlerUtils, BannerHandlerUtils.MSG_PAGE, viewPager.getCurrentItem(), 0));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onBannerPageChangeListener != null && tipsView != null) {
            onBannerPageChangeListener.onPageScrollStateChanged(state);
        } else {
            if (bannerHandlerUtils != null && isStartRotation) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        bannerHandlerUtils.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime);
                        break;
                }
            }
        }
    }


    @Override
    public void onBannerClick(int position, Object model) {
        if (onBannerClickListener != null) {
            //noinspection unchecked
            onBannerClickListener.onBannerClick(position, model);
        }
    }

    public BannerLayout setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
        return this;
    }

    public BannerLayout addOnBannerPageChangeListener(OnBannerPageChangeListener onBannerPageChangeListener) {
        this.onBannerPageChangeListener = onBannerPageChangeListener;
        return this;
    }


    @Override
    public int dotsCount() {
        return getDotsSize();
    }

    @Override
    public Drawable dotsSelector() {
        if (dotsSelector == -1) {
            return BannerSelectorUtils.getDrawableSelector(enabledRadius, enabledColor, normalRadius, normalColor);
        } else {
            return ContextCompat.getDrawable(getContext(), dotsSelector);
        }
    }

    @Override
    public int dotsHeight() {
        return dotsHeight;
    }

    @Override
    public int dotsWidth() {
        return dotsWidth;
    }

    @Override
    public int dotsLeftMargin() {
        return dotsLeftMargin;
    }

    @Override
    public int dotsRightMargin() {
        return dotsRightMargin;
    }

    @Override
    public int dotsSite() {
        return dotsSite;
    }

    @Override
    public int titleColor() {
        return titleColor;
    }

    @Override
    public float titleSize() {
        return titleSize;
    }

    @Override
    public int titleLeftMargin() {
        return titleLeftMargin;
    }

    @Override
    public int titleRightMargin() {
        return titleRightMargin;
    }

    @Override
    public float titleWidth() {
        return titleWidth;
    }

    @Override
    public float titleHeight() {
        return titleHeight;
    }

    @Override
    public int titleSite() {
        return titleSite;
    }

    @Override
    public int tipsSite() {
        return tipsSite;
    }

    @Override
    public float tipsWidth() {
        return tipsLayoutWidth;
    }

    @Override
    public float tipsHeight() {
        return tipsLayoutHeight;
    }

    @Override
    public int tipsLayoutBackgroundColor() {
        return tipsBackgroundColor;
    }

    @Override
    public boolean isBackgroundColor() {
        return isTipsBackground;
    }

    @Override
    public int getPageNumViewTopMargin() {
        return pageNumViewTopMargin;
    }

    @Override
    public int getPageNumViewRightMargin() {
        return pageNumViewRightMargin;
    }

    @Override
    public int getPageNumViewBottomMargin() {
        return pageNumViewBottomMargin;
    }

    @Override
    public int getPageNumViewLeftMargin() {
        return pageNumViewLeftMargin;
    }

    @Override
    public int pageNumViewSite() {
        return pageNumViewSite;
    }

    @Override
    public int getPageNumViewTextColor() {
        return pageNumViewTextColor;
    }

    @Override
    public float getPageNumViewTextSize() {
        return pageNumViewTextSize;
    }

    @Override
    public int getPageNumViewPaddingTop() {
        return pageNumViewPaddingTop;
    }

    @Override
    public int getPageNumViewPaddingLeft() {
        return pageNumViewPaddingLeft;
    }

    @Override
    public int getPageNumViewPaddingBottom() {
        return pageNumViewPaddingBottom;
    }

    @Override
    public int getPageNumViewPaddingRight() {
        return pageNumViewPaddingRight;
    }

    @Override
    public float getPageNumViewRadius() {
        return pageNumViewRadius;
    }


    @Override
    public int getPageNumViewBackgroundColor() {
        return pageNumViewBackgroundColor;
    }

    /**
     * Set the title color, font size, and unneeded setting of -1
     */
    public BannerLayout setTitleSetting(int titleColor, int titleSize) {
        if (titleSize != -1) {
            this.titleSize = titleSize;
        }
        if (titleColor != -1) {
            this.titleColor = titleColor;
        }
        return this;
    }


    /**
     * Sets the ViewPager switching speed
     */
    public BannerLayout setDuration(int pace) {
        this.mDuration = pace;
        if (viewPager != null) {
            viewPager.setDuration(pace);
        }
        return this;
    }

    /**
     * Set whether the viewpager can be swiped, true to prevent sliding
     */
    public BannerLayout setViewPagerTouchMode(boolean b) {
        this.viePagerTouchMode = b;
        if (viewPager != null) {
            viewPager.setViewTouchMode(b);
        }
        return this;
    }

    /**
     * Whether the vertical sliding ,The default is not
     */
    public BannerLayout setVertical(boolean vertical) {
        this.isVertical = vertical;
        if (viewPager != null) {
            viewPager.setVertical(vertical);
        }
        return this;
    }

    /**
     * Set the Load Picture Manager
     */
    public BannerLayout setImageLoaderManage(ImageLoaderManager loaderManage) {
        this.imageLoaderManage = loaderManage;
        if (adapter != null) {
            adapter.setImageLoaderManage(loaderManage);
        }
        return this;
    }

    /**
     * Glide Loads an error image, called before initAdapter
     */
    public BannerLayout setErrorImageView(int errorImageView) {
        this.errorImageView = errorImageView;
        if (adapter != null) {
            adapter.setErrorImage(errorImageView);
        }
        return this;
    }

    /**
     * Glide loads the image before the initAdapter is called
     */
    public BannerLayout setPlaceImageView(int placeImageView) {
        this.placeImageView = placeImageView;
        if (adapter != null) {
            adapter.setPlaceImage(placeImageView);
        }
        return this;
    }

    /**
     * Sets the system to switch animation
     */
    public BannerLayout setBannerTransformer(BannerAnimationType bannerAnimation) {
        if (viewPager == null || bannerAnimation == null) {
            throw new BannerException(getString(R.string.viewpager_or_transformer_null));
        }
        if (isVertical) {
            throw new BannerException(getString(R.string.vertical));
        }
        viewPager.setPageTransformer(true, TransformerUtils.getTransformer(bannerAnimation));
        return this;
    }

    /**
     * When the custom Bean class to get back to the title of the callback
     */
    public BannerLayout addOnBannerTitleListener(OnBannerTitleListener onBannerTitleListener) {
        this.onBannerTitleListener = onBannerTitleListener;
        return this;
    }

    /**
     * Sets a custom toggle animation
     */
    public BannerLayout setBannerTransformer(BannerTransformer bannerTransformer) {
        if (viewPager == null || bannerTransformer == null) {
            throw new BannerException(getString(R.string.viewpager_or_transformer_null));
        }
        if (isVertical) {
            throw new BannerException(getString(R.string.vertical));
        }
        viewPager.setPageTransformer(true, bannerTransformer);
        return this;
    }

    /**
     * Customize the animation collection
     */
    public BannerLayout setBannerTransformerList(List<BannerTransformer> list) {
        if (list == null) {
            throw new BannerException(getString(R.string.bannerTransformer_null));
        }
        this.transformerList = list;
        return this;
    }

    /**
     * A collection of system animations
     */
    public BannerLayout setBannerSystemTransformerList(List<BannerAnimationType> list) {
        if (list == null) {
            throw new BannerException(getString(R.string.animationList_null));
        }
        transformerList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            transformerList.add(TransformerUtils.getTransformer(list.get(i)));
        }
        return this;
    }


    /**
     * Initialize the custom hint column before calling initAdapter
     */
    public BannerLayout setTipsView(View view) {
        if (tipsView != null) {
            removeView(tipsView);
            tipsView = null;
        }
        this.tipsView = view;
        addView(tipsView);
        return this;
    }

    /**
     * setting BannerTipsLayout background
     * The call takes effect before the initTips () method
     */
    public BannerLayout setTipsBackgroundColor(int colorId) {
        this.tipsBackgroundColor = colorId;
        return this;
    }


    /**
     * setting BannerTipsLayoutHeight
     */
    public BannerLayout setTipsHeight(int height) {
        this.tipsLayoutHeight = height;
        return this;
    }

    /**
     * setting BannerTipsLayoutWidth
     */
    public BannerLayout setTipsWidth(int width) {
        this.tipsLayoutWidth = width;
        return this;
    }

    /**
     * Sets the status selector for small dots
     * The call takes effect before the initTips () method
     */
    public BannerLayout initTipsDotsSelector(int dotsSelector) {
        this.dotsSelector = dotsSelector;
        return this;
    }


    /**
     * Set the dots marginLeft and marginRight, the default is 10
     */
    public BannerLayout setDotsMargin(int leftMargin, int rightMargin) {
        this.dotsLeftMargin = leftMargin;
        this.dotsRightMargin = rightMargin;
        return this;
    }


    /**
     * Set the title marginLeft and marginRight, the default is 10
     */
    public BannerLayout setTitleMargin(int leftMargin, int rightMargin) {
        this.titleLeftMargin = leftMargin;
        this.titleRightMargin = rightMargin;
        return this;
    }

    /**
     * Set the dots width, the default is 15
     */
    public BannerLayout setDotsWidth(int width) {
        this.dotsWidth = width;
        return this;
    }

    /**
     * Set the height of the small dot, the default is 15
     */
    public BannerLayout setDotsHeight(int height) {
        this.dotsHeight = height;
        return this;
    }

    /**
     * Sets whether to display title, which is called before initTips () is initialized
     */
    public BannerLayout setVisibleTitle(boolean isVisibleTitle) {
        this.isVisibleTitle = isVisibleTitle;
        return this;
    }

    /**
     * Sets whether or not to display small dots, called before initTips () is initialized
     */
    public BannerLayout setVisibleDots(boolean isVisibleDots) {
        this.isVisibleDots = isVisibleDots;
        return this;
    }

    public BannerLayout setNormalRadius(float normalRadius) {
        this.normalRadius = normalRadius;
        return this;
    }

    public BannerLayout setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public BannerLayout setEnabledColor(int enabledColor) {
        this.enabledColor = enabledColor;
        return this;
    }

    public BannerLayout setEnabledRadius(float enabledRadius) {
        this.enabledRadius = enabledRadius;
        return this;
    }

    public BannerLayout setPageNumViewRadius(float pageNumViewRadius) {
        this.pageNumViewRadius = pageNumViewRadius;
        return this;
    }

    public BannerLayout setPageNumViewPadding(int top, int bottom, int left, int right) {
        this.pageNumViewPaddingTop = top;
        this.pageNumViewPaddingBottom = bottom;
        this.pageNumViewPaddingLeft = left;
        this.pageNumViewPaddingRight = right;
        return this;
    }

    public BannerLayout setPageNumViewMargin(int top, int bottom, int left, int right) {
        this.pageNumViewTopMargin = top;
        this.pageNumViewBottomMargin = bottom;
        this.pageNumViewLeftMargin = left;
        this.pageNumViewRightMargin = right;
        return this;
    }

    public BannerLayout setPageNumViewSite(@PageNumViewSiteMode int pageNumViewSite) {
        this.pageNumViewSite = pageNumViewSite;
        return this;
    }

    public BannerLayout setPageNumViewTextColor(int pageNumViewTextColor) {
        this.pageNumViewTextColor = pageNumViewTextColor;
        return this;
    }

    public BannerLayout setPageNumViewBackgroundColor(int pageNumViewBackgroundColor) {
        this.pageNumViewBackgroundColor = pageNumViewBackgroundColor;
        return this;
    }

    public BannerLayout setPageNumViewTextSize(int pageNumViewTextSize) {
        this.pageNumViewTextSize = pageNumViewTextSize;
        return this;
    }

    /**
     * Set the position of the tips in the layout
     */
    public BannerLayout setTipsSite(@TipsSiteMode int tipsSite) {
        this.tipsSite = tipsSite;
        return this;
    }

    /**
     * Set the position of the dots in the layout
     */
    public BannerLayout setDotsSite(@DotsAndTitleSiteMode int dotsSite) {
        this.dotsSite = dotsSite;
        return this;
    }

    /**
     * Set the position of the title in the layout
     */
    public BannerLayout setTitleSite(@DotsAndTitleSiteMode int titleSite) {
        this.titleSite = titleSite;
        return this;
    }

    /**
     * Start rotation
     */
    @SuppressWarnings("WeakerAccess")
    public void startBanner() {
        if (bannerHandlerUtils != null) {
            bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_START);
        }
    }

    /**
     * Paused rotation
     */
    @SuppressWarnings("WeakerAccess")
    public void stopBanner() {
        if (bannerHandlerUtils != null) {
            bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP);
        }
    }

    /**
     * Resume rotation
     */
    @SuppressWarnings("WeakerAccess")
    public void restoreBanner() {
        if (bannerHandlerUtils != null) {
            bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_BREAK);
        }
    }

    public int getDuration() {
        if (viewPager == null) {
            throw new BannerException(getString(R.string.viewpager_null));
        }
        return viewPager.getDuration();
    }

    /**
     * getViewPager
     */
    public BannerViewPager getViewPager() {
        return viewPager;
    }

    /**
     * Get the number of dots, where you can also get the number of pictures
     */
    private int getDotsSize() {
        if (imageList != null && imageList.size() > 0) {
            return imageList.size();
        }
        throw new BannerException(getString(R.string.list_null));
    }

    private String getString(int id) {
        return getContext().getString(id);
    }
}
