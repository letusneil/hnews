package com.nvinas.hnews.comments;

import android.support.annotation.NonNull;

import com.nvinas.hnews.common.base.BasePresenter;
import com.nvinas.hnews.common.base.BaseView;
import com.nvinas.hnews.data.Comment;
import com.nvinas.hnews.data.Story;

import java.util.List;

/**
 * Created by nvinas on 10/02/2018.
 */

public interface CommentsContract {

    interface View extends BaseView {

        void showStoryInfo(Story story);
        void showStoryWebview(@NonNull String url);
        void showComments(List<Comment> comments);
        boolean isActive();
    }

    interface Presenter extends BasePresenter<View> {

        void loadComments(List<Integer> ids);
        void loadCommentChild(int id);
    }
}
