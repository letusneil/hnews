package com.nvinas.hnews.comments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Comment;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nvinas on 10/02/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> comments;

//    private boolean showProgressIndicator = false;
//    private static final int TYPE_PROGRESS = 0;
//    private static final int TYPE_COMMENT = 1;

    CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return viewType == TYPE_COMMENT ?
//                new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comment_item, parent, false)) :
//                new ProgressIndicatorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_avi_progress, parent, false));
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
//        if (holder instanceof CommentViewHolder) {
//            if (comments != null && comments.size() > 0) {
//                Comment item = comments.get(position);
//                ((CommentViewHolder) holder).bind(item);
//            }
//        } else if (holder instanceof ProgressIndicatorViewHolder) {
//            ((ProgressIndicatorViewHolder) holder).bind();
//        }
        if (comments != null && comments.size() > 0) {
            Comment item = comments.get(position);
            holder.bind(item);
        }

    }

    void setComments(List<Comment> comments) {
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }

//    void setShowProgressIndicator(boolean showProgressIndicator) {
//        this.showProgressIndicator = showProgressIndicator;
//    }
//
//    void showLoadingIndicator() {
//        setShowProgressIndicator(true);
//        notifyItemInserted(comments.size());
//    }

    @Override
    public int getItemCount() {
//        if (comments == null) {
//            return 0;
//        }
//        return showProgressIndicator ? comments.size() + 1 : comments.size();
        return comments.size();
    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return showProgressIndicator && position == comments.size() ? TYPE_PROGRESS : TYPE_COMMENT;
//    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_by)
        TextView textBy;

        @BindView(R.id.text_time_ago)
        TextView textTime;

        @BindView(R.id.text_comment)
        TextView textComment;

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Comment comment) {
            if (comment != null) {
                textBy.setText(context.getString(R.string.comment_by, comment.getBy()));
                textTime.setText(CommonUtil.toTimeSpan(comment.getTime()));
                textComment.setText(CommonUtil.nullToEmptySting(comment.getText()));
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
