package com.nvinas.hnews.common.di.module;

import com.nvinas.hnews.comments.CommentsActivity;
import com.nvinas.hnews.comments.CommentsModule;
import com.nvinas.hnews.stories.StoriesActivity;
import com.nvinas.hnews.stories.StoriesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = StoriesModule.class)
    abstract StoriesActivity storiesActivity();

    @ContributesAndroidInjector(modules = CommentsModule.class)
    abstract CommentsActivity commentsActivity();
}
