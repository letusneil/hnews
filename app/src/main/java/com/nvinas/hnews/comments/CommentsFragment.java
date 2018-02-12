package com.nvinas.hnews.comments;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.listener.RecyclerViewScrollListener;
import com.nvinas.hnews.common.util.ActivityUtil;
import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Comment;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

/**
 * Created by nvinas on 10/02/2018.
 */

public class CommentsFragment extends DaggerFragment implements CommentsContract.View {

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
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

    private CommentsAdapter commentsAdapter;
    private Unbinder unbinder;

    @Inject
    CommentsContract.Presenter presenter;

    @Inject
    public CommentsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        unbinder = ButterKnife.bind(this, view);

        initUi();

        return view;
    }

    private void initUi() {
        commentsAdapter = new CommentsAdapter(getContext(), new ArrayList<>(0));
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvComments.setLayoutManager(lm);
        rvComments.addOnScrollListener(new RecyclerViewScrollListener(lm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
//                commentsAdapter.showLoadingIndicator();
            }
        });
        rvComments.setAdapter(commentsAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Story story = bundle.getParcelable(CommonUtil.Constants.INTENT_KEY_STORY);
            if (story == null) {
                Timber.d("Story is null!");
                return;
            }
            showStoryInfo(story);
            presenter.takeView(this);
            presenter.loadComments(story.getKids());
        }
    }

    @Override
    public void showComments(List<Comment> comments) {
//        commentsAdapter.setShowProgressIndicator(false);
        commentsAdapter.setComments(comments);
    }

    @Override
    public void showStoryInfo(@NonNull Story story) {
        title.setText(CommonUtil.nullToEmptySting(story.getTitle()));
        comments.setText(getString(R.string.comments,
                String.valueOf(story.getDescendants())));
        points.setText(getString(
                R.string.points, String.valueOf(story.getScore())));
        info.setText(getString(
                R.string.info, story.getBy(), CommonUtil.toTimeSpan(story.getTime())));
        urlHost.setText(CommonUtil.urlToBaseUrl(story.getUrl()));
        url.setText(CommonUtil.nullToEmptySting(story.getUrl()));
        urlView.setOnClickListener(view -> showStoryWebview(story.getUrl()));
    }

    @Override
    public void showStoryWebview(@NonNull String url) {
        ActivityUtil.startActivity(getActivity(), WebViewActivity.class,
                new Intent().putExtra(CommonUtil.Constants.INTENT_KEY_URL, url));
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void setProgressIndicator(boolean progress) {
        swipeRefresh.setRefreshing(progress);
    }

    public static CommentsFragment newInstance(@NonNull Story story) {
        Bundle args = new Bundle();
        args.putParcelable(CommonUtil.Constants.INTENT_KEY_STORY, story);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        presenter.dropView();
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
