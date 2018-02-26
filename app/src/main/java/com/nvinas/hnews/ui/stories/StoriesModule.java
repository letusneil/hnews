package com.nvinas.hnews.ui.stories;

import com.nvinas.hnews.common.di.annotation.ActivityScope;
import com.nvinas.hnews.common.di.annotation.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by nvinas on 10/02/2018.
 */
@Module
public abstract class StoriesModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract StoriesFragment storiesFragment();

    @ActivityScope
    @Binds
    abstract StoriesContract.Presenter storiesPresenter(StoriesPresenter storiesPresenter);
}
