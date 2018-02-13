package com.nvinas.hnews.ui.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.util.ActivityUtil;
import com.nvinas.hnews.common.util.CommonUtil;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by nvinas on 11/02/2018.
 */

public class CommentsActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        CommentsFragment commentsFragment = (CommentsFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);
        if (commentsFragment == null) {
            commentsFragment = CommentsFragment.newInstance(
                    getIntent().getParcelableExtra(CommonUtil.Constants.INTENT_KEY_STORY));
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), commentsFragment, R.id.container);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
