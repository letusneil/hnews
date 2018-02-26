package com.nvinas.hnews.util;

import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.nvinas.hnews.common.listener.FragmentIdlingResourceListener;

/**
 * Created by nvinas on 18/02/2018.
 */

public class FragmentIdlingResource implements IdlingResource {

    private static final String TAG = FragmentIdlingResource.class.getSimpleName();
    private FragmentIdlingResourceListener fragmentIdlingResourceListener;
    private ResourceCallback callback;

    public FragmentIdlingResource(Fragment fragment) {
        fragmentIdlingResourceListener = (FragmentIdlingResourceListener) fragment;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public boolean isIdleNow() {
        if (fragmentIdlingResourceListener.isIdle()) {
            callback.onTransitionToIdle();
        }
        return fragmentIdlingResourceListener.isIdle();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}
