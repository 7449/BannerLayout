package com.bannersimple;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.Interface.OnBannerTitleListener;
import com.bannerlayout.animation.AccordionTransformer;
import com.bannerlayout.animation.BackgroundToForegroundTransformer;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.animation.RotateDownTransformer;
import com.bannerlayout.bannerenum.BannerAnimationType;
import com.bannerlayout.bannerenum.BannerDotsSite;
import com.bannerlayout.bannerenum.BannerTipsSite;
import com.bannerlayout.bannerenum.BannerTitleSite;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.bean.BannerBean;
import com.bannersimple.bean.ImageModel;
import com.bannersimple.holder.ArrayBannerHolder;
import com.bannersimple.holder.ImageManagerHolder;
import com.bannersimple.holder.ImageModelHolder;
import com.bannersimple.holder.PromptBarHolder;
import com.bannersimple.holder.SystemModelHolder;
import com.bannersimple.holder.SystemNetWorkModelHolder;
import com.bannersimple.holder.SystemTransformersHolder;
import com.bannersimple.holder.SystemTransformersListHolder;
import com.bannersimple.holder.TransformersHolder;
import com.bannersimple.holder.TransformersListHolder;
import com.bannersimple.holder.VerticalHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/11/14
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.BaseViewHolder> {
    private static final int IMAGE_MODEL = 0;//Customize the model class
    private static final int SYSTEM_NETWORK_MODEL = 1;//Comes with the Model class, the use of network data
    private static final int SYSTEM_MODEL = 2;//Model comes with the Model, the use of local resources
    private static final int ARRAY_BANNER = 3;//Array resources
    private static final int TRANSFORMERS = 4;//Customize the animation
    private static final int SYSTEM_TRANSFORMER = 5;//system the animation
    private static final int TRANSFORMERS_LIST = 6;//Customize the animation collection
    private static final int SYSTEM_TRANSFORMER_LIST = 7;//A collection of system animations
    private static final int PROMPT_BAR = 8; //Customize the tip bar
    private static final int IMAGE_LOADER_MANAGER = 9; //Customize Load Picture Manager
    private static final int IS_VERTICAL = 10;// Whether the vertical sliding

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (holder instanceof ImageModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.image_holder));
            holder.getBannerLayout()
                    .initListResources(initImageModel())
                    .setTitleSetting(ContextCompat.getColor(holder.getContext(), R.color.colorAccent), -1)
                    .initTips(true, true, true, BannerTipsSite.TOP, null, null).start(true);
        }
        if (holder instanceof SystemNetWorkModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.system_network_model));
            holder.getBannerLayout()
                    .initListResources(initSystemNetWorkModel())
                    .initTips(true, true, true, BannerTipsSite.BOTTOM, null, null)
                    .setBannerSystemTransformerList(AnimationList.getAnimationList());
        }
        if (holder instanceof SystemModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.local_model));
            holder.getBannerLayout()
                    .initListResources(initSystemModel());
        }
        if (holder instanceof ArrayBannerHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.array_model));
            initArray();
            holder.getBannerLayout()
                    .initArrayResources(mImage, mTitle)
                    .initTips(true, true, true, BannerTipsSite.BOTTOM, BannerDotsSite.LEFT, BannerTitleSite.RIGHT);
        }
        if (holder instanceof TransformersHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_the_animation));
            holder.getBannerLayout()
                    .initListResources(initSystemNetWorkModel())
                    .initTips(true, true, true)
                    .setBannerTransformer(new AccordionTransformer());

        }
        if (holder instanceof SystemTransformersHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.system_animation));
            holder.getBannerLayout()
                    .initListResources(initSystemNetWorkModel())
                    .initTips(true, true, true)
                    .setBannerTransformer(BannerAnimationType.FLIPHORIZONTAL);
        }
        if (holder instanceof TransformersListHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_the_animation_collection));
            holder.getBannerLayout()
                    .initListResources(initSystemNetWorkModel())
                    .initTips(true, true, true)
                    .setBannerTransformerList(initTransformers());

        }
        if (holder instanceof SystemTransformersListHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.collection_of_system_animations));
            holder.getBannerLayout()
                    .initListResources(initSystemNetWorkModel())
                    .initTips(true, true, true)
                    .setBannerSystemTransformerList(initSystemTransformer());
        }
        if (holder instanceof PromptBarHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_the_tip_bar));
            holder.getBannerLayout()
                    .setTipsView(new PromptBarView(holder.getContext()))
                    .initListResources(initSystemNetWorkModel())
                    .addOnBannerPageChangeListener(new BannerOnPage() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            Toast.makeText(holder.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    })
            ;//The custom hint bar view takes effect before the initAdapter call;
        }
        if (holder instanceof ImageManagerHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_load_Picture_Manager));
            holder.getBannerLayout()
                    .setImageLoaderManage(new ImageManager())
                    .addOnBannerTitleListener(new OnBannerTitleListener() {
                        @Override
                        public String getTitle(int newPosition) {
                            return initBannerBean().get(newPosition).getThisTitle();
                        }
                    })
                    .initListResources(initBannerBean())
                    .initTips(true, true, true);
        }
        if (holder instanceof VerticalHolder) {
            //if it is a vertical slide can not set the animation
            holder.getTitle().setText(getString(holder.getContext(), R.string.is_vertical));
            holder.getBannerLayout()
                    .setVertical(true)
                    .initListResources(initSystemModel())
                    .initTips(true, true, false)
                    .start(true, 2000)
                    .setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void onBannerClick(int position, Object model) {
                            Toast.makeText(holder.getContext(), "" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
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
            case PROMPT_BAR:
                return new PromptBarHolder(getLayoutId(parent));
            case IS_VERTICAL:
                return new VerticalHolder(getLayoutId(parent));
            default:
                return new ImageManagerHolder(getLayoutId(parent));
        }
    }


    @Override
    public int getItemCount() {
        return 11;
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
            case 8:
                return PROMPT_BAR;
            case 9:
                return IMAGE_LOADER_MANAGER;
            default:
                return IS_VERTICAL;
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

        public Context getContext() {
            return itemView.getContext();
        }
    }

    private View getLayoutId(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
    }


    /**
     * A collection of system animations
     */
    private List<BannerAnimationType> initSystemTransformer() {
        List<BannerAnimationType> enumTransformer = new ArrayList<>();
        enumTransformer.add(BannerAnimationType.ACCORDION);
        enumTransformer.add(BannerAnimationType.DEPTH_PAGE);
        return enumTransformer;
    }

    /**
     * Custom animation collection, where the direct use of system animation instead
     */
    private List<BannerTransformer> initTransformers() {
        List<BannerTransformer> transformers = new ArrayList<>();
        transformers.add(new RotateDownTransformer());
        transformers.add(new AccordionTransformer());
        transformers.add(new BackgroundToForegroundTransformer());
        return transformers;
    }

    private Object[] mImage;
    private String[] mTitle;

    /**
     * Initializes an array resource
     */
    private void initArray() {
        mImage = new Object[]{"http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", R.drawable.banner2, R.drawable.banner3};
        mTitle = new String[]{"bannerl", "banner2", "banner3"};
    }

    /**
     * Model comes with the Model, the use of local resources
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
     * Comes with the Model class, the use of network data
     */
    private List<BannerModel> initSystemNetWorkModel() {
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "At that time just love, this time to break up"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "Shame it ~"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "The legs are not long but thin"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "Late at night"));
        return mDatas;
    }

    /**
     * Customize the model class
     */
    private List<ImageModel> initImageModel() {
        List<ImageModel> list = new LinkedList<>();
        list.add(new ImageModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "那个时候刚恋爱，这个时候放分手", "banner1"));
        list.add(new ImageModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "羞羞呢～", "banner2"));
        list.add(new ImageModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "腿不长 但细", "banner3"));
        list.add(new ImageModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "深夜了", "banner4"));
        return list;
    }

    private List<BannerBean> initBannerBean() {
        List<BannerBean> mDatas = new ArrayList<>();
        mDatas.add(new BannerBean("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "At that time just love, this time to break up"));
        mDatas.add(new BannerBean("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "Shame it ~"));
        mDatas.add(new BannerBean("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "The legs are not long but thin"));
        mDatas.add(new BannerBean("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "Late at night"));
        return mDatas;
    }

    private String getString(Context context, int i) {
        return context.getString(i);
    }
}
