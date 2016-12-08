package com.bannerlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bannerlayout.Interface.ImageLoaderManage;
import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.Interface.OnBannerImageClickListener;
import com.bannerlayout.Interface.OnBannerPageChangeListener;
import com.bannerlayout.Interface.OnBannerTitleListener;
import com.bannerlayout.Interface.ViewPagerCurrent;
import com.bannerlayout.R;
import com.bannerlayout.adapter.BannerArrayAdapter;
import com.bannerlayout.adapter.BannerBaseAdapter;
import com.bannerlayout.adapter.BannerListAdapter;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.bannerenum.BANNER_ADAPTER_TYPE;
import com.bannerlayout.bannerenum.BANNER_ANIMATION;
import com.bannerlayout.bannerenum.BANNER_ROUND_POSITION;
import com.bannerlayout.bannerenum.BANNER_TIP_LAYOUT_POSITION;
import com.bannerlayout.bannerenum.BANNER_TITLE_POSITION;
import com.bannerlayout.exception.BannerException;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.util.BannerHandlerUtils;
import com.bannerlayout.util.TransformerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/10/24
 */

public class BannerLayout extends RelativeLayout
        implements ViewPagerCurrent, ViewPager.OnPageChangeListener,
        OnBannerImageClickListener {

    private List<BannerTransformer> transformerList = null; //Animation collection
    private BANNER_ADAPTER_TYPE bannerAdapterType = null;
    private OnBannerClickListener onBannerClickListener = null;
    private List<? extends BannerModel> imageList = null;
    private Object[] imageArray = null;
    private String[] imageArrayTitle = null;
    private BannerViewPager viewPager = null;
    private BannerHandlerUtils bannerHandlerUtils = null;
    private BannerTipLayout bannerTipLayout = null;
    private ImageLoaderManage imageLoaderManage = null; //Image Load Manager
    private View promptBarView = null; //The custom hint bar must take over viewpager's OnPageChangeListener method
    private OnBannerPageChangeListener onBannerPageChangeListener = null;
    private OnBannerTitleListener onBannerTitleListener = null;

    private int preEnablePosition = 0;

    private int roundWidth;//Small dot width
    private int roundHeight;//Small dot height
    private boolean isStartRotation;//Whether auto rotation is enabled or not is not enabled by default
    private boolean isRoundContainerBackground;//Whether to display a small dot background
    private boolean viePagerTouchMode; //Viewpager can manually slide, the default can
    private boolean isVisibleTitle;//Whether to display the title default is not displayed
    private boolean isVisibleRound;//Whether to display the small dot default display
    private float titleSize;//font size
    private int titleColor;//font color
    private long delayTime; //Rotation time
    private int roundLeftMargin; //The dots are marginLeft
    private int roundRightMargin;//The dots are marginRight
    private int titleLeftMargin;//title marginLeft
    private int titleRightMargin;//title marginRight
    private float titleWidth;//title width
    private float titleHeight;// title height
    private float roundContainerHeight; //BannerRound height
    private float roundContainerWidth; // BannerRound width
    private int roundContainerBackgroundColor; //BannerRound BackgroundColor
    private int roundSelector; //Small Dot State Selector
    private int errorImageView;//Glide Load error placeholder
    private int placeImageView;//Placeholder in glide loading
    private int mDuration;//Viewpager switching speed
    private boolean isVertical;//Whether the vertical sliding ,The default is not

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BannerLayout);
        roundContainerBackgroundColor = typedArray.getResourceId(R.styleable.BannerLayout_round_container_background, BannerDefaults.ROUND_CONTAINER_BACKGROUND);
        roundContainerWidth = typedArray.getDimension(R.styleable.BannerLayout_round_container_width, BannerDefaults.ROUND_CONTAINER_WIDTH);
        roundContainerHeight = typedArray.getDimension(R.styleable.BannerLayout_round_container_height, BannerDefaults.ROUND_CONTAINER_HEIGHT);
        delayTime = typedArray.getInt(R.styleable.BannerLayout_delay_time, BannerDefaults.DELAY_TIME);
        isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_start_rotation, BannerDefaults.IS_START_ROTATION);
        isRoundContainerBackground = typedArray.getBoolean(R.styleable.BannerLayout_round_container_background_switch, BannerDefaults.ROUND_CONTAINER_BACKGROUND_SWITCH);
        roundLeftMargin = typedArray.getInt(R.styleable.BannerLayout_round_left_margin, BannerDefaults.ROUND_LEFT_MARGIN);
        roundRightMargin = typedArray.getInt(R.styleable.BannerLayout_round_right_margin, BannerDefaults.ROUND_RIGHT_MARGIN);
        roundWidth = typedArray.getInt(R.styleable.BannerLayout_round_width, BannerDefaults.ROUND_WIDth);
        roundHeight = typedArray.getInt(R.styleable.BannerLayout_round_height, BannerDefaults.ROUND_HEIGHT);
        roundSelector = typedArray.getResourceId(R.styleable.BannerLayout_round_selector, BannerDefaults.ROUND_SELECTOR);
        viePagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_view_pager_touch_mode, BannerDefaults.VIEW_PAGER_TOUCH_MODE);
        errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_glide_error_image, BannerDefaults.GLIDE_ERROR_IMAGE);
        placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_glide_place_image, BannerDefaults.GLIDE_PIACE_IMAGE);
        isVisibleTitle = typedArray.getBoolean(R.styleable.BannerLayout_banner_title_visible, BannerDefaults.BANNER_TITLE_VISIBLE);
        titleColor = typedArray.getColor(R.styleable.BannerLayout_banner_title_color, BannerDefaults.BANNER_TITLE_COLOR);
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_banner_title_size, BannerDefaults.BANNER_TITLE_SIZE);
        titleRightMargin = typedArray.getInt(R.styleable.BannerLayout_title_right_margin, BannerDefaults.BANNER_TITLE_RIGHT_MARGIN);
        titleLeftMargin = typedArray.getInt(R.styleable.BannerLayout_title_left_margin, BannerDefaults.BANNER_TITLE_LEFT_MARGIN);
        titleWidth = typedArray.getDimension(R.styleable.BannerLayout_banner_title_width, BannerDefaults.BANNER_TITLE_WIDTH);
        titleHeight = typedArray.getDimension(R.styleable.BannerLayout_banner_title_height, BannerDefaults.BANNER_TITLE_HEIGHT);
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_banner_title_size, BannerDefaults.BANNER_TITLE_SIZE);
        isVisibleRound = typedArray.getBoolean(R.styleable.BannerLayout_banner_round_visible, BannerDefaults.IS_VISIBLE_ROUND);
        mDuration = typedArray.getInt(R.styleable.BannerLayout_banner_duration, BannerDefaults.BANNER_DURATION);
        isVertical = typedArray.getBoolean(R.styleable.BannerLayout_banner_isVertical, BannerDefaults.BANNER_ISVERTICAL);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (viewPager != null) {
            return viewPager.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    /**
     * Initialize the custom hint column before calling initAdapter
     */
    public BannerLayout addPromptBar(View view) {
        this.promptBarView = view;
        return this;
    }

    /**
     * Initialize the dots using the default parameters
     */
    public BannerLayout initRound() {
        initRound(isRoundContainerBackground, isVisibleRound, isVisibleTitle, null, null, null);
        return this;
    }

    /**
     * Initialize small dots, whether to open the background title small dots
     */
    public BannerLayout initRound(boolean isRoundContainerBackground, boolean isVisibleRound, boolean isVisibleTitle) {
        this.isVisibleRound = isVisibleRound;
        this.isVisibleTitle = isVisibleTitle;
        this.isRoundContainerBackground = isRoundContainerBackground;
        initRound(isRoundContainerBackground, isVisibleRound, isVisibleTitle, null, null, null);
        return this;
    }


    /**
     * Initialize the dots control, do not initialize this method if you select custom hint bar
     *
     * @param isBackgroundColor            Whether to display the background color
     * @param bannerRoundContainerPosition The control displays the default bottom position
     * @param bannerRoundPosition          Small dots display position default to the right
     * @param bannerTitlePosition          The title display is not displayed by default
     * @param isVisibleRound               Whether to display small dots, the default display
     * @param isVisibleTitle               Whether to display title, the default is not displayed
     */
    public BannerLayout initRound(boolean isBackgroundColor,
                                  boolean isVisibleRound,
                                  boolean isVisibleTitle,
                                  BANNER_TIP_LAYOUT_POSITION bannerRoundContainerPosition,
                                  BANNER_ROUND_POSITION bannerRoundPosition,
                                  BANNER_TITLE_POSITION bannerTitlePosition) {
        if (promptBarView != null) {
            throw new BannerException("You can not initialize the round if the promptBarView is not null");
        }
        this.isRoundContainerBackground = isBackgroundColor;
        this.isVisibleRound = isVisibleRound;
        this.isVisibleTitle = isVisibleTitle;
        if (bannerAdapterType == null) {
            Toast.makeText(getContext(), getContext().getString(R.string.banner_adapterType_null), Toast.LENGTH_SHORT).show();
            return this;
        }
        if (bannerTipLayout != null) {
            removeView(bannerTipLayout);
            bannerTipLayout = null;
        }
        bannerTipLayout = new BannerTipLayout(getContext());
        bannerTipLayout.removeAllViews();
        if (isVisibleRound) {
            bannerTipLayout.setRound(getRoundSize(), roundSelector, roundWidth, roundHeight, roundLeftMargin, roundRightMargin, bannerRoundPosition);
        }
        if (isVisibleTitle) {
            bannerTipLayout.setTitle(titleColor, titleSize, titleLeftMargin, titleRightMargin, titleWidth, titleHeight, bannerTitlePosition);
            switch (bannerAdapterType) {
                case ARRAY:
                    if (imageArrayTitle != null) {
                        bannerTipLayout.setTitle(imageArrayTitle[0]);
                    }
                    break;
                case LIST:
                    if (onBannerTitleListener != null) {
                        bannerTipLayout.setTitle(onBannerTitleListener.getTitle(0));
                    } else {
                        bannerTipLayout.setTitle(imageList.get(0).getTitle());
                    }
                    break;
            }
        }
        bannerTipLayout.setBannerTip(roundContainerWidth, roundContainerHeight, bannerRoundContainerPosition, isBackgroundColor, roundContainerBackgroundColor);
        addView(bannerTipLayout);
        return this;
    }


    /**
     * Initializes a List image resource
     */
    public BannerLayout initImageListResources(List<? extends BannerModel> imageList) {
        if (imageList == null) {
            throw new BannerException("the imageList is null");
        }
        this.bannerAdapterType = BANNER_ADAPTER_TYPE.LIST;
        this.imageList = imageList;
        return this;
    }

    /**
     * Initializes an Array image resource
     */
    public BannerLayout initImageArrayResources(Object[] imageArray) {
        if (imageArray == null) {
            throw new BannerException("the imageArray is null");
        }
        this.bannerAdapterType = BANNER_ADAPTER_TYPE.ARRAY;
        this.imageArray = imageArray;
        return this;
    }

    /**
     * Initializes an Array image resource
     */
    public BannerLayout initImageArrayResources(Object[] imageArray, String[] imageArrayTitle) {
        if (imageArray == null) {
            throw new BannerException("the imageArray is null");
        }
        this.bannerAdapterType = BANNER_ADAPTER_TYPE.ARRAY;
        this.imageArray = imageArray;
        this.imageArrayTitle = imageArrayTitle;
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
        if (isStartRotation && getRoundSize() > 1) {
            startBanner();
        }
        return this;
    }


    /**
     * Start rotation
     */
    public void startBanner() {
        bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_START);
    }

    /**
     * Paused rotation
     */
    public void stopBanner() {
        bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP);
    }

    /**
     * Resume rotation
     */
    public void restoreBanner() {
        bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_BREAK);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isStartRotation && bannerHandlerUtils != null && getRoundSize() > 1) {
            restoreBanner();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isStartRotation && bannerHandlerUtils != null && getRoundSize() > 1) {
            stopBanner();
        }
    }

    /**
     * Get the number of small dots, where you can also get the number of pictures
     */
    private int getRoundSize() {
        switch (bannerAdapterType) {
            case LIST:
                return imageList.size();
            case ARRAY:
                return imageArray.length;
        }
        throw new BannerException("getRoundSize error");
    }


    /**
     * init adapter();
     * This method must be called after the init image resource
     */
    public BannerLayout initAdapter() {
        if (viewPager != null) {
            removeView(viewPager);
            viewPager = null;
        }
        viewPager = new BannerViewPager(getContext());
        viewPager.setDuration(mDuration);
        viewPager.setVertical(isVertical);
        addView(viewPager);
        if (promptBarView != null) {
            removeView(promptBarView);
            addView(promptBarView);
        }
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(getBannerAdapter());
        viewPager.setCurrentItem((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % getRoundSize()));
        return this;
    }

    /**
     * adapter
     */
    private PagerAdapter getBannerAdapter() {
        BannerBaseAdapter adapter = null;
        switch (bannerAdapterType) {
            case LIST:
                adapter = new BannerListAdapter(imageList);
                break;
            case ARRAY:
                adapter = new BannerArrayAdapter(imageArray);
                break;
        }
        adapter.setPlaceImage(placeImageView);
        adapter.setErrorImage(errorImageView);
        adapter.setImageLoaderManage(imageLoaderManage);
        adapter.setImageClickListener(this);
        return adapter;
    }

    @Override
    public void setCurrentItem(int page) {
        if (viewPager == null) {
            throw new BannerException("the viewPager is null");
        }
        viewPager.setCurrentItem(page);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onBannerPageChangeListener != null && promptBarView != null) {
            onBannerPageChangeListener.onPageScrolled(position % getRoundSize(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (transformerList != null && transformerList.size() > 1 && !isVertical) {
            viewPager.setPageTransformer(true, transformerList.get((int) (Math.random() * transformerList.size())));
        }
        int newPosition = position % getRoundSize();
        if (onBannerPageChangeListener != null && promptBarView != null) {
            onBannerPageChangeListener.onPageSelected(newPosition);
            return;
        }
        if (bannerTipLayout != null) {
            if (isVisibleRound) {
                bannerTipLayout.changeRoundPosition(preEnablePosition, newPosition);
            }
            if (isVisibleTitle) {
                bannerTipLayout.clearText();
                switch (bannerAdapterType) {
                    case ARRAY:
                        if (imageArrayTitle == null) {
                            return;
                        }
                        bannerTipLayout.setTitle(imageArrayTitle[newPosition]);
                        break;
                    case LIST:
                        if (onBannerTitleListener != null) {
                            bannerTipLayout.setTitle(onBannerTitleListener.getTitle(newPosition));
                        } else {
                            bannerTipLayout.setTitle(imageList.get(newPosition).getTitle());
                        }
                        break;
                }
            }
        }
        preEnablePosition = newPosition;
        if (bannerHandlerUtils != null && isStartRotation) {
            bannerHandlerUtils.sendMessage(Message.obtain(bannerHandlerUtils, BannerHandlerUtils.MSG_PAGE, viewPager.getCurrentItem(), 0));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onBannerPageChangeListener != null && promptBarView != null) {
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

    /**
     * setting BannerRoundContainer background
     * The call takes effect before the initRound () method
     */
    public BannerLayout setRoundContainerBackgroundColor(int colorId) {
        this.roundContainerBackgroundColor = colorId;
        return this;
    }


    /**
     * setting BannerRoundHeight
     */
    public BannerLayout setRoundContainerHeight(int height) {
        this.roundContainerHeight = height;
        return this;
    }

    /**
     * setting BannerRoundWidth
     */
    public BannerLayout setRoundContainerWidth(int width) {
        this.roundContainerWidth = width;
        return this;
    }

    /**
     * Sets the status selector for small dots
     * The call takes effect before the initRound () method
     */
    public BannerLayout initRoundSelector(int roundSelector) {
        this.roundSelector = roundSelector;
        return this;
    }


    /**
     * Set the dots marginLeft, the default is 10
     */
    public BannerLayout setRoundLeftMargin(int leftMargin) {
        this.roundLeftMargin = leftMargin;
        return this;
    }

    /**
     * Set the dots marginRight, the default is 10
     */
    public BannerLayout setRoundRightMargin(int rightMargin) {
        this.roundRightMargin = rightMargin;
        return this;
    }

    /**
     * Set the title marginLeft, the default is 10
     */
    public BannerLayout setTitleLeftMargin(int leftMargin) {
        this.titleLeftMargin = leftMargin;
        return this;
    }

    /**
     * Set the title marginRight, the default is 10
     */
    public BannerLayout setTitleRightMargin(int rightMargin) {
        this.titleRightMargin = rightMargin;
        return this;
    }

    /**
     * Set the dots width, the default is 15
     */
    public BannerLayout setRoundWidth(int width) {
        this.roundWidth = width;
        return this;
    }

    /**
     * Set the height of the small dot, the default is 15
     */
    public BannerLayout setRoundHeight(int height) {
        this.roundHeight = height;
        return this;
    }

    /**
     * getViewPager
     */
    public BannerViewPager getViewPager() {
        return viewPager;
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
     * Set the Load Picture Manager
     */
    public BannerLayout setImageLoaderManage(ImageLoaderManage loaderManage) {
        this.imageLoaderManage = loaderManage;
        return this;
    }

    /**
     * Sets whether to display title, which is called before initRound () is initialized
     */
    public BannerLayout setVisibleTitle(boolean isVisibleTitle) {
        this.isVisibleTitle = isVisibleTitle;
        return this;
    }

    /**
     * Sets whether or not to display small dots, called before initRound () is initialized
     */
    public BannerLayout setVisibleRound(boolean isVisibleRound) {
        this.isVisibleRound = isVisibleRound;
        return this;
    }

    /**
     * Glide Loads an error image, called before initAdapter
     */
    public BannerLayout setErrorImageView(int errorImageView) {
        this.errorImageView = errorImageView;
        return this;
    }

    /**
     * Glide loads the image before the initAdapter is called
     */
    public BannerLayout setPlaceImageView(int placeImageView) {
        this.placeImageView = placeImageView;
        return this;
    }

    /**
     * Set whether the viewpager can be swiped, true to prevent sliding
     */
    public BannerLayout setViewPagerTouchMode(boolean b) {
        if (viewPager == null) {
            throw new BannerException("the viewpager is null");
        }
        this.viePagerTouchMode = b;
        viewPager.setViewTouchMode(viePagerTouchMode);
        return this;
    }

    /**
     * Sets the ViewPager switching speed
     */
    public BannerLayout setDuration(int pace) {
        if (viewPager == null) {
            throw new BannerException("the viewpager is null");
        }
        this.mDuration = pace;
        viewPager.setDuration(mDuration);
        return this;
    }


    /**
     * Sets the system to switch animation
     */
    public BannerLayout setBannerTransformer(BANNER_ANIMATION bannerAnimation) {
        if (viewPager == null || bannerAnimation == null) {
            throw new BannerException("the viewpager or bannerAnimation is null");
        }
        if (isVertical) {
            throw new BannerException("If it is a vertical slide can not set the animation");
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
            throw new BannerException("the viewpager or bannertransformer is null");
        }
        if (isVertical) {
            throw new BannerException("If it is a vertical slide can not set the animation");
        }
        viewPager.setPageTransformer(true, bannerTransformer);
        return this;
    }

    /**
     * Customize the animation collection
     */
    public BannerLayout setBannerTransformerList(List<BannerTransformer> list) {
        if (list == null) {
            throw new BannerException("the transformerlist is null");
        }
        if (transformerList != null) {
            transformerList.clear();
            transformerList = null;
        }
        this.transformerList = list;
        return this;
    }

    /**
     * A collection of system animations
     */
    public BannerLayout setBannerSystemTransformerList(List<BANNER_ANIMATION> list) {
        if (list == null) {
            throw new BannerException("the BANNER_ANIMATION is null");
        }
        if (transformerList != null) {
            transformerList.clear();
            transformerList = null;
        }
        transformerList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            transformerList.add(TransformerUtils.getTransformer(list.get(i)));
        }
        return this;
    }

    /**
     * Whether the vertical sliding ,The default is not
     */
    public BannerLayout setVertical(boolean vertical) {
        isVertical = vertical;
        return this;
    }
}
