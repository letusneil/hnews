package com.nvinas.hnews.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;

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

        CommentsFragment commentsFragment = (CommentsFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);
        if (commentsFragment == null) {
            commentsFragment = CommentsFragment.newInstance(
                    getIntent().getIntExtra(CommonUtil.Constants.INTENT_KEY_ID, 0));
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), commentsFragment, R.id.container);
        }
    }
}
