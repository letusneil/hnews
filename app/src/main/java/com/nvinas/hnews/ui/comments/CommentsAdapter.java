package com.nvinas.hnews.ui.comments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Comment;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nvinas on 10/02/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> comments;

    CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        if (comments != null && comments.size() > 0) {
            Comment item = comments.get(position);
            holder.bind(item, position);
        }

    }

    void addComment(Comment comment, int pos) {
        comments.add(comment);
        notifyItemInserted(pos);
    }

    void removeAll() {
        comments.clear();
        notifyDataSetChanged();
    }

    void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_by)
        TextView textBy;

        @BindView(R.id.text_time_ago)
        TextView textTime;

        @BindView(R.id.text_comment)
        TextView textComment;

        @BindView(R.id.indentation)
        LinearLayout indentation;

        @BindView(R.id.comment_level_indicator)
        View levelIndicator;

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Comment comment, int position) {
            textBy.setText(context.getString(R.string.comment_by, comment.getBy()));
            textTime.setText(CommonUtil.toTimeSpan(comment.getTime()));
            textComment.setText(Html.fromHtml(CommonUtil.nullToEmptySting(comment.getText())));

            if (comment.getLevel() == 0) {
                levelIndicator.setVisibility(View.GONE);
                indentation.removeAllViews();
            }

            for (int i = 0; i < comment.getLevel(); i++) {
                addDepthView(comment.getLevel());
            }
        }

        private void addDepthView(int level) {
            switch (level) {
                case 1:
                    levelIndicator.setBackgroundResource(R.drawable.shape_level_1_indicator);
                    break;
                case 2:
                    levelIndicator.setBackgroundResource(R.drawable.shape_level_2_indicator);
                    break;
                case 3:
                    levelIndicator.setBackgroundResource(R.drawable.shape_level_3_indicator);
                    break;
                case 4:
                    levelIndicator.setBackgroundResource(R.drawable.shape_level_4_indicator);
                    break;
                case 5:
                    levelIndicator.setBackgroundResource(R.drawable.shape_level_5_indicator);
                    break;
                case 6:
                    levelIndicator.setBackgroundResource(R.drawable.shape_level_6_indicator);
                    break;
                case 7:
                    levelIndicator.setBackgroundResource(R.drawable.shape_level_7_indicator);
                    break;
                default:
                    levelIndicator.setBackgroundResource(R.drawable.shape_default_indicator);
                    break;
            }

            indentation.setVisibility(View.VISIBLE);
            indentation.addView(LayoutInflater.from(itemView.getContext()).inflate(R.layout.view_indentation, indentation, false));
        }
    }
}
