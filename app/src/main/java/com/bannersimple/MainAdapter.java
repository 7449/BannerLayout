package com.bannersimple;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.animation.AccordionTransformer;
import com.bannerlayout.animation.BackgroundToForegroundTransformer;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.animation.CubeInTransformer;
import com.bannerlayout.animation.CubeOutTransformer;
import com.bannerlayout.animation.RotateDownTransformer;
import com.bannerlayout.bannerenum.BANNER_ANIMATION;
import com.bannerlayout.bannerenum.BANNER_ROUND_POSITION;
import com.bannerlayout.bannerenum.BANNER_TIP_LAYOUT_POSITION;
import com.bannerlayout.bannerenum.BANNER_TITLE_POSITION;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.holder.ArrayBannerHolder;
import com.bannersimple.holder.ImageModelHolder;
import com.bannersimple.holder.PromptBarHolder;
import com.bannersimple.holder.SystemModelHolder;
import com.bannersimple.holder.SystemNetWorkModelHolder;
import com.bannersimple.holder.SystemTransformersHolder;
import com.bannersimple.holder.SystemTransformersListHolder;
import com.bannersimple.holder.TransformersHolder;
import com.bannersimple.holder.TransformersListHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/11/14
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.BaseViewHolder> {
    public static final int IMAGE_MODEL = 0;//自定义model类
    public static final int SYSTEM_NETWORK_MODEL = 1;//自带的Model类,使用网络数据
    public static final int SYSTEM_MODEL = 2;//自带的Model类,使用本地资源
    public static final int ARRAY_BANNER = 3;//数组资源
    public static final int TRANSFORMERS = 4;//自定义动画
    public static final int SYSTEM_TRANSFORMER = 5;//系统动画
    public static final int TRANSFORMERS_LIST = 6;//自定义动画集合
    public static final int SYSTEM_TRANSFORMER_LIST = 7;//系统动画集合
    public static final int PROMPT_BAR = 8;


    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (holder instanceof ImageModelHolder) {
            holder.getTitle().setText("自定义model类,提示栏在顶部");
            holder.getBannerLayout()
                    .initImageListResources(initImageModel())
                    .initAdapter()
                    .setTitleSetting(ContextCompat.getColor(((ImageModelHolder) holder).itemView.getContext(), R.color.colorAccent), -1)
                    .initRound(true, true, true, BANNER_TIP_LAYOUT_POSITION.TOP, null, null)
                    .start(true, 2000)
                    .setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void onBannerClick(int position, Object model) {
                            Toast.makeText(((ImageModelHolder) holder).itemView.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (holder instanceof SystemNetWorkModelHolder) {
            holder.getTitle().setText("网络数据,提示栏在底部");
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true, BANNER_TIP_LAYOUT_POSITION.BOTTOM, null, null);
        }
        if (holder instanceof SystemModelHolder) {
            holder.getTitle().setText("本地资源");
            holder.getBannerLayout()
                    .initImageListResources(initSystemModel())
                    .initAdapter();
        }
        if (holder instanceof ArrayBannerHolder) {
            holder.getTitle().setText("数组资源");
            initArray();
            holder.getBannerLayout()
                    .initImageArrayResources(mImage, mTitle)
                    .initAdapter()
                    .initRound(true, true, true, BANNER_TIP_LAYOUT_POSITION.BOTTOM, BANNER_ROUND_POSITION.LEFT, BANNER_TITLE_POSITION.RIGHT);
        }
        if (holder instanceof TransformersHolder) {
            holder.getTitle().setText("自定义动画");
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerTransformer(new AccordionTransformer());

        }
        if (holder instanceof SystemTransformersHolder) {
            holder.getTitle().setText("系统动画");
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerTransformer(BANNER_ANIMATION.CUBE_IN);
        }
        if (holder instanceof TransformersListHolder) {
            holder.getTitle().setText("自定义动画集合");
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerTransformerList(initTransformers());

        }
        if (holder instanceof SystemTransformersListHolder) {
            holder.getTitle().setText("系统动画集合");
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerSystemTransformerList(initSystemTransformer());
        }
        if (holder instanceof PromptBarHolder) {
            holder.getTitle().setText("自定义提示栏");
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .addOnBannerPageChangeListener(new BannerOnPage() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            Toast.makeText(((PromptBarHolder) holder).itemView.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addPromptBar(new PromptBarView(((PromptBarHolder) holder).itemView.getContext())) //自定义提示栏view initAdapter之前调用生效
                    .initAdapter();
        }
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE_MODEL:
                return new ImageModelHolder(getLayoutId(parent));
            case SYSTEM_NETWORK_MODEL:
                return new SystemNetWorkModelHolder(getLayoutId(parent));
            case SYSTEM_MODEL:
                return new SystemModelHolder(getLayoutId(parent));
            case ARRAY_BANNER:
                return new ArrayBannerHolder(getLayoutId(parent));
            case TRANSFORMERS:
                return new TransformersHolder(getLayoutId(parent));
            case SYSTEM_TRANSFORMER:
                return new SystemTransformersHolder(getLayoutId(parent));
            case TRANSFORMERS_LIST:
                return new TransformersListHolder(getLayoutId(parent));
            case SYSTEM_TRANSFORMER_LIST:
                return new SystemTransformersListHolder(getLayoutId(parent));
            default:
                return new PromptBarHolder(getLayoutId(parent));
        }
    }


    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return IMAGE_MODEL;
            case 1:
                return SYSTEM_NETWORK_MODEL;
            case 2:
                return SYSTEM_MODEL;
            case 3:
                return ARRAY_BANNER;
            case 4:
                return TRANSFORMERS;
            case 5:
                return SYSTEM_TRANSFORMER;
            case 6:
                return TRANSFORMERS_LIST;
            case 7:
                return SYSTEM_TRANSFORMER_LIST;
            default:
                return PROMPT_BAR;
        }
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private BannerLayout bannerLayout;

        public BaseViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            bannerLayout = (BannerLayout) itemView.findViewById(R.id.banner_layout);
        }

        public TextView getTitle() {
            return title;
        }

        public BannerLayout getBannerLayout() {
            return bannerLayout;
        }
    }

    private View getLayoutId(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
    }


    /**
     * 系统动画集合
     */
    private List<BANNER_ANIMATION> initSystemTransformer() {
        List<BANNER_ANIMATION> enumTransformer = new ArrayList<>();
        enumTransformer.add(BANNER_ANIMATION.ACCORDION);
        enumTransformer.add(BANNER_ANIMATION.CUBE_IN);
        enumTransformer.add(BANNER_ANIMATION.CUBE_OUT);
        enumTransformer.add(BANNER_ANIMATION.DEFAULT);
        enumTransformer.add(BANNER_ANIMATION.DEPTH_PAGE);
        return enumTransformer;
    }

    /**
     * 自定义动画集合，这里直接用系统动画代替
     */
    private List<BannerTransformer> initTransformers() {
        List<BannerTransformer> transformers = new ArrayList<>();
        transformers.add(new RotateDownTransformer());
        transformers.add(new AccordionTransformer());
        transformers.add(new BackgroundToForegroundTransformer());
        transformers.add(new CubeOutTransformer());
        transformers.add(new CubeInTransformer());
        return transformers;
    }

    private Object[] mImage;
    private String[] mTitle;

    /**
     * 初始化数组资源
     */
    private void initArray() {
        mImage = new Object[]{"http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", R.drawable.banner2, R.drawable.banner3};
        mTitle = new String[]{"bannerl", "banner2", "banner3"};
    }

    /**
     * 自带的Model类,使用本地资源
     */
    private List<BannerModel> initSystemModel() {
        List<BannerModel> mBanner = new ArrayList<>();
        mBanner.add(new BannerModel(R.drawable.banner1));
        mBanner.add(new BannerModel(R.drawable.banner2));
        mBanner.add(new BannerModel(R.drawable.banner3));
        mBanner.add(new BannerModel(R.drawable.banner4));
        return mBanner;
    }

    /**
     * 自带的Model类,使用网络数据
     */
    private List<BannerModel> initSystemNetWorkModel() {
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "那个时候刚恋爱，这个时候放分手"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "羞羞呢～"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "腿不长 但细"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "深夜了"));
        return mDatas;
    }

    /**
     * 自定义model类
     */
    private List<ImageModel> initImageModel() {
        List<ImageModel> list = new LinkedList<>();
        list.add(new ImageModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "那个时候刚恋爱，这个时候放分手", "banner1"));
        list.add(new ImageModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "羞羞呢～", "banner2"));
        list.add(new ImageModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "腿不长 但细", "banner3"));
        list.add(new ImageModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "深夜了", "banner4"));
        return list;
    }
}
