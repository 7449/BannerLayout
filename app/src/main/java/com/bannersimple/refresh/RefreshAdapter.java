package com.bannersimple.refresh;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bannerlayout.Interface.OnBannerClickListener;
import com.bannerlayout.model.BannerModel;
import com.bannerlayout.widget.BannerLayout;
import com.bannersimple.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * by y on 2017/3/8.
 */

public class RefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ITEM = 1;

    private List<ListModel> listModels = null;
    private List<BannerModel> bannerModels = null;

    private Object[] image = new Object[]{
            "http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg",
            "http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6qyhzgj30dw07t75g.jpg",
            "http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg",
            "http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg"
    };
    private String[] title = new String[]{
            "At that time just love, this time to break up",
            "Shame it ~",
            "The legs are not long but thin",
            "Late at night"};

    public RefreshAdapter(List<ListModel> listModels) {
        this.listModels = listModels;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BANNER:
                return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false));
            default:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case TYPE_BANNER:
                if (bannerModels == null) {
                    return;
                }
                final BannerViewHolder viewHolder = (BannerViewHolder) holder;
                viewHolder.bannerLayout
                        .clearHandler()
                        .initArrayResources(image, title)
//                        .initListResources(bannerModels)
                        .setPageNumViewSite(BannerLayout.PAGE_NUM_VIEW_SITE_TOP_LEFT)
                        .setPageNumViewMargin(12, 0, 12, 0)
                        .setPageNumViewTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorAccent))
                        .initPageNumView()
                        .setTipsSite(BannerLayout.ALIGN_PARENT_BOTTOM)
                        .initTips(true, true, true)
                        .setOnBannerClickListener(new OnBannerClickListener() {
                            @Override
                            public void onBannerClick(View view, int position, Object model) {
                                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
                            }
                        });

                viewHolder.start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.bannerLayout.startBanner();
                    }
                });
                viewHolder.stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.bannerLayout.stopBanner();
                    }
                });
                viewHolder.restore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.bannerLayout.restoreBanner();
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
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .centerCrop()
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


    public void addBanner(List<BannerModel> bannerModels) {
        this.bannerModels = bannerModels;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {

        private BannerLayout bannerLayout;
        private Button start;
        private Button stop;
        private Button restore;

        public BannerViewHolder(View itemView) {
            super(itemView);
            bannerLayout = (BannerLayout) itemView.findViewById(R.id.banner);
            start = (Button) itemView.findViewById(R.id.start);
            stop = (Button) itemView.findViewById(R.id.stop);
            restore = (Button) itemView.findViewById(R.id.restore);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageView;
        private AppCompatTextView textView;

        ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.list_image);
            textView = (AppCompatTextView) itemView.findViewById(R.id.list_tv);
        }
    }
}
