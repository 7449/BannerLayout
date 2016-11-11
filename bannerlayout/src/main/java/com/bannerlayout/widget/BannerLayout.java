package com.bannerlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.Interface.OnBannerImageClickListener;
import com.bannerlayout.Interface.OnBannerPageChangeListener;
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
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.util.BannerHandlerUtils;
import com.bannerlayout.util.ImageLoaderManage;
import com.bannerlayout.util.TransformerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/10/24
 */

public class BannerLayout extends RelativeLayout
        implements ViewPagerCurrent, ViewPager.OnPageChangeListener,
        OnBannerImageClickListener {

    private List<BannerTransformer> transformerList = null; //动画集合
    private BANNER_ADAPTER_TYPE bannerAdapterType = null;
    private OnBannerClickListener onBannerClickListener = null;
    private List<? extends BannerModel> imageList = null;
    private int[] imageArray = null;
    private String[] imageArrayTitle = null;
    private BannerViewPager viewPager = null;
    private BannerHandlerUtils bannerHandlerUtils = null;
    private BannerTipLayout bannerTipLayout = null;
    private ImageLoaderManage imageLoaderManage = null; //图片加载管理器
    private View promptBarView = null; //自定义提示栏，必须接管viewpager的OnPageChangeListener方法
    private OnBannerPageChangeListener onBannerPageChangeListener = null;

    private int preEnablePosition = 0;

    private int roundWidth;//小圆点width
    private int roundHeight;//小圆点height
    private boolean isStartRotation;//是否开启自动轮播，默认不开启
    private boolean isRoundContainerBackground;//是否显示小圆点背景
    private boolean viePagerTouchMode; //viewpager是否可以手动滑动，默认可以
    private boolean isVisibleTitle;//是否显示title 默认不显示
    private boolean isVisibleRound;//是否显示小圆点 默认显示
    private float titleSize;//字体大小
    private int titleColor;//字体颜色
    private long delayTime; //轮播时间
    private int roundLeftMargin; //小圆点的marginLeft
    private int roundRightMargin;//小圆点的marginRight
    private int titleLeftMargin;//title marginLeft
    private int titleRightMargin;//title marginRight
    private float titleWidth;//title width
    private float titleHeight;// title height
    private float roundContainerHeight; //BannerRound高度
    private float roundContainerWidth; // BannerRound宽度
    private int roundContainerBackgroundColor; //BannerRound背景色
    private int roundSelector; //小圆点状态选择器
    private int errorImageView;//glide加载错误占位符
    private int placeImageView;//glide加载中占位符
    private int mDuration;//viewpager切换速度

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

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        if (viewPager != null) {
            removeView(viewPager);
        }
        viewPager = new BannerViewPager(getContext());
        addView(viewPager);
    }

    /**
     * 初始化自定义提示栏  initAdapter之前调用
     */
    public BannerLayout addPromptBar(View view) {
        this.promptBarView = view;
        return this;
    }

    /**
     * 初始化小圆点,使用默认参数
     */
    public BannerLayout initRound() {
        initRound(isRoundContainerBackground, isVisibleRound, isVisibleTitle, null, null, null);
        return this;
    }

    /**
     * 初始化小圆点,是否开启背景 title 小圆点
     */
    public BannerLayout initRound(boolean isRoundContainerBackground, boolean isVisibleRound, boolean isVisibleTitle) {
        this.isVisibleRound = isVisibleRound;
        this.isVisibleTitle = isVisibleTitle;
        initRound(isRoundContainerBackground, isVisibleRound, isVisibleTitle, null, null, null);
        return this;
    }


    /**
     * 初始化小圆点控件，如果选择自定义提示栏 请不要初始化此方法
     *
     * @param isBackgroundColor            是否显示背景色
     * @param bannerRoundContainerPosition 控件显示位置 默认底部
     * @param bannerRoundPosition          小圆点显示位置 默认右边
     * @param bannerTitlePosition          title显示位置 默认不显示
     * @param isVisibleRound               是否显示小圆点，默认显示
     * @param isVisibleTitle               是否显示title,默认不显示
     */
    public BannerLayout initRound(boolean isBackgroundColor, boolean isVisibleRound, boolean isVisibleTitle, BANNER_TIP_LAYOUT_POSITION bannerRoundContainerPosition, BANNER_ROUND_POSITION bannerRoundPosition, BANNER_TITLE_POSITION bannerTitlePosition) {
        if (promptBarView != null) {
            return this;
        }
        this.isRoundContainerBackground = isBackgroundColor;
        this.isVisibleRound = isVisibleRound;
        this.isVisibleTitle = isVisibleTitle;
        if (bannerAdapterType == null) {
            Toast.makeText(getContext(), "请在初始化图片资源之后再调用initRound()", Toast.LENGTH_LONG).show();
            return this;
        }
        if (bannerTipLayout != null) {
            removeView(bannerTipLayout);
        }
        if (getRoundSize() > 1) {
            bannerTipLayout = new BannerTipLayout(getContext());
            bannerTipLayout.removeAllViews();
            if (isVisibleRound) {
                bannerTipLayout.addRound(getRoundSize(), roundSelector, roundWidth, roundHeight, roundLeftMargin, roundRightMargin, bannerRoundPosition);
            }
            if (isVisibleTitle) {
                bannerTipLayout.addTitle(titleColor, titleSize, titleLeftMargin, titleRightMargin, titleWidth, titleHeight, bannerTitlePosition);
                if (bannerAdapterType == BANNER_ADAPTER_TYPE.ARRAY && imageArrayTitle != null) {
                    bannerTipLayout.setTitle(imageArrayTitle[0]);
                } else if (bannerAdapterType == BANNER_ADAPTER_TYPE.LIST) {
                    bannerTipLayout.setTitle(imageList.get(0).getTitle());
                }
            }
            bannerTipLayout.settingBannerRound(roundContainerWidth, roundContainerHeight, bannerRoundContainerPosition, isBackgroundColor, roundContainerBackgroundColor);
            addView(bannerTipLayout);
        }
        return this;
    }


    /**
     * 初始化List图片资源
     */
    public BannerLayout initImageListResources(List<? extends BannerModel> imageList) {
        if (imageList == null) {
            throw new NullPointerException("the imageList is null");
        }
        this.bannerAdapterType = BANNER_ADAPTER_TYPE.LIST;
        this.imageList = imageList;
        return this;
    }

    /**
     * 初始化Array图片资源
     */
    public BannerLayout initImageArrayResources(int[] imageArray) {
        if (imageArray == null) {
            throw new NullPointerException("the imageArray is null");
        }
        this.bannerAdapterType = BANNER_ADAPTER_TYPE.ARRAY;
        this.imageArray = imageArray;
        return this;
    }

    /**
     * 初始化Array图片资源
     */
    public BannerLayout initImageArrayResources(int[] imageArray, String[] imageArrayTitle) {
        if (imageArray == null) {
            throw new NullPointerException("the imageArray is null");
        }
        this.bannerAdapterType = BANNER_ADAPTER_TYPE.ARRAY;
        this.imageArray = imageArray;
        this.imageArrayTitle = imageArrayTitle;
        return this;
    }

    /**
     * 初始化轮播handler,默认时间2S
     */
    public BannerLayout start() {
        start(isStartRotation, delayTime);
        return this;
    }

    /**
     * 初始化轮播handler
     */
    public BannerLayout start(boolean isStartRotation) {
        bannerHandlerUtils = new BannerHandlerUtils(this, viewPager.getCurrentItem());
        bannerHandlerUtils.setDelayTime(delayTime);
        this.isStartRotation = isStartRotation;
        if (isStartRotation && getRoundSize() > 1) {
            startBanner();
        }
        return this;
    }

    /**
     * 初始化轮播handler
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
     * 开始轮播
     */
    public void startBanner() {
        bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_START);
    }

    /**
     * 暂停轮播
     */
    public void stopBanner() {
        bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP);
    }

    /**
     * 恢复轮播
     */
    public void restoreBanner() {
        bannerHandlerUtils.sendEmptyMessage(BannerHandlerUtils.MSG_BREAK);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isStartRotation && bannerHandlerUtils != null) {
            restoreBanner();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isStartRotation && bannerHandlerUtils != null) {
            stopBanner();
        }
    }

    /**
     * 获取小圆点个数，这里还可以获取图片数量
     */
    private int getRoundSize() {
        switch (bannerAdapterType) {
            case LIST:
                return imageList.size();
            case ARRAY:
                return imageArray.length;
        }
        throw new NullPointerException("getRoundSize error");
    }


    /**
     * 初始化adapter
     * 必须在init图片资源之后才能调用此方法
     */
    public BannerLayout initAdapter() {
        initViewPager();
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
            throw new NullPointerException("the viewPager is null");
        }
        viewPager.setCurrentItem(page);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onBannerPageChangeListener != null) {
            onBannerPageChangeListener.onPageScrolled(position % getRoundSize(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (transformerList != null && transformerList.size() > 1) {
            viewPager.setPageTransformer(true, transformerList.get((int) (Math.random() * transformerList.size())));
        }
        int newPosition = position % getRoundSize();
        if (onBannerPageChangeListener != null && promptBarView != null) {
            onBannerPageChangeListener.onPageSelected(newPosition);
        } else {
            if (bannerTipLayout != null) {
                if (isVisibleRound) {
                    bannerTipLayout.changeRoundPosition(preEnablePosition, newPosition);
                }
                if (isVisibleTitle) {
                    bannerTipLayout.clearText();
                    if (bannerAdapterType == BANNER_ADAPTER_TYPE.ARRAY && imageArrayTitle != null) {
                        bannerTipLayout.setTitle(imageArrayTitle[newPosition]);
                    } else if (bannerAdapterType == BANNER_ADAPTER_TYPE.LIST) {
                        bannerTipLayout.setTitle(imageList.get(newPosition).getTitle());
                    }
                }
            }
            preEnablePosition = newPosition;
            if (bannerHandlerUtils != null && isStartRotation) {
                bannerHandlerUtils.sendMessage(Message.obtain(bannerHandlerUtils, BannerHandlerUtils.MSG_PAGE, viewPager.getCurrentItem(), 0));
            }
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
     * 设置BannerRoundContainer背景色
     * 在initRound()方法之前调用生效
     */
    public BannerLayout setRoundContainerBackgroundColor(int colorId) {
        this.roundContainerBackgroundColor = colorId;
        return this;
    }


    /**
     * 设置BannerRoundHeight
     */
    public BannerLayout setRoundContainerHeight(int height) {
        this.roundContainerHeight = height;
        return this;
    }

    /**
     * 设置BannerRoundWidth
     */
    public BannerLayout setRoundContainerWidth(int width) {
        this.roundContainerWidth = width;
        return this;
    }

    /**
     * 设置小圆点selector
     * 在initRound()方法之前调用生效
     */
    public BannerLayout initRoundSelector(int roundSelector) {
        this.roundSelector = roundSelector;
        return this;
    }


    /**
     * 设置小圆点marginLeft，默认为10
     */
    public BannerLayout setRoundLeftMargin(int leftMargin) {
        this.roundLeftMargin = leftMargin;
        return this;
    }

    /**
     * 设置小圆点marginRight，默认为10
     */
    public BannerLayout setRoundRightMargin(int rightMargin) {
        this.roundRightMargin = rightMargin;
        return this;
    }

    /**
     * 设置title marginLeft，默认为10
     */
    public BannerLayout setTitleLeftMargin(int leftMargin) {
        this.titleLeftMargin = leftMargin;
        return this;
    }

    /**
     * 设置title marginRight，默认为10
     */
    public BannerLayout setTitleRightMargin(int rightMargin) {
        this.titleRightMargin = rightMargin;
        return this;
    }

    /**
     * 设置小圆点width，默认为15
     */
    public BannerLayout setRoundWidth(int width) {
        this.roundWidth = width;
        return this;
    }

    /**
     * 设置小圆点Height，默认为15
     */
    public BannerLayout setRoundHeight(int height) {
        this.roundHeight = height;
        return this;
    }

    /**
     * 获取viewpager
     */
    public BannerViewPager getViewPager() {
        return viewPager;
    }

    /**
     * 设置title颜色,字体大小,不需要的设置-1
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
     * 设置加载图片管理器
     */
    public BannerLayout setImageLoaderManage(ImageLoaderManage loaderManage) {
        this.imageLoaderManage = loaderManage;
        return this;
    }

    /**
     * 设置是否显示title，在初始化initRound()之前调用
     */
    public BannerLayout setVisibleTitle(boolean isVisibleTitle) {
        this.isVisibleTitle = isVisibleTitle;
        return this;
    }

    /**
     * 设置是否显示小圆点，在初始化initRound()之前调用
     */
    public BannerLayout setVisibleRound(boolean isVisibleRound) {
        this.isVisibleRound = isVisibleRound;
        return this;
    }

    /**
     * glide加载错误图片,在initAdapter之前调用
     */
    public BannerLayout setErrorImageView(int errorImageView) {
        this.errorImageView = errorImageView;
        return this;
    }

    /**
     * Glide加载中图片,在initAdapter之前调用
     */
    public BannerLayout setPlaceImageView(int placeImageView) {
        this.placeImageView = placeImageView;
        return this;
    }

    /**
     * 设置viewpager是否可以滑动，true禁止滑动
     */
    public BannerLayout setViewPagerTouchMode(boolean b) {
        if (viewPager == null) {
            throw new NullPointerException("the viewpager is null");
        }
        this.viePagerTouchMode = b;
        viewPager.setViewTouchMode(viePagerTouchMode);
        return this;
    }

    /**
     * 设置ViewPager切换速度
     */
    public BannerLayout setDuration(int pace) {
        if (viewPager == null) {
            throw new NullPointerException("the viewpager is empty");
        }
        this.mDuration = pace;
        viewPager.setDuration(mDuration);
        return this;
    }

    /**
     * 设置系统切换动画
     */
    public BannerLayout setBannerTransformer(BANNER_ANIMATION bannerAnimation) {
        if (viewPager == null || bannerAnimation == null) {
            throw new NullPointerException("the viewpager or bannerAnimation is empty");
        }
        viewPager.setPageTransformer(true, TransformerUtils.getTransformer(bannerAnimation));
        return this;
    }

    /**
     * 设置自定义切换动画
     */
    public BannerLayout setBannerTransformer(BannerTransformer bannerTransformer) {
        if (viewPager == null || bannerTransformer == null) {
            throw new NullPointerException("the viewpager or bannertransformer is empty");
        }
        viewPager.setPageTransformer(true, bannerTransformer);
        return this;
    }

    /**
     * 自定义动画集合
     */
    public BannerLayout setBannerTransformerList(List<BannerTransformer> list) {
        if (list == null) {
            throw new NullPointerException("the transformerlist is empty");
        }
        if (transformerList != null) {
            transformerList.clear();
            transformerList = null;
        }
        this.transformerList = list;
        return this;
    }

    /**
     * 系统动画集合
     */
    public BannerLayout setBannerSystemTransformerList(List<BANNER_ANIMATION> list) {
        if (list == null) {
            throw new NullPointerException("the BANNER_ANIMATION is empty");
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

}
