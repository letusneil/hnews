package com.nvinas.hnews.ui.stories;

import android.os.Bundle;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.util.ActivityUtil;

import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class StoriesActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        StoriesFragment storiesFragment = (StoriesFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);
        if (storiesFragment == null) {
            storiesFragment = StoriesFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), storiesFragment, R.id.container);
        }
    }

}
