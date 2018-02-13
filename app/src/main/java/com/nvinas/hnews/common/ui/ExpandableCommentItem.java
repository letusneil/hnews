package com.nvinas.hnews.common.ui;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Comment;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nvinas on 12/02/2018.
 */

public class ExpandableCommentItem extends Item<ViewHolder> implements ExpandableItem {

    @BindView(R.id.text_by)
    TextView textBy;
    @BindView(R.id.text_time_ago)
    TextView textTime;
    @BindView(R.id.text_comment)
    TextView textComment;
    @BindView(R.id.text_show_more)
    TextView showMoreComments;

    private ExpandableGroup expandableGroup;
    private Comment comment;
    private int depth;

    public ExpandableCommentItem(Comment comment, int depth) {
        this.comment = comment;
        this.depth = depth;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        ButterKnife.bind(this, viewHolder.itemView);
        addDepthView(viewHolder.itemView);
        textBy.setText(comment.getBy());
        textTime.setText(CommonUtil.toTimeSpan(comment.getTime()));
        textComment.setText(comment.getText());
        if (comment.getKids().size() > 0) {
            showMoreComments.setVisibility(View.VISIBLE);
            showMoreComments.setOnClickListener(view -> expandableGroup.onToggleExpanded());
        } else {
            showMoreComments.setVisibility(View.GONE);
        }
    }

    @Override
    public void setExpandableGroup(@NonNull ExpandableGroup expandableGroup) {
        this.expandableGroup = expandableGroup;
    }

    @Override
    public int getLayout() {
        return R.layout.view_comment_item;
    }

    private void addDepthView(View itemView) {
    }
}
