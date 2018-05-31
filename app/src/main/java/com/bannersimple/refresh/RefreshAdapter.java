package com.bannersimple.refresh;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bannerlayout.listener.OnBannerClickListener;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bannersimple.bean.SimpleBannerModel;
import com.bannersimple.bean.SimpleData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * by y on 2017/3/8.
 */

public class RefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ITEM = 1;

    private List<ListModel> listModels;
    private List<SimpleBannerModel> bannerModels = null;

    private BannerLayout bannerLayout = null;

    public RefreshAdapter(List<ListModel> listModels) {
        this.listModels = listModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BANNER:
                return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false));
            default:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case TYPE_BANNER:
                if (bannerModels == null || bannerLayout != null) {
                    return;
                }
                final BannerViewHolder viewHolder = (BannerViewHolder) holder;
                bannerLayout = viewHolder.bannerLayout;
                Log.i(getClass().getSimpleName(), "RecyclerViewAdapter TYPE-banner");
                bannerLayout
                        .initTips(true, true, true)
                        .initListResources(bannerModels)
                        .setDelayTime(1000)
                        .switchBanner(true)
                        .setOnBannerClickListener(
                                new OnBannerClickListener() {
                                    @Override
                                    public void onBannerClick(View view, int position, Object model) {
                                        Toast.makeText(view.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                                    }
                                });

                viewHolder.start.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewHolder.bannerLayout.switchBanner(true);
                            }
                        });
                viewHolder.stop.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewHolder.bannerLayout.switchBanner(false);
                            }
                        });
                viewHolder.update.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bannerLayout.initListResources(bannerModels = SimpleData.update());
                            }
                        });

                break;
            case TYPE_ITEM:
                if (listModels == null) {
                    return;
                }
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Glide
                        .with(itemViewHolder.imageView.getContext())
                        .load(listModels.get(position - 1).getTitleImage())
                        .into(itemViewHolder.imageView);
                itemViewHolder.textView.setText(listModels.get(position - 1).getTitle());
                break;

        }


    }

    @Override
    public int getItemCount() {
        return listModels == null ? 0 : listModels.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_BANNER : TYPE_ITEM;
    }

    public void addAll(List<ListModel> data) {
        if (listModels != null) {
            listModels.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (listModels != null) {
            listModels.clear();
            notifyDataSetChanged();
        }
    }


    public void addBanner(List<SimpleBannerModel> bannerModels) {
        this.bannerModels = bannerModels;
    }

    public void clearBanner() {
        if (bannerLayout != null) {
            bannerLayout.clearBanner();
        }
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {

        private BannerLayout bannerLayout;
        private Button start;
        private Button stop;
        private Button update;

        BannerViewHolder(View itemView) {
            super(itemView);
            bannerLayout = itemView.findViewById(R.id.banner);
            start = itemView.findViewById(R.id.start);
            stop = itemView.findViewById(R.id.stop);
            update = itemView.findViewById(R.id.update);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageView;
        private AppCompatTextView textView;

        ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_image);
            textView = itemView.findViewById(R.id.list_tv);
        }
    }
}
