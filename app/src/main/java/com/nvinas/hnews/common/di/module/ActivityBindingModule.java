package com.nvinas.hnews.common.di.module;

import com.nvinas.hnews.common.di.annotation.ActivityScope;
import com.nvinas.hnews.ui.comments.CommentsActivity;
import com.nvinas.hnews.ui.comments.CommentsModule;
import com.nvinas.hnews.ui.stories.StoriesActivity;
import com.nvinas.hnews.ui.stories.StoriesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = StoriesModule.class)
    abstract StoriesActivity storiesActivity();

    @ContributesAndroidInjector(modules = CommentsModule.class)
    abstract CommentsActivity commentsActivity();
}
