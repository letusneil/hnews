package com.nvinas.hnews.comments;

import com.nvinas.hnews.comments.CommentsContract;
import com.nvinas.hnews.comments.CommentsFragment;
import com.nvinas.hnews.comments.CommentsPresenter;

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
