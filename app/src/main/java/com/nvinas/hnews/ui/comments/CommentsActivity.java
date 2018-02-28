package com.nvinas.hnews.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.listener.ActivityIdlingResourceListener;
import com.nvinas.hnews.common.util.ActivityUtil;
import com.nvinas.hnews.common.util.CommonUtil;
import com.nvinas.hnews.data.Comment;
import com.nvinas.hnews.data.Story;
import com.nvinas.hnews.ui.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

/**
 * Created by nvinas on 11/02/2018.
 */

public class CommentsActivity extends DaggerAppCompatActivity
        implements CommentsContract.View, ActivityIdlingResourceListener {

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
    private boolean isIdle = true;

    private ArrayList<Comment> commentItems = new ArrayList<>(0);
    private static final String KEY_COMMENTS = "key_comments";
    private Story story;
    private static final String KEY_COMMENT_STORY = "key_story_comment";

    @Inject
    CommentsContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        initCommentUi();

        presenter.takeView(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                Story storyExtra = intent.getParcelableExtra(CommonUtil.Constants.INTENT_KEY_STORY);
                if (storyExtra != null) {
                    story = storyExtra;
                    initStoryUi();
                    List<Integer> kids = story.getKids();
                    if (kids != null) {
                        presenter.loadComments(kids);
                    }
                }
            }
        } else {
            story = savedInstanceState.getParcelable(KEY_COMMENT_STORY);
            commentItems = savedInstanceState.getParcelableArrayList(KEY_COMMENTS);
            showStoryInfo(story);
            showComments(commentItems);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initCommentUi() {
        commentsAdapter = new CommentsAdapter(this, new ArrayList<>(0));
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvComments.setLayoutManager(lm);
        rvComments.addItemDecoration(CommonUtil.getDividerItemDecoration(this, R.drawable.divider, lm.getOrientation()));
        rvComments.setAdapter(commentsAdapter);

        swipeRefresh.setOnRefreshListener(() -> {
            commentItems.clear();
            commentsAdapter.removeAll();
            presenter.loadComments(story.getKids());
        });
    }

    private void initStoryUi() {
        showStoryInfo(story);
    }

    @Override
    public void showComments(@NonNull List<Comment> comments) {
        commentItems = new ArrayList<>(comments);
        commentsAdapter.setComments(comments);
    }

    @Override
    public void showComment(@NonNull Comment comment) {
        commentItems.add(comment);
        commentsAdapter.addComment(comment, commentItems.size());
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
        ActivityUtil.startActivity(this, WebViewActivity.class,
                new Intent().putExtra(CommonUtil.Constants.INTENT_KEY_URL, url));
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProgressIndicator(boolean progress) {
        swipeRefresh.setRefreshing(progress);
    }

    @Override
    public boolean isActive() {
        return !isFinishing();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Timber.d("Comment size? %s", commentItems.size());
        outState.putParcelable(KEY_COMMENT_STORY, story);
        outState.putParcelableArrayList(KEY_COMMENTS, commentItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        presenter.dropView();
        super.onDestroy();
    }

    @Override
    public boolean isIdle() {
        return isIdle;
    }

    @Override
    public void setIdleStatus(boolean isIdle) {
        this.isIdle = isIdle;
    }
}
