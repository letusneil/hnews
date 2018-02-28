package com.nvinas.hnews.ui.comments;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class CommentsModule {

    @ContributesAndroidInjector
    abstract CommentsFragment commentsFragment();

    @Binds
    abstract CommentsContract.Presenter commentsPresenter(CommentsPresenter commentsPresenter);
}
