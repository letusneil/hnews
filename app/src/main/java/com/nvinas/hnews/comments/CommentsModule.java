package com.nvinas.hnews.comments;

import dagger.Binds;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 10/02/2018.
 */

public abstract class CommentsModule {

    @ContributesAndroidInjector
    abstract CommentsFragment commentsFragment();

    @Binds
    abstract CommentsContract.Presenter commentsPresenter(CommentsPresenter commentsPresenter);
}
