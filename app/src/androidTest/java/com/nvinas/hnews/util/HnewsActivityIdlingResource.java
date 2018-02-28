package com.nvinas.hnews.util;

import android.app.Activity;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.nvinas.hnews.common.listener.ActivityIdlingResourceListener;

/**
 * Created by nvinas on 18/02/2018.
 */

public class HnewsActivityIdlingResource implements IdlingResource {

    private static final String TAG = HnewsActivityIdlingResource.class.getSimpleName();
    private ActivityIdlingResourceListener activityIdlingResourceListener;
    private ResourceCallback callback;

    public HnewsActivityIdlingResource(Activity activity) {
        activityIdlingResourceListener = (ActivityIdlingResourceListener) activity;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean isIdleNow() {
        if (activityIdlingResourceListener.isIdle()) {
            callback.onTransitionToIdle();
        }
        return activityIdlingResourceListener.isIdle();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}
