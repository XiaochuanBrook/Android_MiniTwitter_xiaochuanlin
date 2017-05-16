package com.lin.app.Ui.RecyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.app.Bean.FeedItem;
import com.lin.app.R;
import com.lin.app.Twitter;
import com.lin.app.Ui.Views.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedsRecyclerViewAdapter.FeedItemViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public FeedsRecyclerViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    public void refresh(List<FeedItem> feedItemList) {
        feedItemList.clear();
       /// feedItemList.addAll(feedItemList);
        notifyDataSetChanged();
    }

    @Override
    public FeedItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        FeedItemViewHolder viewHolder = new FeedItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FeedItemViewHolder feedItemViewHolder, int i) {
        final FeedItem feedItem = feedItemList.get(i);

        if (feedItem.getAuthor().equals(FeedItem.FAKE_FOOTER_FEED_KEY)) {
            // special item for showing load more footer
            // hide all items expect the description
            feedItemViewHolder.avatar.setVisibility(View.GONE);
            feedItemViewHolder.imageView.setVisibility(View.GONE);
            feedItemViewHolder.textView.setVisibility(View.GONE);
            feedItemViewHolder.description.setVisibility(View.VISIBLE);
            feedItemViewHolder.description.setText("Load more...");
        } else {

            final String avatarUrl = feedItem.getAvatar();
            feedItemViewHolder.avatar.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(avatarUrl)) {
                Picasso.with(mContext).load(avatarUrl)
                        .fit()
                        .placeholder(R.drawable.ic_action_account)
                        .into(feedItemViewHolder.avatar);
            } else {
                feedItemViewHolder.avatar.setImageDrawable(Twitter.getAppContext().getResources().getDrawable(R.drawable.ic_action_account));
            }

            // set picture
            final String imageUrl = feedItem.getPicture();
            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.with(Twitter.getAppContext()).load(Uri.parse(imageUrl))
                        .fit()
                        .placeholder(R.drawable.ic_image_placeholder)
                        .into(feedItemViewHolder.imageView);
                feedItemViewHolder.imageView.setVisibility(View.VISIBLE);
            } else {
                feedItemViewHolder.imageView.setImageDrawable(null);
                feedItemViewHolder.imageView.setVisibility(View.GONE);
            }

            //Setting text view title
            feedItemViewHolder.textView.setText(Html.fromHtml(feedItem.getAuthor()));

            feedItemViewHolder.description.setText(Html.fromHtml(feedItem.getDescription()));
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(feedItem);
            }
        };
        feedItemViewHolder.imageView.setOnClickListener(listener);
        feedItemViewHolder.textView.setOnClickListener(listener);
        feedItemViewHolder.description.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


    class FeedItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private ImageView imageView;
        private TextView textView;
        private TextView description;

        private FeedItemViewHolder(View view) {
            super(view);
            this.avatar = (ImageView) view.findViewById(R.id.avatar);
            this.imageView = (ImageView) view.findViewById(R.id.picture);
            this.textView = (TextView) view.findViewById(R.id.author);
            this.description = (TextView) view.findViewById(R.id.content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}