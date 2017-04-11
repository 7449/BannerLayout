package com.bannersimple;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.bean.SimpleBannerModel;

import java.util.ArrayList;
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
        switch (getItemViewType(position)) {

            case IMAGE_MODEL:

                holder.getTitle().setText(getString(holder.getContext(), R.string.image_holder));
//                holder.getBannerLayout()
//                        .initListResources(initImageModel())
//                        .setTitleSetting(R.color.colorAccent, -1)
//                        .setPageNumViewMargin(10, 0, 0, 10)
//                        .setPageNumViewTextColor(R.color.colorAccent)
//                        .setPageNumViewBackgroundColor(R.color.colorWhite)
//                        .setPageNumViewMark(" * ")
//                        .initPageNumView()
//                        .initTips(true, true, true)
//                        .start(true);
                holder.getBannerLayout()
                        .initListResources(initNetWorkModel())
                        .initTips(true, true, false)
                        .setOnBannerClickListener(new OnBannerClickListener<SimpleBannerModel>() {
                            @Override
                            public void onBannerClick(View view, int position, SimpleBannerModel model) {

                            }
                        })
                        .start(true, 4000);
                break;
            case SYSTEM_NETWORK_MODEL:

                holder.getTitle().setText(getString(holder.getContext(), R.string.system_network_model));
                holder.getBannerLayout()
                        .initListResources(initNetWorkModel())
                        .setPageNumViewSite(BannerLayout.PAGE_NUM_VIEW_SITE_TOP_LEFT)
                        .setPageNumViewMargin(12, 0, 12, 0)
                        .setPageNumViewTextColor(ContextCompat.getColor(holder.getContext(), R.color.colorAccent))
                        .initPageNumView()
                        .setTipsSite(BannerLayout.ALIGN_PARENT_BOTTOM)
                        .initTips(true, true, true);
                break;
            case IS_VERTICAL:

                //if it is a vertical slide can not set the animation
                holder.getTitle().setText(getString(holder.getContext(), R.string.is_vertical));
                holder.getBannerLayout()
                        .setVertical(true)
                        .initListResources(initNetWorkModel())
                        .initTips(true, true, false)
                        .start(true, 2000)
                        .setOnBannerClickListener(new OnBannerClickListener() {
                            @Override
                            public void onBannerClick(View view, int position, Object model) {
                                Toast.makeText(holder.getContext(), "" + position, Toast.LENGTH_SHORT).show();
                            }

                        });
                break;
            default:

                holder.getTitle().setText(getString(holder.getContext(), R.string.customize_load_Picture_Manager));
                holder.getBannerLayout()
                        .initListResources(initNetWorkModel())
                        .setImageLoaderManager(new ImageManager())
                        .initTips(true, true, true);
                break;
        }
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return new BaseViewHolder(linearLayout);
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
        private LinearLayout rootView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView;
            title = new TextView(rootView.getContext());
            title.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorBackground));
            title.setTextSize(14);
            title.setPadding(10, 10, 10, 10);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 450);
            bannerLayout = new BannerLayout(rootView.getContext());

            rootView.addView(title);
            rootView.addView(bannerLayout, params);
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

    private List<SimpleBannerModel> initNetWorkModel() {
        List<SimpleBannerModel> mDatas = new ArrayList<>();
        mDatas.add(new SimpleBannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "At that time just love, this time to break up"));
        mDatas.add(new SimpleBannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg", "Shame it ~"));
        mDatas.add(new SimpleBannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "The legs are not long but thin"));
        mDatas.add(new SimpleBannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "Late at night"));
        return mDatas;
    }

    private String getString(Context context, int i) {
        return context.getString(i);
    }
}