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
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.bean.BannerBean;
import com.bannersimple.bean.ImageModel;
import com.bannersimple.holder.ImageManagerHolder;
import com.bannersimple.holder.ImageModelHolder;
import com.bannersimple.holder.SystemNetWorkModelHolder;
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
    private static final int IMAGE_LOADER_MANAGER = 2;//A collection of system animations
    private static final int IS_VERTICAL = 3; //Customize Load Picture Manager

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (holder instanceof ImageModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.image_holder));
            holder.getBannerLayout()
                    .initListResources(initImageModel())
                    .setTipsSite(BannerLayout.ALIGN_PARENT_TOP)
                    .setTitleSetting(ContextCompat.getColor(holder.getContext(), R.color.colorAccent), -1)
                    .initTips(true, true, true).start(true);
        }
        if (holder instanceof SystemNetWorkModelHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.system_network_model));
            holder.getBannerLayout()
                    .initListResources(initSystemNetWorkModel())
                    .setTipsSite(BannerLayout.ALIGN_PARENT_BOTTOM)
                    .initTips(true, true, true);
        }
        if (holder instanceof ImageManagerHolder) {
            holder.getTitle().setText(getString(holder.getContext(), R.string.customize_load_Picture_Manager));
            holder.getBannerLayout()
                    .initListResources(initBannerBean())
                    .setImageLoaderManage(new ImageManager())
                    .addOnBannerTitleListener(new OnBannerTitleListener() {
                        @Override
                        public String getTitle(int newPosition) {
                            return initBannerBean().get(newPosition).getThisTitle();
                        }
                    })
                    .initTips(true, true, true);
        }
        if (holder instanceof VerticalHolder) {
            //if it is a vertical slide can not set the animation
            holder.getTitle().setText(getString(holder.getContext(), R.string.is_vertical));
            holder.getBannerLayout()
                    .setVertical(true)
                    .initListResources(initSystemNetWorkModel())
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
            case IS_VERTICAL:
                return new VerticalHolder(getLayoutId(parent));
            default:
                return new ImageManagerHolder(getLayoutId(parent));
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return IMAGE_MODEL;
            case 1:
                return SYSTEM_NETWORK_MODEL;
            case 2:
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
