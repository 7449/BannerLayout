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
import com.bannerlayout.animation.CubeInTransformer;
import com.bannerlayout.animation.CubeOutTransformer;
import com.bannerlayout.animation.RotateDownTransformer;
import com.bannerlayout.bannerenum.BANNER_ANIMATION;
import com.bannerlayout.bannerenum.BANNER_ROUND_POSITION;
import com.bannerlayout.bannerenum.BANNER_TIP_LAYOUT_POSITION;
import com.bannerlayout.bannerenum.BANNER_TITLE_POSITION;
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


    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (holder instanceof ImageModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.image_holder));
            holder.getBannerLayout()
                    .initImageListResources(initImageModel())
                    .initAdapter()
                    .setTitleSetting(ContextCompat.getColor(holder.getContext(), R.color.colorAccent), -1)
                    .initRound(true, true, true, BANNER_TIP_LAYOUT_POSITION.TOP, null, null)
                    .start(true, 2000)
                    .setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void onBannerClick(int position, Object model) {
                            Toast.makeText(holder.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (holder instanceof SystemNetWorkModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.system_network_model));
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true, BANNER_TIP_LAYOUT_POSITION.BOTTOM, null, null);
        }
        if (holder instanceof SystemModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.local_model));
            holder.getBannerLayout()
                    .initImageListResources(initSystemModel())
                    .initAdapter();
        }
        if (holder instanceof ArrayBannerHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.array_model));
            initArray();
            holder.getBannerLayout()
                    .initImageArrayResources(mImage, mTitle)
                    .initAdapter()
                    .initRound(true, true, true, BANNER_TIP_LAYOUT_POSITION.BOTTOM, BANNER_ROUND_POSITION.LEFT, BANNER_TITLE_POSITION.RIGHT);
        }
        if (holder instanceof TransformersHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_the_animation));
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerTransformer(new AccordionTransformer());

        }
        if (holder instanceof SystemTransformersHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.system_animation));
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerTransformer(BANNER_ANIMATION.CUBE_IN);
        }
        if (holder instanceof TransformersListHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_the_animation_collection));
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerTransformerList(initTransformers());

        }
        if (holder instanceof SystemTransformersListHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.collection_of_system_animations));
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .initAdapter()
                    .initRound(true, true, true)
                    .setBannerSystemTransformerList(initSystemTransformer());
        }
        if (holder instanceof PromptBarHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_the_tip_bar));
            holder.getBannerLayout()
                    .initImageListResources(initSystemNetWorkModel())
                    .addOnBannerPageChangeListener(new BannerOnPage() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            Toast.makeText(holder.getContext(), position + "", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addPromptBar(new PromptBarView(holder.getContext())) //The custom hint bar view takes effect before the initAdapter call
                    .initAdapter();
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
                    .initImageListResources(initBannerBean())
                    .initAdapter()
                    .initRound(true, true, true);
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
            default:
                return new ImageManagerHolder(getLayoutId(parent));
        }
    }


    @Override
    public int getItemCount() {
        return 10;
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
            default:
                return IMAGE_LOADER_MANAGER;
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
     * Custom animation collection, where the direct use of system animation instead
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
