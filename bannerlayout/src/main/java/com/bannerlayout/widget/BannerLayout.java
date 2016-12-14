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

import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.Interface.OnBannerPageChangeListener;
import com.bannerlayout.Interface.OnBannerTitleListener;
import com.bannerlayout.Interface.ViewPagerCurrent;
import com.bannerlayout.R;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.bannerenum.BannerAnimationType;
import com.bannerlayout.bannerenum.BannerDotsSite;
import com.bannerlayout.bannerenum.BannerTipsSite;
import com.bannerlayout.bannerenum.BannerTitleSite;
import com.bannerlayout.exception.BannerException;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.util.BannerHandlerUtils;
import com.bannerlayout.util.TransformerUtils;
import com.bannerlayout.widget.BannerTipsLayout.DotsInterface;

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
        BannerTipsLayout.TipsInterface {

    private List<BannerTransformer> transformerList = null; //Animation collection
    private OnBannerClickListener onBannerClickListener = null;
    private List<? extends BannerModel> imageList = null;
    private List<BannerModel> imageArrayList = null;
    private BannerViewPager viewPager = null;
    private BannerHandlerUtils bannerHandlerUtils = null;
    private BannerTipsLayout bannerTipLayout = null;
    private ImageLoaderManager imageLoaderManage = null; //Image Load Manager
    private View promptBarView = null; //The custom hint bar must take over viewpager's OnPageChangeListener method
    private OnBannerPageChangeListener onBannerPageChangeListener = null;
    private OnBannerTitleListener onBannerTitleListener = null;

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
    private int tipsLayoutBackgroundColor; //BannerTipsLayout BackgroundColor
    private int dotsSelector; //Dots State Selector
    private int errorImageView;//Glide Load error placeholder
    private int placeImageView;//Placeholder in glide loading
    private int mDuration;//Viewpager switching speed
    private boolean isVertical;//Whether the vertical sliding ,The default is not

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BannerLayout);

        isTipsBackground = typedArray.getBoolean(R.styleable.BannerLayout_is_tips_background, BannerDefaults.IS_TIPS_LAYOUT_BACKGROUND);
        tipsLayoutBackgroundColor = typedArray.getResourceId(R.styleable.BannerLayout_tips_background, BannerDefaults.TIPS_LAYOUT_BACKGROUND);
        tipsLayoutWidth = typedArray.getDimension(R.styleable.BannerLayout_tips_width, BannerDefaults.TIPS_LAYOUT_WIDTH);
        tipsLayoutHeight = typedArray.getDimension(R.styleable.BannerLayout_tips_height, BannerDefaults.TIPS_LAYOUT_HEIGHT);

        isVisibleDots = typedArray.getBoolean(R.styleable.BannerLayout_dots_visible, BannerDefaults.IS_VISIBLE_DOTS);
        dotsLeftMargin = typedArray.getInt(R.styleable.BannerLayout_dots_left_margin, BannerDefaults.DOTS_LEFT_MARGIN);
        dotsRightMargin = typedArray.getInt(R.styleable.BannerLayout_dots_right_margin, BannerDefaults.DOTS_RIGHT_MARGIN);
        dotsWidth = typedArray.getInt(R.styleable.BannerLayout_dots_width, BannerDefaults.DOTS_WIDth);
        dotsHeight = typedArray.getInt(R.styleable.BannerLayout_dots_height, BannerDefaults.DOTS_HEIGHT);
        dotsSelector = typedArray.getResourceId(R.styleable.BannerLayout_dots_selector, BannerDefaults.DOTS_SELECTOR);

        delayTime = typedArray.getInt(R.styleable.BannerLayout_delay_time, BannerDefaults.DELAY_TIME);
        isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_start_rotation, BannerDefaults.IS_START_ROTATION);
        viePagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_view_pager_touch_mode, BannerDefaults.VIEW_PAGER_TOUCH_MODE);
        errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_glide_error_image, BannerDefaults.GLIDE_ERROR_IMAGE);
        placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_glide_place_image, BannerDefaults.GLIDE_PIACE_IMAGE);
        mDuration = typedArray.getInt(R.styleable.BannerLayout_banner_duration, BannerDefaults.BANNER_DURATION);
        isVertical = typedArray.getBoolean(R.styleable.BannerLayout_banner_isVertical, BannerDefaults.IS_VERTICAL);

        isVisibleTitle = typedArray.getBoolean(R.styleable.BannerLayout_title_visible, BannerDefaults.TITLE_VISIBLE);
        titleColor = typedArray.getColor(R.styleable.BannerLayout_title_color, BannerDefaults.TITLE_COLOR);
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_title_size, BannerDefaults.TITLE_SIZE);
        titleRightMargin = typedArray.getInt(R.styleable.BannerLayout_title_right_margin, BannerDefaults.TITLE_RIGHT_MARGIN);
        titleLeftMargin = typedArray.getInt(R.styleable.BannerLayout_title_left_margin, BannerDefaults.TITLE_LEFT_MARGIN);
        titleWidth = typedArray.getDimension(R.styleable.BannerLayout_title_width, BannerDefaults.TITLE_WIDTH);
        titleHeight = typedArray.getDimension(R.styleable.BannerLayout_title_height, BannerDefaults.TITLE_HEIGHT);
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_title_size, BannerDefaults.TITLE_SIZE);
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
     * Initialize the dots using the default parameters
     */
    public BannerLayout initTips() {
        initTips(isTipsBackground, isVisibleDots, isVisibleTitle, null, null, null);
        return this;
    }

    /**
     * Initialize dots, whether to open the background title dots
     */
    public BannerLayout initTips(boolean isTipsBackground,
                                 boolean isVisibleDots,
                                 boolean isVisibleTitle) {
        this.isVisibleDots = isVisibleDots;
        this.isVisibleTitle = isVisibleTitle;
        this.isTipsBackground = isTipsBackground;
        initTips(isTipsBackground, isVisibleDots, isVisibleTitle, null, null, null);
        return this;
    }


    /**
     * Initialize the dots control, do not initialize this method if you select custom hint bar
     *
     * @param isBackgroundColor Whether to display the background color
     * @param bannerTipsSite    The control displays the default bottom position
     * @param bannerDotsSite    Small dots display position default to the right
     * @param bannerTitleSite   The title display is not displayed by default
     * @param isVisibleDots     Whether to display small dots, the default display
     * @param isVisibleTitle    Whether to display title, the default is not displayed
     */
    public BannerLayout initTips(boolean isBackgroundColor,
                                 boolean isVisibleDots,
                                 boolean isVisibleTitle,
                                 BannerTipsSite bannerTipsSite,
                                 BannerDotsSite bannerDotsSite,
                                 BannerTitleSite bannerTitleSite) {
        if (promptBarView != null) {
            throw new BannerException("You can not initialize the round if the promptBarView is not null");
        }
        this.isTipsBackground = isBackgroundColor;
        this.isVisibleDots = isVisibleDots;
        this.isVisibleTitle = isVisibleTitle;
        if (imageList == null) {
            Toast.makeText(getContext(), getContext().getString(R.string.banner_adapterType_null), Toast.LENGTH_SHORT).show();
            return this;
        }
        if (bannerTipLayout != null) {
            removeView(bannerTipLayout);
            bannerTipLayout = null;
        }
        bannerTipLayout = new BannerTipsLayout(getContext());
        bannerTipLayout.removeAllViews();
        if (isVisibleDots) {
            bannerTipLayout.setDots(bannerDotsSite, this);
        }
        if (isVisibleTitle) {
            bannerTipLayout.setTitle(bannerTitleSite, this);
            if (onBannerTitleListener != null) {
                bannerTipLayout.setTitle(onBannerTitleListener.getTitle(0) + "");
            } else {
                bannerTipLayout.setTitle(imageList.get(0).getTitle() + "");
            }
        }
        bannerTipLayout.setBannerTips(bannerTipsSite, this);
        addView(bannerTipLayout);
        return this;
    }


    /**
     * Initializes a List image resource
     */
    public BannerLayout initListResources(List<? extends BannerModel> imageList) {
        if (imageList == null) {
            throw new BannerException("the imageList is null");
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
            throw new BannerException("the imageArray is null");
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
        if (imageArray == null) {
            throw new BannerException("the imageArray is null");
        }
        imageArrayList = new ArrayList<>();
        List<Object> url = Arrays.asList(imageArray);
        List<String> title = Arrays.asList(imageArrayTitle);
        if (url.size() != title.size()) {
            throw new BannerException("Check the url and title number inconsistencies, please confirm the data");
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
        this.isStartRotation = isStartRotation;
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
    private BannerLayout initAdapter() {
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
        viewPager.setCurrentItem((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % getDotsSize()));
        return this;
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
            onBannerPageChangeListener.onPageScrolled(position % getDotsSize(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (transformerList != null && transformerList.size() > 1 && !isVertical) {
            viewPager.setPageTransformer(true, transformerList.get((int) (Math.random() * transformerList.size())));
        }
        int newPosition = position % getDotsSize();
        if (onBannerPageChangeListener != null && promptBarView != null) {
            onBannerPageChangeListener.onPageSelected(newPosition);
            return;
        }
        if (bannerTipLayout != null) {
            if (isVisibleDots) {
                bannerTipLayout.changeRoundPosition(preEnablePosition, newPosition);
            }
            if (isVisibleTitle) {
                bannerTipLayout.clearText();
                if (onBannerTitleListener != null) {
                    bannerTipLayout.setTitle(onBannerTitleListener.getTitle(newPosition) + "");
                } else {
                    bannerTipLayout.setTitle(imageList.get(newPosition).getTitle() + "");
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


    @Override
    public int dotsCount() {
        return getDotsSize();
    }

    @Override
    public int dotsSelector() {
        return dotsSelector;
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
    public float tipsWidth() {
        return tipsLayoutWidth;
    }

    @Override
    public float tipsHeight() {
        return tipsLayoutHeight;
    }

    @Override
    public int tipsLayoutBackgroundColor() {
        return tipsLayoutBackgroundColor;
    }

    @Override
    public boolean isBackgroundColor() {
        return isTipsBackground;
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
    public BannerLayout setImageLoaderManage(ImageLoaderManager loaderManage) {
        this.imageLoaderManage = loaderManage;
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
    public BannerLayout setBannerTransformer(BannerAnimationType bannerAnimation) {
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
    public BannerLayout setBannerSystemTransformerList(List<BannerAnimationType> list) {
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

    /**
     * Initialize the custom hint column before calling initAdapter
     */
    public BannerLayout addPromptBar(View view) {
        this.promptBarView = view;
        return this;
    }

    /**
     * setting BannerTipsLayout background
     * The call takes effect before the initTips () method
     */
    public BannerLayout setTipsLayoutBackgroundColor(int colorId) {
        this.tipsLayoutBackgroundColor = colorId;
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
     * Set the dots marginLeft, the default is 10
     */
    public BannerLayout setDotsLeftMargin(int leftMargin) {
        this.dotsLeftMargin = leftMargin;
        return this;
    }

    /**
     * Set the dots marginRight, the default is 10
     */
    public BannerLayout setDotsRightMargin(int rightMargin) {
        this.dotsRightMargin = rightMargin;
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

    /**
     * getViewPager
     */
    public BannerViewPager getViewPager() {
        return viewPager;
    }

    /**
     * adapter
     */
    private PagerAdapter getBannerAdapter() {
        BannerAdapter adapter = new BannerAdapter(imageList);
        adapter.setPlaceImage(placeImageView);
        adapter.setErrorImage(errorImageView);
        adapter.setImageLoaderManage(imageLoaderManage);
        adapter.setImageClickListener(this);
        return adapter;
    }

    /**
     * Get the number of dots, where you can also get the number of pictures
     */
    private int getDotsSize() {
        if (imageList != null) {
            return imageList.size();
        }
        throw new BannerException("the imageList is null");
    }

}
