package com.nvinas.hnews.ui.comments;

import dagger.Binds;
import dagger.Module;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class CommentsModule {

    @Binds
    abstract CommentsContract.Presenter commentsPresenter(CommentsPresenter commentsPresenter);
}
