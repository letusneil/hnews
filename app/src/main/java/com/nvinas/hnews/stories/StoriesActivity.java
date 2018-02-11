package com.nvinas.hnews.stories;

import android.os.Bundle;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.util.ActivityUtil;

import dagger.android.support.DaggerAppCompatActivity;

public class StoriesActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_stories);

        StoriesFragment storiesFragment = (StoriesFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);
        if (storiesFragment == null) {
            storiesFragment = StoriesFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), storiesFragment, R.id.container);
        }
    }

}
