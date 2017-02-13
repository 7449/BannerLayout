package com.bannerlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.bannerlayout.annotation.DotsAndTitleSiteMode;
import com.bannerlayout.annotation.PageNumViewSiteMode;
import com.bannerlayout.annotation.TipsSiteMode;
import com.bannerlayout.bannerenum.BannerAnimation;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.util.BannerHandlerUtils;
import com.bannerlayout.util.BannerSelectorUtils;
import com.bannerlayout.util.TransformerUtils;
import com.bannerlayout.widget.BannerTipsLayout.DotsInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * by y on 2016/10/24
 */

@SuppressWarnings("ALL")
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
    private float titleWidth;//title width
    private float titleHeight;// title height
    private int titleSite;

    private float tipsLayoutHeight; //BannerTipsLayout height
    private float tipsLayoutWidth; // BannerTipsLayout width
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
    private int pageNumViewTextSize;

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BannerLayout);

        isTipsBackground = typedArray.getBoolean(R.styleable.BannerLayout_is_tips_background, BannerDefaults.IS_TIPS_LAYOUT_BACKGROUND);
        tipsBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_tips_background, BannerDefaults.TIPS_LAYOUT_BACKGROUND);
        tipsLayoutWidth = typedArray.getDimension(R.styleable.BannerLayout_tips_width, BannerDefaults.TIPS_LAYOUT_WIDTH);
        tipsLayoutHeight = typedArray.getDimension(R.styleable.BannerLayout_tips_height, BannerDefaults.TIPS_LAYOUT_HEIGHT);

        isVisibleDots = typedArray.getBoolean(R.styleable.BannerLayout_dots_visible, BannerDefaults.IS_VISIBLE_DOTS);
        dotsLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_dots_left_margin, BannerDefaults.DOTS_LEFT_MARGIN);
        dotsRightMargin = typedArray.getInteger(R.styleable.BannerLayout_dots_right_margin, BannerDefaults.DOTS_RIGHT_MARGIN);
        dotsWidth = typedArray.getInteger(R.styleable.BannerLayout_dots_width, BannerDefaults.DOTS_WIDth);
        dotsHeight = typedArray.getInteger(R.styleable.BannerLayout_dots_height, BannerDefaults.DOTS_HEIGHT);
        dotsSelector = typedArray.getResourceId(R.styleable.BannerLayout_dots_selector, BannerDefaults.DOTS_SELECTOR);
        enabledRadius = typedArray.getFloat(R.styleable.BannerLayout_enabledRadius, BannerDefaults.ENABLED_RADIUS);
        enabledColor = typedArray.getColor(R.styleable.BannerLayout_enabledColor, BannerDefaults.ENABLED_COLOR);
        normalRadius = typedArray.getFloat(R.styleable.BannerLayout_normalRadius, BannerDefaults.NORMAL_RADIUS);
        normalColor = typedArray.getColor(R.styleable.BannerLayout_normalColor, BannerDefaults.NORMAL_COLOR);

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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isStartRotation && !isNull(bannerHandlerUtils) && getDotsSize() > 1) {
            restoreBanner();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isStartRotation && !isNull(bannerHandlerUtils) && getDotsSize() > 1) {
            stopBanner();
        }
    }


    @Override
    public void setCurrentItem(int page) {
        if (!isNull(viewPager)) {
            viewPager.setCurrentItem(page);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!isNull(onBannerPageChangeListener, tipsView)) {
            onBannerPageChangeListener.onPageScrolled(position % getDotsSize(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % getDotsSize();
        if (!isNull(pageView)) {
            pageView.setText(newPosition + 1 + " / " + getDotsSize());
        }
        if (!isNull(onBannerPageChangeListener, tipsView)) {
            onBannerPageChangeListener.onPageSelected(newPosition);
        } else {
            if (!isNull(bannerTipLayout)) {
                if (isVisibleDots) {
                    bannerTipLayout.changeDotsPosition(preEnablePosition, newPosition);
                }
                if (isVisibleTitle) {
                    bannerTipLayout.clearText();
                    if (!isNull(onBannerTitleListener)) {
                        bannerTipLayout.setTitle(onBannerTitleListener.getTitle(newPosition));
                    } else {
                        bannerTipLayout.setTitle(imageList.get(newPosition).getTitle());
                    }
                }
            }
            preEnablePosition = newPosition;
            if (!isNull(transformerList) && transformerList.size() > 1 && !isVertical) {
                viewPager.setPageTransformer(true, transformerList.get((int) (Math.random() * transformerList.size())));
            }
            if (!isNull(bannerHandlerUtils) && isStartRotation) {
                bannerHandlerUtils.sendMessage(Message.obtain(bannerHandlerUtils, BannerHandlerUtils.MSG_PAGE, viewPager.getCurrentItem(), 0));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (!isNull(onBannerPageChangeListener, tipsView)) {
            onBannerPageChangeListener.onPageScrollStateChanged(state);
        } else {
            if (!isNull(bannerHandlerUtils) && isStartRotation) {
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


    public BannerLayout initPageNumView() {
        if (!isNull(pageView)) {
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
        if (!isNull(tipsView)) {
            error(R.string.tipsView_no_null);
        }
        if (isNull(imageList)) {
            error(R.string.banner_adapterType_null);
        }
        this.isTipsBackground = isBackgroundColor;
        this.isVisibleDots = isVisibleDots;
        this.isVisibleTitle = isVisibleTitle;
        if (!isNull(bannerTipLayout)) {
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
            if (!isNull(onBannerTitleListener)) {
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
    public BannerLayout initListResources(@NonNull List<? extends BannerModel> imageList) {
        if (isNull(imageList)) {
            error(R.string.list_null);
        }
        this.imageList = imageList;
        initAdapter();
        return this;
    }


    /**
     * Initializes an Array image resource
     */
    public BannerLayout initArrayResources(@NonNull Object[] imageArray) {
        if (isNull(imageArray)) {
            error(R.string.array_null);
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
    public BannerLayout initArrayResources(@NonNull Object[] imageArray, @NonNull String[] imageArrayTitle) {
        if (isNull(imageArray, imageArrayTitle)) {
            error(R.string.array_null_);
        }
        imageArrayList = new ArrayList<>();
        List<Object> url = Arrays.asList(imageArray);
        List<String> title = Arrays.asList(imageArrayTitle);
        if (url.size() != title.size()) {
            error(R.string.array_size_lnconsistent);
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


    /**
     * glide Loads an error image, called before initAdapter
     */
    public BannerLayout setErrorImageView(int errorImageView) {
        this.errorImageView = errorImageView;
        if (!isNull(adapter)) {
            adapter.setErrorImage(errorImageView);
        }
        return this;
    }

    /**
     * glide loads the image before the initAdapter is called
     */
    public BannerLayout setPlaceImageView(int placeImageView) {
        this.placeImageView = placeImageView;
        if (!isNull(adapter)) {
            adapter.setPlaceImage(placeImageView);
        }
        return this;
    }

    /**
     * sets the ViewPager switching speed
     */
    public BannerLayout setDuration(int pace) {
        this.mDuration = pace;
        if (!isNull(viewPager)) {
            viewPager.setDuration(pace);
        }
        return this;
    }

    /**
     * sets whether the viewpager can be swiped, true to prevent sliding
     */
    public BannerLayout setViewPagerTouchMode(boolean b) {
        this.viePagerTouchMode = b;
        if (!isNull(viewPager)) {
            viewPager.setViewTouchMode(b);
        }
        return this;
    }

    /**
     * Whether the vertical sliding ,The default is not
     */
    public BannerLayout setVertical(boolean vertical) {
        this.isVertical = vertical;
        if (!isNull(viewPager)) {
            viewPager.setVertical(vertical);
        }
        return this;
    }

    /**
     * Initialize the custom hint column before calling initAdapter
     */
    public BannerLayout setTipsView(@NonNull View view) {
        if (!isNull(tipsView)) {
            removeView(tipsView);
            tipsView = null;
        }
        this.tipsView = view;
        addView(tipsView);
        return this;
    }

    /**
     * get ViewPager sliding speed
     */
    public int getDuration() {
        if (isNull(viewPager)) {
            error(R.string.viewpager_null);
        }
        return viewPager.getDuration();
    }

    public BannerViewPager getViewPager() {
        return viewPager;
    }


    /**
     * get banner rotation status
     */
    public int getBannerStatus() {
        if (isNull(bannerHandlerUtils)) {
            error(R.string.banner_handler_erro);
        }
        return bannerHandlerUtils.getBannerStatus();
    }

    public void startBanner() {
        if (!isNull(bannerHandlerUtils)) {
            bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_START);
        }
    }

    public void stopBanner() {
        if (!isNull(bannerHandlerUtils)) {
            bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP);
        }
    }

    public void restoreBanner() {
        if (!isNull(bannerHandlerUtils)) {
            bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_BREAK);
        }
    }

    /************************************************************************************************************
     * <p>                          BannerLayout method end
     *************************************************************************************************************/


    /************************************************************************************************************
     * <p>
     * <p>                          BannerTipsLayout method start
     * <p>
     *************************************************************************************************************/


    public BannerLayout setTitleSetting(int titleColor, float titleSize) {
        if (titleSize != -1) {
            this.titleSize = titleSize;
        }
        if (titleColor != -1) {
            this.titleColor = titleColor;
        }
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
     * sets the status selector for small dots
     * The call takes effect before the initTips () method
     */
    public BannerLayout initTipsDotsSelector(int dotsSelector) {
        this.dotsSelector = dotsSelector;
        return this;
    }

    /**
     * setting BannerTipsLayoutHeight
     */
    public BannerLayout setTipsWidthAndHeight(int width, int height) {
        this.tipsLayoutHeight = height;
        this.tipsLayoutWidth = width;
        return this;
    }


    /**
     * set the position of the tips in the layout
     */
    public BannerLayout setTipsSite(@TipsSiteMode int tipsSite) {
        this.tipsSite = tipsSite;
        return this;
    }

    /**
     * sets whether to display title, which is called before initTips () is initialized
     */
    public BannerLayout setVisibleTitle(boolean isVisibleTitle) {
        this.isVisibleTitle = isVisibleTitle;
        return this;
    }

    /**
     * sets the title marginLeft and marginRight, the default is 10
     */
    public BannerLayout setTitleMargin(int leftMargin, int rightMargin) {
        this.titleLeftMargin = leftMargin;
        this.titleRightMargin = rightMargin;
        return this;
    }


    /**
     * set the position of the title in the layout
     */
    public BannerLayout setTitleSite(@DotsAndTitleSiteMode int titleSite) {
        this.titleSite = titleSite;
        return this;
    }

    /**
     * sets whether or not to display small dots, called before initTips () is initialized
     */
    public BannerLayout setVisibleDots(boolean isVisibleDots) {
        this.isVisibleDots = isVisibleDots;
        return this;
    }

    /**
     * set the dots width and height, the default is 15
     */
    public BannerLayout setDotsWidthAndHeight(int width, int height) {
        this.dotsWidth = width;
        this.dotsHeight = height;
        return this;
    }

    /**
     * set the position of the dots in the layout
     */
    public BannerLayout setDotsSite(@DotsAndTitleSiteMode int dotsSite) {
        this.dotsSite = dotsSite;
        return this;
    }

    /**
     * sets the dots marginLeft and marginRight, the default is 10
     */
    public BannerLayout setDotsMargin(int leftMargin, int rightMargin) {
        this.dotsLeftMargin = leftMargin;
        this.dotsRightMargin = rightMargin;
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

    /************************************************************************************************************
     * <p>                           BannerTipsLayout method end
     *************************************************************************************************************/

    /************************************************************************************************************
     * <p>
     * <p>                          viewPager  Transformer    start
     * <p>
     *************************************************************************************************************/

    public BannerLayout setBannerTransformer(@NonNull BannerAnimation bannerAnimation) {
        if (isNull(viewPager) || isNull(bannerAnimation)) {
            error(R.string.viewpager_or_transformer_null);
        }
        if (isVertical) {
            error(R.string.vertical);
        }
        setBannerTransformer(TransformerUtils.getTransformer(bannerAnimation));
        return this;
    }

    public BannerLayout setBannerTransformer(@NonNull BannerTransformer bannerTransformer) {
        if (isNull(viewPager) || isNull(bannerTransformer)) {
            error(R.string.viewpager_or_transformer_null);
        }
        if (isVertical) {
            error(R.string.vertical);
        }
        viewPager.setPageTransformer(true, bannerTransformer);
        return this;
    }

    public BannerLayout setBannerSystemTransformerList(@NonNull List<BannerAnimation> list) {
        if (isNull(list)) {
            error(R.string.animationList_null);
        }

        transformerList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            transformerList.add(TransformerUtils.getTransformer(list.get(i)));
        }
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
     * <p>                          viewPager  Transformer    end
     *************************************************************************************************************/

    /************************************************************************************************************
     * <p>
     * <p>                          BannerPageNumView  setting    start
     * <p>
     *************************************************************************************************************/

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

    public BannerLayout setPageNumViewSite(@PageNumViewSiteMode int pageNumViewSite) {
        this.pageNumViewSite = pageNumViewSite;
        return this;
    }

    /************************************************************************************************************
     * <p>                          BannerPageNumView  setting    end
     *************************************************************************************************************/

    /************************************************************************************************************
     * <p>
     * <p>                          BannerLayout interface listener      start
     * <p>
     *************************************************************************************************************/


    public BannerLayout setOnBannerClickListener(@NonNull OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
        return this;
    }

    public BannerLayout addOnBannerPageChangeListener(@NonNull OnBannerPageChangeListener onBannerPageChangeListener) {
        this.onBannerPageChangeListener = onBannerPageChangeListener;
        return this;
    }

    public BannerLayout addOnBannerTitleListener(@NonNull OnBannerTitleListener onBannerTitleListener) {
        this.onBannerTitleListener = onBannerTitleListener;
        return this;
    }

    public BannerLayout setImageLoaderManager(@NonNull ImageLoaderManager loaderManage) {
        this.imageLoaderManage = loaderManage;
        if (!isNull(adapter)) {
            adapter.setImageLoaderManage(loaderManage);
        }
        return this;
    }

    /************************************************************************************************************
     * <p>                          BannerLayout interface listener      end
     *************************************************************************************************************/


    /************************************************************************************************************
     * <p>
     * <p>                          dotsView default setting    start
     * <p>
     *************************************************************************************************************/


    @Override
    public int dotsCount() {
        return getDotsSize();
    }

    @Override
    public Drawable dotsSelector() {
        return dotsSelector == BannerDefaults.DOTS_SELECTOR ?
                BannerSelectorUtils.getDrawableSelector(
                        enabledRadius,
                        ContextCompat.getColor(getContext(), enabledColor),
                        normalRadius,
                        ContextCompat.getColor(getContext(), normalColor))
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


    /************************************************************************************************************
     * <p>                          dotsView default setting    end
     *************************************************************************************************************/


    /************************************************************************************************************
     * <p>
     * <p>                          titleView default setting    start
     * <p>
     *************************************************************************************************************/

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

    /************************************************************************************************************
     * <p>                          titleView default setting    end
     *************************************************************************************************************/


    /************************************************************************************************************
     * <p>
     * <p>                          BannerTipsView default setting    start
     * <p>
     *************************************************************************************************************/


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

    /************************************************************************************************************
     * <p>                          BannerTipsView default setting    end
     *************************************************************************************************************/


    /************************************************************************************************************
     * <p>
     * <p>                          BannerPageNumView default setting    start
     * <p>
     *************************************************************************************************************/

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

    /************************************************************************************************************
     * <p>                          BannerPageNumView default setting    end
     *************************************************************************************************************/


    private void initAdapter() {
        if (!isNull(viewPager)) {
            removeView(viewPager);
            viewPager = null;
        }
        viewPager = new BannerViewPager(getContext());
        viewPager.setDuration(mDuration);
        viewPager.setVertical(isVertical);
        viewPager.setViewTouchMode(viePagerTouchMode);
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

    private int getDotsSize() {
        if (!isNull(imageList) && imageList.size() > 0) {
            return imageList.size();
        }
        throw error(R.string.list_null);
    }

    private String getString(int id) {
        return getContext().getString(id);
    }

    private BannerException error(int messageId) {
        throw new BannerException(getString(messageId));
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


    /**
     * gallery ? See google play home page, a simple test
     */
    private void gallery() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(10);
        setClipChildren(false);
        viewPager.setClipChildren(false);
        params.rightMargin = 60;
        params.leftMargin = 60;
        addView(viewPager, params);
    }
}
