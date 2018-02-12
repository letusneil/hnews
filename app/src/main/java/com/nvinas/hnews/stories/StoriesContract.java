package com.nvinas.hnews.stories;

import android.support.annotation.NonNull;

import com.nvinas.hnews.common.base.BasePresenter;
import com.nvinas.hnews.common.base.BaseView;
import com.nvinas.hnews.data.Story;

import java.util.List;

/**
 * Created by nvinas on 10/02/2018.
 */

public interface StoriesContract {

    interface View extends BaseView {
        void showStories(List<Story> stories);
        void showStoryWebview(@NonNull String url);
        void showStory(@NonNull Story story);
        boolean isActive();
    }

    interface Presenter extends BasePresenter<View> {
        void loadStories();
        void loadStoriesInfo();
    }
}
