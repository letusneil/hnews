package com.nvinas.hnews.stories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.listener.StoryItemClickListener;
import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Story;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nvinas on 10/02/2018.
 */
public class StoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Story> stories;

    private boolean showProgressIndicator = false;
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_STORY = 1;
    private StoryItemClickListener listener;

    StoriesAdapter(Context context, List<Story> stories) {
        this.context = context;
        this.stories = stories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == TYPE_STORY ?
                new StoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_story_item, parent, false)) :
                new ProgressIndicatorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_avi_progress, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StoryViewHolder) {
            if (stories != null && stories.size() > 0) {
                Story item = stories.get(position);
                ((StoryViewHolder) holder).bind(item);
            }
        } else if (holder instanceof ProgressIndicatorViewHolder) {
            ((ProgressIndicatorViewHolder) holder).bind();
        }
    }

    void setItemClickListener(StoryItemClickListener listener) {
        this.listener = listener;
    }

    void setStories(List<Story> stories) {
        this.stories.addAll(stories);
        notifyDataSetChanged();
    }

    void setShowProgressIndicator(boolean showProgressIndicator) {
        this.showProgressIndicator = showProgressIndicator;
    }

    void showLoadingIndicator() {
        setShowProgressIndicator(true);
        notifyItemInserted(stories.size());
    }

    @Override
    public int getItemCount() {
        if (stories == null) {
            return 0;
        }
        return showProgressIndicator ? stories.size() + 1 : stories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return showProgressIndicator && position == stories.size() ? TYPE_PROGRESS : TYPE_STORY;
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_title)
        TextView title;
        @BindView(R.id.tv_item_info)
        TextView info;
        @BindView(R.id.tv_item_points)
        TextView points;
        @BindView(R.id.tv_item_comments)
        TextView comments;
        @BindView(R.id.tv_url)
        TextView url;
        @BindView(R.id.tv_url_src)
        TextView urlHost;
        @BindView(R.id.ll_url)
        LinearLayout urlView;

        StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Story story) {
            if (story != null) {
                title.setText(CommonUtil.nullToEmptySting(story.getTitle()));
                comments.setText(context.getString(R.string.comments,
                        String.valueOf(story.getDescendants())));
                points.setText(context.getString(
                        R.string.points, String.valueOf(story.getScore())));
                info.setText(context.getString(
                        R.string.info, story.getBy(), CommonUtil.toTimeSpan(story.getTime())));
                urlHost.setText(CommonUtil.urlToBaseUrl(story.getUrl()));
                url.setText(CommonUtil.nullToEmptySting(story.getUrl()));
                urlView.setOnClickListener(view -> {
                    if (listener != null) {
                        listener.onOpenStoryUrl(CommonUtil.nullToEmptySting(story.getUrl()));
                    }
                });
            }
        }
    }

    class ProgressIndicatorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avi_progress_indicator)
        AVLoadingIndicatorView aviProgressIndicator;

        ProgressIndicatorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {
            aviProgressIndicator.show();
        }
    }
}
