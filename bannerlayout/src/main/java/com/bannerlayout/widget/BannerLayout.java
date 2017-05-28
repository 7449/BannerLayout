package com.bannerlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.bannerlayout.Interface.BannerModelCallBack;
import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannerlayout.Interface.OnBannerChangeListener;
import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.Interface.ViewPagerCurrent;
import com.bannerlayout.R;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.annotation.AnimationMode;
import com.bannerlayout.annotation.DotsAndTitleSiteMode;
import com.bannerlayout.annotation.PageNumViewSiteMode;
import com.bannerlayout.annotation.TipsSiteMode;
import com.bannerlayout.util.BannerHandlerUtils;
import com.bannerlayout.util.BannerSelectorUtils;
import com.bannerlayout.util.TransformerUtils;
import com.bannerlayout.widget.BannerTipsLayout.DotsInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/10/24
 */

@SuppressWarnings("ALL")
public class BannerLayout extends FrameLayout
        implements ViewPagerCurrent, ViewPager.OnPageChangeListener,
        BannerAdapter.OnBannerImageClickListener,
        DotsInterface,
        BannerTipsLayout.TitleInterface,
        BannerTipsLayout.TipsInterface,
        BannerPageView.PageNumViewInterface {

    public static final int MATCH_PARENT = FrameLayout.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = FrameLayout.LayoutParams.WRAP_CONTENT;

    /**
     * animation type
     */
    public static final int ANIMATION_ACCORDION = 0;
    public static final int ANIMATION_BACKGROUND = 1;
    public static final int ANIMATION_CUBE_IN = 2;
    public static final int ANIMATION_CUBE_OUT = 3;
    public static final int ANIMATION_DEFAULT = 4;
    public static final int ANIMATION_DEPTH_PAGE = 5;
    public static final int ANIMATION_FLIPHORIZONTAL = 6;
    public static final int ANIMATION_FLIPVERTICAL = 7;
    public static final int ANIMATION_FOREGROUND = 8;
    public static final int ANIMATION_ROTATEDOWN = 9;
    public static final int ANIMATION_ROTATEUP = 10;
    public static final int ANIMATION_STACK = 11;
    public static final int ANIMATION_SCALEINOUT = 12;
    public static final int ANIMATION_TABLET = 13;
    public static final int ANIMATION_ZOOMIN = 14;
    public static final int ANIMATION_ZOOMOUTPAGE = 15;
    public static final int ANIMATION_ZOOMOUTSLIDE = 16;
    public static final int ANIMATION_ZOOMOUT = 17;

    /**
     * pageNumberView site type
     */
    public static final int PAGE_NUM_VIEW_TOP_LEFT = 0;
    public static final int PAGE_NUM_VIEW_TOP_RIGHT = 1;
    public static final int PAGE_NUM_VIEW_BOTTOM_LEFT = 2;
    public static final int PAGE_NUM_VIEW_BOTTOM_RIGHT = 3;
    public static final int PAGE_NUM_VIEW_CENTER_LEFT = 4;
    public static final int PAGE_NUM_VIEW_CENTER_RIGHT = 5;
    public static final int PAGE_NUM_VIEW_TOP_CENTER = 6;
    public static final int PAGE_NUM_VIEW_BOTTOM_CENTER = 7;
    /**
     * tipsLayout location marker
     */
    public static final int LEFT = 9;
    public static final int TOP = 10;
    public static final int RIGHT = 11;
    public static final int BOTTOM = 12;
    public static final int CENTER = 13;

    private List<BannerTransformer> transformerList = null; //Animation collection
    private OnBannerClickListener onBannerClickListener = null;
    private List<? extends BannerModelCallBack> imageList = null;
    private BannerViewPager viewPager = null;
    private BannerHandlerUtils bannerHandlerUtils = null;
    private BannerTipsLayout bannerTipLayout = null;
    private ImageLoaderManager imageLoaderManage = null; //Image Load Manager
    private BannerAdapter adapter = null;//viewpager adapter
    private BannerPageView pageView = null; // viewpager page count textView
    private OnBannerChangeListener onBannerChangeListener = null;
    private BannerTransformer bannerTransformer = null;

    private int preEnablePosition = 0;

    private boolean isStartRotation;//Whether auto rotation is enabled or not is not enabled by default
    private boolean isTipsBackground;//Whether to display a  dots background
    private boolean viePagerTouchMode; //Viewpager can manually slide, the default can
    private int errorImageView;//Glide Load error placeholder
    private int placeImageView;//Placeholder in glide loading
    private int mDuration;//Viewpager switching speed
    private boolean isVertical;//Whether the vertical sliding ,The default is not

    private boolean isVisibleDots;//Whether to display the dots default display
    private int dotsWidth;// dots width
    private int dotsHeight;// dots height
    private int dotsSelector; //Dots State Selector
    private long delayTime; //Rotation time
    private int dotsLeftMargin; //The dots are marginLeft
    private int dotsRightMargin;//The dots are marginRight
    private int dotsSite;
    private float enabledRadius;
    private float normalRadius;
    private int enabledColor; //dots enabledColor
    private int normalColor;//dots normalColor

    private boolean isVisibleTitle;//Whether to display the title default is not displayed
    private float titleSize;//font size
    private int titleColor;//font color
    private int titleLeftMargin;//title marginLeft
    private int titleRightMargin;//title marginRight
    private int titleWidth;//title width
    private int titleHeight;// title height
    private int titleSite;

    private int tipsLayoutHeight; //BannerTipsLayout height
    private int tipsLayoutWidth; // BannerTipsLayout width
    private int tipsBackgroundColor; //BannerTipsLayout BackgroundColor
    private int tipsSite;

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
    private float pageNumViewTextSize;
    private String pageNumViewMark;


    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BannerLayout);

        isTipsBackground = typedArray.getBoolean(R.styleable.BannerLayout_banner_is_tips_background, BannerDefaults.IS_TIPS_LAYOUT_BACKGROUND);
        tipsBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_tips_background, ContextCompat.getColor(getContext(), BannerDefaults.TIPS_LAYOUT_BACKGROUND));
        tipsLayoutWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_width, BannerDefaults.TIPS_LAYOUT_WIDTH);
        tipsLayoutHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_height, BannerDefaults.TIPS_LAYOUT_HEIGHT);

        isVisibleDots = typedArray.getBoolean(R.styleable.BannerLayout_banner_dots_visible, BannerDefaults.IS_VISIBLE_DOTS);
        dotsLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_left_margin, BannerDefaults.DOTS_LEFT_MARGIN);
        dotsRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_right_margin, BannerDefaults.DOTS_RIGHT_MARGIN);
        dotsWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_width, BannerDefaults.DOTS_WIDth);
        dotsHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_height, BannerDefaults.DOTS_HEIGHT);
        dotsSelector = typedArray.getResourceId(R.styleable.BannerLayout_banner_dots_selector, BannerDefaults.DOTS_SELECTOR);
        enabledRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_enabledRadius, BannerDefaults.ENABLED_RADIUS);
        enabledColor = typedArray.getColor(R.styleable.BannerLayout_banner_enabledColor, ContextCompat.getColor(getContext(), BannerDefaults.ENABLED_COLOR));
        normalRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_normalRadius, BannerDefaults.NORMAL_RADIUS);
        normalColor = typedArray.getColor(R.styleable.BannerLayout_banner_normalColor, ContextCompat.getColor(getContext(), BannerDefaults.NORMAL_COLOR));

        delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, BannerDefaults.DELAY_TIME);
        isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_banner_start_rotation, BannerDefaults.IS_START_ROTATION);
        viePagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, BannerDefaults.VIEW_PAGER_TOUCH_MODE);
        errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_error_image, BannerDefaults.GLIDE_ERROR_IMAGE);
        placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_place_image, BannerDefaults.GLIDE_PIACE_IMAGE);
        mDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, BannerDefaults.BANNER_DURATION);
        isVertical = typedArray.getBoolean(R.styleable.BannerLayout_banner_isVertical, BannerDefaults.IS_VERTICAL);

        isVisibleTitle = typedArray.getBoolean(R.styleable.BannerLayout_banner_title_visible, BannerDefaults.TITLE_VISIBLE);
        titleColor = typedArray.getColor(R.styleable.BannerLayout_banner_title_color, ContextCompat.getColor(getContext(), BannerDefaults.TITLE_COLOR));
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_banner_title_size, BannerDefaults.TITLE_SIZE);
        titleRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_right_margin, BannerDefaults.TITLE_RIGHT_MARGIN);
        titleLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_left_margin, BannerDefaults.TITLE_LEFT_MARGIN);
        titleWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_title_width, BannerDefaults.TITLE_WIDTH);
        titleHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_title_height, BannerDefaults.TITLE_HEIGHT);


        tipsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_site, BOTTOM);
        dotsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_site, RIGHT);
        titleSite = typedArray.getInteger(R.styleable.BannerLayout_banner_title_site, LEFT);
        pageNumViewSite = typedArray.getInteger(R.styleable.BannerLayout_banner_pageNum_site, PAGE_NUM_VIEW_TOP_RIGHT);

        pageNumViewRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_page_num_radius, BannerDefaults.PAGE_NUM_VIEW_RADIUS);
        pageNumViewPaddingTop = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingTop, BannerDefaults.PAGE_NUM_VIEW_PADDING_TOP);
        pageNumViewPaddingLeft = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingLeft, BannerDefaults.PAGE_NUM_VIEW_PADDING_LEFT);
        pageNumViewPaddingBottom = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingBottom, BannerDefaults.PAGE_NUM_VIEW_PADDING_BOTTOM);
        pageNumViewPaddingRight = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingRight, BannerDefaults.PAGE_NUM_VIEW_PADDING_RIGHT);
        pageNumViewTopMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginTop, BannerDefaults.PAGE_NUM_VIEW_TOP_MARGIN);
        pageNumViewRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginRight, BannerDefaults.PAGE_NUM_VIEW_RIGHT_MARGIN);
        pageNumViewBottomMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginBottom, BannerDefaults.PAGE_NUM_VIEW_BOTTOM_MARGIN);
        pageNumViewLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginLeft, BannerDefaults.PAGE_NUM_VIEW_LEFT_MARGIN);
        pageNumViewTextColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_textColor, ContextCompat.getColor(getContext(), BannerDefaults.PAGE_NUL_VIEW_TEXT_COLOR));
        pageNumViewBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_BackgroundColor, ContextCompat.getColor(getContext(), BannerDefaults.PAGE_NUM_VIEW_BACKGROUND));
        pageNumViewTextSize = typedArray.getDimension(R.styleable.BannerLayout_banner_page_num_textSize, BannerDefaults.PAGE_NUM_VIEW_SIZE);
        pageNumViewMark = typedArray.getString(R.styleable.BannerLayout_banner_page_num_mark);
        if (isNull(pageNumViewMark)) {
            pageNumViewMark = BannerDefaults.PAGE_NUM_VIEW_MARK;
        }
        typedArray.recycle();
    }

    public BannerLayout(Context context) {
        super(context);
        init(null);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void setCurrentItem(int page) {
        viewPager.setCurrentItem(page);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!isNull(onBannerChangeListener)) {
            onBannerChangeListener.onPageScrolled(position % getDotsSize(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % getDotsSize();
        if (!isNull(pageView)) {
            pageView.setText(TextUtils.concat(String.valueOf(newPosition + 1), pageNumViewMark, String.valueOf(getDotsSize())));
        }
        if (!isNull(bannerTipLayout)) {
            if (isVisibleDots) {
                bannerTipLayout.changeDotsPosition(preEnablePosition, newPosition);
            }
            if (isVisibleTitle) {
                bannerTipLayout.setTitle(imageList.get(newPosition).getBannerTitle());
            }
        }
        preEnablePosition = newPosition;
        if (!isNull(transformerList) && transformerList.size() > 1 && !isVertical) {
            viewPager.setPageTransformer(true, transformerList.get((int) (Math.random() * transformerList.size())));
        }
        if (!isNull(bannerHandlerUtils)) {
            bannerHandlerUtils.sendMessage(Message.obtain(bannerHandlerUtils, BannerHandlerUtils.MSG_PAGE, viewPager.getCurrentItem(), 0));
        }
        if (!isNull(onBannerChangeListener)) {
            onBannerChangeListener.onPageSelected(newPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (!isNull(bannerHandlerUtils) && isStartRotation) {
            bannerHandlerUtils.removeCallbacksAndMessages(null);
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP);
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    bannerHandlerUtils.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime);
                    break;
            }
        }
        if (!isNull(onBannerChangeListener)) {
            onBannerChangeListener.onPageScrollStateChanged(state);
        }
    }


    @Override
    public void onBannerClick(View view, int position, Object model) {
        if (!isNull(onBannerClickListener)) {
            //noinspection unchecked
            onBannerClickListener.onBannerClick(view, position, model);
        }
    }


    /************************************************************************************************************
     * <p>
     * <p>                          BannerLayout method start
     * <p>
     *************************************************************************************************************/

    public BannerLayout addOnPageChangeListener(@NonNull OnBannerChangeListener onBannerChangeListener) {
        this.onBannerChangeListener = onBannerChangeListener;
        return this;
    }

    public BannerLayout initPageNumView() {
        clearPageView();
        pageView = new BannerPageView(getContext());
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
    public BannerLayout initTips(@BoolRes boolean isBackgroundColor,
                                 @BoolRes boolean isVisibleDots,
                                 @BoolRes boolean isVisibleTitle) {
        this.isTipsBackground = isBackgroundColor;
        this.isVisibleDots = isVisibleDots;
        this.isVisibleTitle = isVisibleTitle;
        clearBannerTipLayout();
        bannerTipLayout = new BannerTipsLayout(getContext());
        return this;
    }


    /**
     * Initializes a List image resource
     */
    public BannerLayout initListResources(@NonNull List<? extends BannerModelCallBack> imageList) {
        if (isNull(imageList)) {
            error(R.string.list_null);
        }
        this.imageList = imageList;
        initBannerMethod();
        return this;
    }


    public BannerLayout setDelayTime(int time) {
        this.delayTime = time;
        return this;
    }

    public BannerLayout switchBanner(@BoolRes boolean isStartRotation) {
        this.isStartRotation = isStartRotation;
        bannerHandlerUtils.removeCallbacksAndMessages(null);
        if (isStartRotation) {
            bannerHandlerUtils.setDelayTime(delayTime);
            bannerHandlerUtils.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime);
        } else {
            bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP);
            bannerHandlerUtils.removeCallbacksAndMessages(null);
        }
        return this;
    }

    public BannerLayout setErrorImageView(@DrawableRes int errorImageView) {
        this.errorImageView = errorImageView;
        return this;
    }


    public BannerLayout setPlaceImageView(@DrawableRes int placeImageView) {
        this.placeImageView = placeImageView;
        return this;
    }

    /**
     * sets the ViewPager switching speed
     */
    public BannerLayout setDuration(int pace) {
        this.mDuration = pace;
        return this;
    }

    /**
     * sets whether the viewpager can be swiped, true to prevent sliding
     */
    public BannerLayout setViewPagerTouchMode(@BoolRes boolean b) {
        this.viePagerTouchMode = b;
        return this;
    }

    /**
     * Whether the vertical sliding ,The default is not
     */
    public BannerLayout setVertical(@BoolRes boolean vertical) {
        this.isVertical = vertical;
        return this;
    }


    public BannerLayout setTipsBackgroundColor(@ColorInt int colorId) {
        this.tipsBackgroundColor = colorId;
        return this;
    }


    public BannerLayout setTipsDotsSelector(@DrawableRes int dotsSelector) {
        this.dotsSelector = dotsSelector;
        return this;
    }

    public BannerLayout setTipsWidthAndHeight(int width,
                                              int height) {
        this.tipsLayoutHeight = height;
        this.tipsLayoutWidth = width;
        return this;
    }


    public BannerLayout setTipsSite(@TipsSiteMode int tipsSite) {
        this.tipsSite = tipsSite;
        return this;
    }

    public BannerLayout setTitleTextColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public BannerLayout setTitleTextSize(float titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public BannerLayout setTitleMargin(int leftMargin,
                                       int rightMargin) {
        this.titleLeftMargin = leftMargin;
        this.titleRightMargin = rightMargin;
        return this;
    }

    public BannerLayout setTitleMargin(int margin) {
        this.titleLeftMargin = margin;
        this.titleRightMargin = margin;
        return this;
    }


    public BannerLayout setTitleSite(@DotsAndTitleSiteMode int titleSite) {
        this.titleSite = titleSite;
        return this;
    }

    public BannerLayout setDotsWidthAndHeight(int width,
                                              int height) {
        this.dotsWidth = width;
        this.dotsHeight = height;
        return this;
    }

    public BannerLayout setDotsSite(@DotsAndTitleSiteMode int dotsSite) {
        this.dotsSite = dotsSite;
        return this;
    }

    public BannerLayout setDotsMargin(int leftMargin,
                                      int rightMargin) {
        this.dotsLeftMargin = leftMargin;
        this.dotsRightMargin = rightMargin;
        return this;
    }

    public BannerLayout setDotsMargin(int margin) {
        this.dotsLeftMargin = margin;
        this.dotsRightMargin = margin;
        return this;
    }

    public BannerLayout setNormalRadius(float normalRadius) {
        this.normalRadius = normalRadius;
        return this;
    }

    public BannerLayout setNormalColor(@ColorInt int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public BannerLayout setEnabledColor(@ColorInt int enabledColor) {
        this.enabledColor = enabledColor;
        return this;
    }

    public BannerLayout setEnabledRadius(float enabledRadius) {
        this.enabledRadius = enabledRadius;
        return this;
    }

    /************************************************************************************************************
     * <p>
     * <p>                          viewPager  Transformer    start
     * <p>
     *************************************************************************************************************/

    public BannerLayout setBannerTransformer(@AnimationMode int type) {
        setBannerTransformer(TransformerUtils.getTransformer(type));
        return this;
    }


    public BannerLayout setBannerTransformer(@NonNull BannerTransformer bannerTransformer) {
        if (isVertical) {
            error(R.string.vertical);
        }
        this.bannerTransformer = bannerTransformer;
        if (!isNull(viewPager)) {
            viewPager.setPageTransformer(true, bannerTransformer);
        }
        return this;
    }

    public BannerLayout setBannerSystemTransformerList(@NonNull List<Integer> list) {
        List<BannerTransformer> bannerTransformers = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            bannerTransformers.add(TransformerUtils.getTransformer(list.get(i)));
        }
        setBannerTransformerList(bannerTransformers);
        return this;
    }

    public BannerLayout setBannerTransformerList(@NonNull List<BannerTransformer> list) {
        if (isNull(list)) {
            error(R.string.bannerTransformer_null);
        }
        this.transformerList = list;
        return this;
    }


    /************************************************************************************************************
     * <p>
     * <p>                          BannerPageNumView  setting    start
     * <p>
     *************************************************************************************************************/

    public BannerLayout setPageNumViewRadius(float pageNumViewRadius) {
        this.pageNumViewRadius = pageNumViewRadius;
        return this;
    }

    public BannerLayout setPageNumViewPadding(int top,
                                              int bottom,
                                              int left,
                                              int right) {
        this.pageNumViewPaddingTop = top;
        this.pageNumViewPaddingBottom = bottom;
        this.pageNumViewPaddingLeft = left;
        this.pageNumViewPaddingRight = right;
        return this;
    }

    public BannerLayout setPageNumViewPadding(int padding) {
        this.pageNumViewPaddingTop = padding;
        this.pageNumViewPaddingBottom = padding;
        this.pageNumViewPaddingLeft = padding;
        this.pageNumViewPaddingRight = padding;
        return this;
    }

    public BannerLayout setPageNumViewMargin(int top,
                                             int bottom,
                                             int left,
                                             int right) {
        this.pageNumViewTopMargin = top;
        this.pageNumViewBottomMargin = bottom;
        this.pageNumViewLeftMargin = left;
        this.pageNumViewRightMargin = right;
        return this;
    }

    public BannerLayout setPageNumViewMargin(int margin) {
        this.pageNumViewTopMargin = margin;
        this.pageNumViewBottomMargin = margin;
        this.pageNumViewLeftMargin = margin;
        this.pageNumViewRightMargin = margin;
        return this;
    }

    public BannerLayout setPageNumViewTextColor(@ColorInt int pageNumViewTextColor) {
        this.pageNumViewTextColor = pageNumViewTextColor;
        return this;
    }

    public BannerLayout setPageNumViewBackgroundColor(@ColorInt int pageNumViewBackgroundColor) {
        this.pageNumViewBackgroundColor = pageNumViewBackgroundColor;
        return this;
    }

    public BannerLayout setPageNumViewTextSize(float pageNumViewTextSize) {
        this.pageNumViewTextSize = pageNumViewTextSize;
        return this;
    }

    public BannerLayout setPageNumViewSite(@PageNumViewSiteMode int pageNumViewSite) {
        this.pageNumViewSite = pageNumViewSite;
        return this;
    }

    public BannerLayout setPageNumViewMark(@NonNull String pageNumViewMark) {
        this.pageNumViewMark = pageNumViewMark;
        return this;
    }

    public BannerLayout setPageNumViewMark(@StringRes int pageNumViewMark) {
        this.pageNumViewMark = getContext().getString(pageNumViewMark);
        return this;
    }

    public BannerLayout setOnBannerClickListener(@NonNull OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
        return this;
    }

    public BannerLayout setImageLoaderManager(@NonNull ImageLoaderManager loaderManage) {
        this.imageLoaderManage = loaderManage;
        return this;
    }

    private BannerLayout initBannerMethod() {
        clearHandler();
        removeAllViews();
        preEnablePosition = 0;
        if (adapter == null) {
            adapter = new BannerAdapter();
        }
        adapter.addAll(imageList);
        adapter.setPlaceImage(placeImageView);
        adapter.setErrorImage(errorImageView);
        adapter.setImageLoaderManage(imageLoaderManage);
        adapter.setImageClickListener(this);

        viewPager = new BannerViewPager(getContext());
        viewPager.setDuration(mDuration);
        viewPager.setViewTouchMode(viePagerTouchMode);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);

        if (isVertical) {
            viewPager.setVertical(isVertical);
        } else {
            viewPager.setPageTransformer(true, bannerTransformer);
        }
        addView(viewPager);

        int currentItem = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % getDotsSize());
        viewPager.setCurrentItem(currentItem);
        bannerHandlerUtils = new BannerHandlerUtils(this, currentItem);
        bannerHandlerUtils.setDelayTime(delayTime);
        switchBanner(isStartRotation);

        if (!isNull(pageView)) {
            pageView.setText(TextUtils.concat(String.valueOf(1), pageNumViewMark, String.valueOf(getDotsSize())));
            addView(pageView, pageView.initPageView(this));
        }
        if (!isNull(bannerTipLayout)) {
            bannerTipLayout.removeAllViews();
            if (isVisibleDots) {
                bannerTipLayout.setDots(this);
            }
            if (isVisibleTitle) {
                bannerTipLayout.setTitle(this);
                bannerTipLayout.setTitle(imageList.get(0).getBannerTitle());
            }
            addView(bannerTipLayout, bannerTipLayout.setBannerTips(this));
        }
        return this;
    }

    @Override
    public int dotsCount() {
        return getDotsSize();
    }

    @Override
    public Drawable dotsSelector() {
        return dotsSelector == BannerDefaults.DOTS_SELECTOR ?
                BannerSelectorUtils.getDrawableSelector(
                        enabledRadius,
                        enabledColor,
                        normalRadius,
                        normalColor)
                :
                ContextCompat.getDrawable(getContext(), dotsSelector);
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
    public int titleWidth() {
        return titleWidth;
    }

    @Override
    public int titleHeight() {
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
    public int tipsWidth() {
        return tipsLayoutWidth;
    }

    @Override
    public int tipsHeight() {
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

    private int getDotsSize() {
        if (!isNull(imageList) && imageList.size() > 0) {
            return imageList.size();
        }
        throw error(R.string.list_null);
    }

    private BannerException error(int messageId) {
        throw new BannerException(getContext().getString(messageId));
    }

    private boolean isNull(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                return true;
            }
        }
        return false;
    }

    public static class BannerException extends RuntimeException {
        public BannerException(String s) {
            super(s);
        }
    }


    public BannerLayout clearBanner() {
        clearViewPager();
        clearHandler();
        clearBannerTipLayout();
        clearTransformerList();
        clearImageList();
        clearPageView();
        return this;
    }


    public BannerLayout clearViewPager() {
        if (!isNull(viewPager)) {
            viewPager.removeAllViews();
            removeView(viewPager);
            viewPager = null;
        }
        return this;
    }

    public BannerLayout clearTransformerList() {
        if (!isNull(transformerList)) {
            transformerList.clear();
            transformerList = null;
        }
        return this;
    }


    public BannerLayout clearImageList() {
        if (!isNull(imageList)) {
            imageList.clear();
            imageList = null;
        }
        return this;
    }

    public BannerLayout clearHandler() {
        if (!isNull(bannerHandlerUtils)) {
            bannerHandlerUtils.removeCallbacksAndMessages(null);
            bannerHandlerUtils = null;
        }
        return this;
    }

    public BannerLayout clearBannerTipLayout() {
        if (!isNull(bannerTipLayout)) {
            bannerTipLayout.removeAllViews();
            removeView(bannerTipLayout);
            bannerTipLayout = null;
        }
        return this;
    }

    public BannerLayout clearPageView() {
        if (!isNull(pageView)) {
            removeView(pageView);
            pageView = null;
        }
        return this;
    }


    public int getDuration() {
        return viewPager.getDuration();
    }


    public BannerViewPager getViewPager() {
        return viewPager;
    }

    public int getBannerStatus() {
        return bannerHandlerUtils.getStatus();
    }

    public List<? extends BannerModelCallBack> getImageList() {
        return imageList;
    }


}
