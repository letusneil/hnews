package com.nvinas.hnews.ui.stories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nvinas.hnews.common.base.BasePresenter;
import com.nvinas.hnews.common.base.BaseView;
import com.nvinas.hnews.data.Story;

import java.util.List;

/**
 * Created by nvinas on 10/02/2018.
 */

public interface StoriesContract {

    interface View extends BaseView {

        void showStory(@NonNull Story story);

        void showStories(@Nullable List<Story> stories);

        void showMoreStories(@Nullable List<Story> stories);

        void showStoryWebView(@NonNull String url);

        void showStoriesUnavailableError();

        void setIdleStatus(boolean isIdle);

        boolean isActive();
    }

    interface Presenter extends BasePresenter<View> {

        void loadStories(boolean forceUpdateStoryIds);

        void refreshStories();

        void setNextPage(int nextPage);
    }
}
