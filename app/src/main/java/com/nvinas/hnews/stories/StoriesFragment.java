package com.nvinas.hnews.stories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.listener.StoryItemClickListener;
import com.nvinas.hnews.common.listener.RecyclerViewScrollListener;
import com.nvinas.hnews.common.util.ActivityUtil;
import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

/**
 * Created by nvinas on 09/02/2018.
 */

public class StoriesFragment extends DaggerFragment implements StoriesContract.View, StoryItemClickListener {

    @BindView(R.id.rv_stories)
    RecyclerView rvStories;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Inject
    StoriesContract.Presenter presenter;

    private StoriesAdapter storiesAdapter;
    private Unbinder unbinder;

    @Inject
    public StoriesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stories, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initUi();
        return rootView;
    }

    private void initUi() {
        swipeRefresh.setOnRefreshListener(() -> presenter.loadStories());

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvStories.setLayoutManager(lm);
        rvStories.addOnScrollListener(new RecyclerViewScrollListener(lm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                storiesAdapter.showLoadingIndicator();
                presenter.loadStoriesInfo();
            }
        });
        storiesAdapter = new StoriesAdapter(getContext(), new ArrayList<>());
        storiesAdapter.setItemClickListener(this);
        rvStories.setAdapter(storiesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void showStories(List<Story> stories) {
        storiesAdapter.setShowProgressIndicator(false);
        storiesAdapter.setStories(stories);
    }

    @Override
    public void onItemClick(int pos, Story object) {
    }

    @Override
    public void onOpenStoryUrl(String url) {
        ActivityUtil.startActivity(getActivity(), WebViewActivity.class,
                new Intent().putExtra(CommonUtil.Constants.INTENT_KEY_URL, url));
    }

    @Override
    public void onShareStory(Story story) {
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void setProgressIndicator(boolean progress) {
        swipeRefresh.setRefreshing(progress);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        presenter.dropView();
        super.onDestroy();
    }

    public static StoriesFragment newInstance() {
        return new StoriesFragment();
    }
}
